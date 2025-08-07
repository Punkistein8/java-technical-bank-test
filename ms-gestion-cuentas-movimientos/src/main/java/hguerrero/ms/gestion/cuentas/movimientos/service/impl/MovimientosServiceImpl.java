package hguerrero.ms.gestion.cuentas.movimientos.service.impl;

import db.bank.core.entity.Cuenta;
import db.bank.core.entity.Movimiento;
import db.bank.core.repository.MovimientosRepository;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.InsufficientFundsException;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.CuentaService;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.MovimientosService;
import hguerrero.ms.gestion.cuentas.movimientos.utils.MovimientosUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class MovimientosServiceImpl implements MovimientosService {
    private final CuentaService cuentaService;
    private final MovimientosRepository movimientosRepository;
    private final MovimientosUtils utils;

    @Override
    public List<Movimiento> findMovimientoByCuentaNum(String cuentaNum) {
        return movimientosRepository.findAllByCuentaNum(cuentaNum);
    }

    @Override
    public List<Movimiento> findAll() {
        return movimientosRepository.findAll();
    }

    @Override
    public Movimiento findById(Long id) throws RecordNotFound {
        return movimientosRepository.findById(id).orElseThrow(() -> new RecordNotFound("No se encontró el movimiento con el id: " + id));
    }

    @Override
    public Movimiento saveMovimiento(Movimiento movimiento) throws RecordNotFound, InsufficientFundsException {
        String numCuentaOrigen = movimiento.getCuentaNum();
        BigDecimal monto = movimiento.getValor();
        // validate if the account exists
        Cuenta cuenta = cuentaService.findByNumCuenta(numCuentaOrigen);
        if (cuenta == null) throw new RecordNotFound("La cuenta no existe");
        // get all movements from the account
        List<Movimiento> movimientos = movimientosRepository.findMovimientoByCuentaNumOrderByFechaDesc(numCuentaOrigen);
        BigDecimal saldoBase = movimientos.isEmpty() ? cuenta.getSaldoInicial() : movimientos.getFirst().getSaldo();
        // if there are no movements, create the first movement
        Movimiento newMovimiento = new Movimiento();
        newMovimiento.setCuentaNum(cuenta.getNumCuenta());
        newMovimiento.setTipoMovimiento(utils.getMovementType(monto) + " " +  monto.abs().toString());
        newMovimiento.setValor(monto);
        newMovimiento.setSaldo(utils.calcularSaldo(saldoBase, monto));
        return movimientosRepository.save(newMovimiento);
    }
}
