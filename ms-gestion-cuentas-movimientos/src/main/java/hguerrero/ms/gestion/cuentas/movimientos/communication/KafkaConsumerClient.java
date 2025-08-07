package hguerrero.ms.gestion.cuentas.movimientos.communication;

import db.bank.core.dtos.ClienteLogicalDeleteDTO;
import db.bank.core.dtos.ClienteResponseDTO;
import db.bank.core.entity.Cuenta;
import db.bank.core.repository.CuentaRepository;
import hguerrero.ms.gestion.cuentas.movimientos.cons.KafkaCons;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@AllArgsConstructor
public class KafkaConsumerClient {
    private final CuentaRepository cuentaRepository;


    private final Map<String, CompletableFuture<ClienteResponseDTO>> responseFutures = new ConcurrentHashMap<>();
    // Consumidor de respuestas
    @KafkaListener(topics = KafkaCons.TOPIC_RESPONSE, groupId = KafkaCons.GROUP_ID)
    public void listenClienteResponse(ConsumerRecord<String, ClienteResponseDTO> record) {
        ClienteResponseDTO response = record.value();
        String correlationId = response.getCorrelationId();
        log.info("Recibida respuesta de Kafka para correlationId: {}", correlationId);
        KafkaPetitionManager.getInstance().completeResponse(correlationId, response);
    }

    @KafkaListener(topics = KafkaCons.TOPIC_ELIMINACION_LOGICA, groupId = KafkaCons.GROUP_ID)
    @Transactional
    public void onClienteEliminacionLogica(ClienteLogicalDeleteDTO mensaje) {
        Long clienteId = mensaje.getClienteId();
        log.info("Recibida notificación de eliminación lógica para clienteId: {}", clienteId);

        // Buscar y actualizar cuentas
        Optional<List<Cuenta>> cuentasOpt = cuentaRepository.findCuentaByClienteId(clienteId);
        cuentasOpt.ifPresentOrElse(
                cuentas -> {
                    cuentas.forEach(cuenta -> {
                        cuenta.setEstado(Boolean.FALSE);
                        cuentaRepository.save(cuenta);
                        log.debug("Cuenta {} actualizada a estado FALSE", cuenta.getNumCuenta());
                    });
                    log.info("Se actualizaron {} cuentas del clienteId {} a estado FALSE", cuentas.size(), clienteId);
                },
                () -> log.info("No se encontraron cuentas asociadas al clienteId {}", clienteId)
        );


        log.info("Cuentas del clienteId {} actualizadas a estado FALSE", clienteId);
    }
}