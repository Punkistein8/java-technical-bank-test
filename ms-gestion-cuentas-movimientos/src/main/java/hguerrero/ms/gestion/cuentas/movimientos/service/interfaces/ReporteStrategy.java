package hguerrero.ms.gestion.cuentas.movimientos.service.interfaces;

import db.bank.core.dtos.ReporteDTO;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;

import java.util.Date;

public interface ReporteStrategy {
    ReporteDTO generateReport(Long clienteId, Date fechaInicio, Date fechaFin) throws RecordNotFound;
}
