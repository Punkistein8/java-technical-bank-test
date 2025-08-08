package hguerrero.ms.gestion.cuentas.movimientos.service.interfaces;

import db.bank.core.dtos.ReporteDTO;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;

import java.time.LocalDateTime;
import java.util.Date;

public interface ReporteStrategy {
    ReporteDTO generateReport(Long clienteId, LocalDateTime fechaInicio, LocalDateTime fechaFin) throws RecordNotFound;
}
