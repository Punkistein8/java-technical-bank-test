package db.bank.core.dtos;

import db.bank.core.entity.Cliente;
import lombok.Data;

@Data
public class ClienteResponseDTO {
    private Cliente cliente;
    private String correlationId;
}
