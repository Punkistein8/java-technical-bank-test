package hguerrero.ms.gestion.persona.cliente.customExceptions;

public class InvalidFieldException extends RuntimeException{
    public InvalidFieldException(String message) {
        super(message);
    }
}
