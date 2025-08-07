package db.bank.core.repository;

import db.bank.core.entity.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findAll();
    List<Cliente> findByEstado(boolean estado);
    Cliente findClienteByIdentificacion(String identificacion);
}
