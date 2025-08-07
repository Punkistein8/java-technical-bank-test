package db.bank.core.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReporteDTO {
    private Long clienteId;
    private List<CuentaConMovimientoDTO> cuentas;
}
