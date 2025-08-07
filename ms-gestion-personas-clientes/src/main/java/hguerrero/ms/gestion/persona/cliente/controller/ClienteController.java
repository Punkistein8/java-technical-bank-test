package hguerrero.ms.gestion.persona.cliente.controller;

import db.bank.core.entity.Cliente;
import lombok.AllArgsConstructor;
import hguerrero.ms.gestion.persona.cliente.base.GenericResponse;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordNotFoundException;
import hguerrero.ms.gestion.persona.cliente.service.impl.ClienteServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {
    private final ClienteServiceImpl clienteService;
    @GetMapping("/getAll")
    @ResponseBody
    public GenericResponse<List<Cliente>> findAll() {
        return clienteService.findAll();
    }

    @GetMapping("/getAllByEstado")
    public GenericResponse<List<Cliente>> findByEstado(@RequestParam String estado) {
        return clienteService.findByEstado(estado);
    }

    @GetMapping("/{id}")
    public GenericResponse<Cliente> findById(@PathVariable("id") Long id) throws RecordNotFoundException {
        return clienteService.findById(id);
    }

    @PostMapping("/create")
    public GenericResponse<Cliente> save(@RequestBody Cliente cliente) throws Exception {
        return clienteService.save(cliente);
    }

    @PutMapping("/update")
    public GenericResponse<Cliente> update(@RequestBody Cliente cliente) throws Exception {
        return clienteService.update(cliente);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        clienteService.delete(id);
    }

}
