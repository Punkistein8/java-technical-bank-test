package hguerrero.ms.gestion.cuentas.movimientos.service.interfaces;

import db.bank.core.entity.Cuenta;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.List;

public interface CuentaService {
    Cuenta findByNumCuenta(String numCuenta) throws RecordNotFound;
    List<Cuenta> findAll();
    Cuenta save(Cuenta cuenta) throws RecordNotFound;
}
