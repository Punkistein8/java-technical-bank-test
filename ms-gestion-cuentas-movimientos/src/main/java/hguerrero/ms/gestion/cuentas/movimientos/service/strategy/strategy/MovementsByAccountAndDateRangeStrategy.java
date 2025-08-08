package hguerrero.ms.gestion.cuentas.movimientos.service.strategy.strategy;

import db.bank.core.dtos.ClienteResponseDTO;
import db.bank.core.dtos.CuentaConMovimientoDTO;
import db.bank.core.dtos.ReporteDTO;
import db.bank.core.entity.Cuenta;
import db.bank.core.entity.Movimiento;
import db.bank.core.repository.CuentaRepository;
import db.bank.core.repository.MovimientosRepository;
import hguerrero.ms.gestion.cuentas.movimientos.communication.KafkaProducerClient;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.ReporteStrategy;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Component("movementsByAccountAndDateRangeStrategy")
public class MovementsByAccountAndDateRangeStrategy implements ReporteStrategy {
    private final MovimientosRepository movimientosRepository;
    private final CuentaRepository cuentaRepository;
    private final KafkaProducerClient kafkaProducerClient;

    @Override
    public ReporteDTO generateReport(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws RecordNotFound {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha de fin");
        }

        // Validar la existencia del cliente usando Kafka
        log.info("Validando existencia del cliente con ID: {}", clienteId);
        ClienteResponseDTO clienteResponse = kafkaProducerClient.fetchCliente(clienteId);
        if (clienteResponse.getCliente() == null) {
            log.warn("Cliente con ID {} no encontrado en ms-core-persona-cliente", clienteId);
            throw new RecordNotFound("No se encontró el cliente con ID: " + clienteId);
        }
        log.info("Cliente con ID {} validado exitosamente", clienteId);

        // Obtener todas las cuentas del cliente
        List<Cuenta> cuentas = cuentaRepository.findCuentaByClienteId(clienteId)
                .orElseThrow(() -> new RecordNotFound("No se encontraron cuentas para el cliente con ID " + clienteId));

        // Construir el reporte
        List<CuentaConMovimientoDTO> cuentasConMovimientos = cuentas.stream()
                .map(cuenta -> {
                    List<Movimiento> movimientos = movimientosRepository
                            .findMovimientoByCuentaNumAndFechaBetween(cuenta.getNumCuenta(), fechaInicio, fechaFin);

                    BigDecimal saldoActual = movimientosRepository
                            .findMovimientoByCuentaNumOrderByFechaDesc(cuenta.getNumCuenta())
                            .stream()
                            .map(Movimiento::getSaldo)
                            .findFirst()
                            .orElse(cuenta.getSaldoInicial());

                    return CuentaConMovimientoDTO.builder()
                            .cuenta(cuenta)
                            .saldoActual(saldoActual)
                            .movimientos(movimientos)
                            .build();
                })
                .collect(Collectors.toList());

        return ReporteDTO.builder()
                .clienteId(clienteId)
                .cuentas(cuentasConMovimientos)
                .build();
    }
}
