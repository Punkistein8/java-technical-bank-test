package db.bank.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "movimientos", schema = "movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime fecha;
    private String tipoMovimiento;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;
    // foreign key with Cuenta
    @Column(nullable = false)
    private String cuentaNum;
}
