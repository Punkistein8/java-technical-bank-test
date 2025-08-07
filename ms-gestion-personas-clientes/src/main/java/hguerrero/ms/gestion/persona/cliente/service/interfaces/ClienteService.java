package hguerrero.ms.gestion.persona.cliente.service.interfaces;

import db.bank.core.entity.Cliente;
import hguerrero.ms.gestion.persona.cliente.base.GenericResponse;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordNotFoundException;

import java.util.List;

public interface ClienteService {
    GenericResponse<List<Cliente>> findAll();
    GenericResponse<List<Cliente>> findByEstado(String estado);
    GenericResponse<Cliente> findById(Long id) throws RecordNotFoundException;
    GenericResponse<Cliente> findbyIdentificacion(String identificacion);
    GenericResponse<Cliente> save(Cliente cliente) throws Exception;
    GenericResponse<Cliente> update(Cliente cliente) throws Exception;
    GenericResponse<String> delete(Long id);
}
