package db.bank.core.repository;

import db.bank.core.entity.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface MovimientosRepository extends JpaRepository<Movimiento, Long> {
    List<Movimiento> findAll();
    List<Movimiento> findAllByCuentaNum(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumOrderByFechaDesc(String cuentaNum);
    List<Movimiento> findMovimientoByCuentaNumAndFechaBetween(String cuentaNum, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}