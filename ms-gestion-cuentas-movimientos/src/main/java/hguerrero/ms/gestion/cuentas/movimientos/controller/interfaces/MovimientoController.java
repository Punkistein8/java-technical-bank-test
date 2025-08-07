package hguerrero.ms.gestion.cuentas.movimientos.controller.interfaces;

import db.bank.core.entity.Movimiento;
import hguerrero.ms.gestion.cuentas.movimientos.base.GenericResponse;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.InsufficientFundsException;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.MovimientosService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movimientos")
@AllArgsConstructor
public class MovimientoController {

    private final MovimientosService movimientosService;

    // Obtener todos los movimientos
    @GetMapping
    public ResponseEntity<GenericResponse<List<Movimiento>>> findAll() {
        List<Movimiento> movimientos = movimientosService.findAll();
        return ResponseEntity.ok(
                GenericResponse.<List<Movimiento>>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Movimientos obtenidos con éxito")
                        .payload(movimientos)
                        .build()
        );
    }

    // Obtener movimientos por número de cuenta
    @GetMapping("/cuenta/{numCuenta}")
    public ResponseEntity<GenericResponse<List<Movimiento>>> findByCuentaNum(@PathVariable("numCuenta") String numCuenta) {
        List<Movimiento> movimientos = movimientosService.findMovimientoByCuentaNum(numCuenta);
        return ResponseEntity.ok(
                GenericResponse.<List<Movimiento>>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Movimientos de la cuenta " + numCuenta + " obtenidos con éxito")
                        .payload(movimientos)
                        .build()
        );
    }

    // Obtener un movimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<Movimiento>> findById(@PathVariable("id") Long id) throws RecordNotFound {
        Movimiento movimiento = movimientosService.findById(id);
        return ResponseEntity.ok(
                GenericResponse.<Movimiento>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                        .message("Movimiento encontrado")
                        .payload(movimiento)
                        .build()
        );
    }

    // Crear un nuevo movimiento
    @PostMapping
    public ResponseEntity<GenericResponse<Movimiento>> saveMovimiento(@RequestBody Movimiento movimiento)
            throws RecordNotFound, InsufficientFundsException {
        Movimiento nuevoMovimiento = movimientosService.saveMovimiento(movimiento);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(GenericResponse.<Movimiento>builder()
                        .status(HttpStatusCode.valueOf(HttpStatus.CREATED.value()))
                        .message("Movimiento creado con éxito")
                        .payload(nuevoMovimiento)
                        .build()
                );
    }
}
