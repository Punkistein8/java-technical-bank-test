package hguerrero.ms.gestion.persona.cliente.service.impl;

import db.bank.core.entity.Cliente;
import db.bank.core.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import hguerrero.ms.gestion.persona.cliente.base.GenericResponse;
import hguerrero.ms.gestion.persona.cliente.communication.KafkaProducer;
import hguerrero.ms.gestion.persona.cliente.customExceptions.InvalidFieldException;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordAlreadyExistsException;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordNotFoundException;
import hguerrero.ms.gestion.persona.cliente.service.interfaces.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRepository clienteRepository;
    private final KafkaProducer kafkaProducerClient;

    @Override
    public GenericResponse<List<Cliente>> findAll() {
        return GenericResponse.<List<Cliente>>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clienteRepository.findAll()).build();
    }

    @Override
    public GenericResponse<List<Cliente>> findByEstado(String estado) {
        try {
            boolean parsedBool = Boolean.parseBoolean(estado);
            return GenericResponse.<List<Cliente>>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .message("Success")
                    .payload(clienteRepository.findByEstado(parsedBool)).build();
        } catch (Exception e) {
            throw new InvalidFieldException("El estado no es valido, verificar");
        }
    }

    @Override
    public GenericResponse<Cliente> findById(Long id) throws RecordNotFoundException {
        Cliente cliente = clienteRepository
                .findById(id)
                .orElseThrow(
                        () -> new RecordNotFoundException("No se encontró el cliente con el id: " + id));
        return GenericResponse.<Cliente>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(cliente).build();
    }

    @Override
    public GenericResponse<Cliente> findbyIdentificacion(String identificacion) {
        Cliente cliente = clienteRepository
                .findClienteByIdentificacion(identificacion);

        if (cliente == null) {
            throw new RecordNotFoundException("No se encontró el cliente con la identificación: " + identificacion);
        }

        return GenericResponse.<Cliente>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(cliente).build();
    }

    @Override
    public GenericResponse<Cliente> save(Cliente cliente) throws Exception {

        Cliente client = clienteRepository.findClienteByIdentificacion(cliente.getIdentificacion());
        if (client != null) {
            throw new RecordAlreadyExistsException("Ya existe un cliente con la identificación: " + cliente.getIdentificacion());
        }

        if(cliente.getIdentificacion().length() > 10){
            throw new InvalidFieldException("La identificación debe tener 10 números");
        }

        // validation if the client inserts letters instead of numbers
        try {
            Long.parseLong(cliente.getIdentificacion());
        } catch (NumberFormatException e) {
            throw new InvalidFieldException("La identificación debe ser un número");
        }

        return GenericResponse.<Cliente>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clienteRepository.save(cliente)).build();

    }

    @Override
    public GenericResponse<Cliente> update(Cliente cliente) throws Exception {
        try {
            return GenericResponse.<Cliente>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                    .message("Success")
                    .payload(clienteRepository.save(cliente)).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error al actualizar el cliente");
        }
    }

    @Override
    public GenericResponse<String> delete(Long id) {
        Cliente clientToDelete = clienteRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("No se encontró el cliente con el id: " + id));
        clientToDelete.setEstado(Boolean.FALSE);

        kafkaProducerClient.sendEliminacionLogica(id);

        return GenericResponse.<String>builder().status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload("Cliente eliminado correctamente").build();
    }
}
