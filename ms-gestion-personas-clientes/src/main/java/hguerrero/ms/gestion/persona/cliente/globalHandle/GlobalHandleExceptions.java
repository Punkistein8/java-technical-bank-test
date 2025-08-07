package hguerrero.ms.gestion.persona.cliente.globalHandle;

import hguerrero.ms.gestion.persona.cliente.base.GenericResponse;
import hguerrero.ms.gestion.persona.cliente.customExceptions.InvalidFieldException;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordAlreadyExistsException;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalHandleExceptions {
    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<GenericResponse<String>> handleCuentaNotFoundException(RecordNotFoundException e) {
        e.printStackTrace();
        GenericResponse<String> response = GenericResponse.<String>builder()
                .status(HttpStatus.NOT_FOUND)
                .message(e.getMessage())
                .payload(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<GenericResponse<String>> handleCuentaAlreadyExistsException(RecordAlreadyExistsException e) {
        e.printStackTrace();
        GenericResponse<String> response = GenericResponse.<String>builder()
                .status(HttpStatus.CONFLICT)
                .message(e.getMessage())
                .payload(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);

    }

    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<GenericResponse<String>> handleInvalidField(InvalidFieldException e) {
        e.printStackTrace();
        GenericResponse<String> response =  GenericResponse.<String>builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(e.getMessage())
                .payload(null)
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RuntimeException.class)
    public GenericResponse<String> handleRuntimeException(RuntimeException e) {
        e.printStackTrace();
        return GenericResponse.<String>builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("Ha ocurrido un error inesperado, por favor contactar a sistemas")
                .build();
    }
}
