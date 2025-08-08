package hguerrero.ms.gestion.cuentas.movimientos.controller;

import db.bank.core.dtos.ReporteDTO;
import hguerrero.ms.gestion.cuentas.movimientos.base.GenericResponse;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.ReportesService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/reportes")
@AllArgsConstructor
public class ReportesController {

    private final ReportesService reportesService;

    // Otros métodos existentes...

    // Nuevo endpoint para reporte por número de cuenta y rango de fechas
    @GetMapping("/reporte")
    public ResponseEntity<GenericResponse<ReporteDTO>> getReporteByCuentaAndFechas(
            @RequestParam("clienteId") Long clienteId,
            @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
            @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin,
            @RequestParam("tipoReporte") String tipoReporte) throws RecordNotFound {

        // Parseo de Date a LocalDateTime
        java.time.LocalDateTime inicio = fechaInicio.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        java.time.LocalDateTime fin = fechaFin.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        
        ReporteDTO reporte = reportesService.generateReport(clienteId, inicio, fin, tipoReporte);
        return ResponseEntity.ok(
                GenericResponse.<ReporteDTO>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Reporte generado con éxito")
                        .payload(reporte)
                        .build()
        );
    }
}