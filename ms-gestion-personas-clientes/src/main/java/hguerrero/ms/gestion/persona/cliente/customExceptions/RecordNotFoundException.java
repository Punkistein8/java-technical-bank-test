package hguerrero.ms.gestion.persona.cliente.customExceptions;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String message) {
        super(message);
    }
}
