package hguerrero.ms.gestion.cuentas.movimientos.service.interfaces;

import db.bank.core.entity.Movimiento;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.InsufficientFundsException;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.List;

public interface MovimientosService {
    Movimiento saveMovimiento(Movimiento movimiento) throws RecordNotFound, InsufficientFundsException;
    List<Movimiento> findMovimientoByCuentaNum(String cuentaNum);
    List<Movimiento> findAll();
    Movimiento findById(Long id) throws RecordNotFound;
}
