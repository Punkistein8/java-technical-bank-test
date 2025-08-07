package hguerrero.ms.gestion.cuentas.movimientos.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericResponse<T> implements Serializable {
    private HttpStatusCode status;
    private String message;
    private T payload;
}