package db.bank.core.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@Table(name = "movimientos", schema = "movimientos")
public class Movimiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private Date fecha;
    private String tipoMovimiento;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal saldo;
    // foreign key with Cuenta
    @Column(nullable = false)
    private String cuentaNum;
}
