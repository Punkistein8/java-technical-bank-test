package hguerrero.ms.gestion.cuentas.movimientos.controller.impl;

import db.bank.core.entity.Cuenta;
import hguerrero.ms.gestion.cuentas.movimientos.customExceptions.RecordNotFound;
import hguerrero.ms.gestion.cuentas.movimientos.service.interfaces.CuentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/cuentas")
public class CuentaController {
    private final CuentaService cuentaService;

    @GetMapping("/{numCuenta}")
    public ResponseEntity<Cuenta> getCuentaByNumCuenta(@PathVariable("numCuenta") String numCuenta) throws RecordNotFound {
        Cuenta cuenta = cuentaService.findByNumCuenta(numCuenta);
        return ResponseEntity.ok(cuenta);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Cuenta>> getAllCuentas() {
        List<Cuenta> cuentas = cuentaService.findAll();
        return ResponseEntity.ok(cuentas);
    }

    @PostMapping("/create")
    public ResponseEntity<Cuenta> createCuenta(@RequestBody Cuenta cuenta) throws RecordNotFound {
        Cuenta savedCuenta = cuentaService.save(cuenta);
        return ResponseEntity.ok(savedCuenta);
    }
}
