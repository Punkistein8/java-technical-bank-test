package db.bank.core.validators;

import db.bank.core.entity.Cliente;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ClienteValidator implements Validator {


    @Override
    public boolean supports(Class<?> clazz) {
        return Cliente.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Cliente cliente = (Cliente) target;

        validateIdentificacion(cliente.getIdentificacion(), errors);

        validateTelefono(cliente.getTelefono(), errors);
    }

    private void validateIdentificacion(String identificacion, Errors errors) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            errors.rejectValue("identificacion", "identificacion.empty", "La identificación no puede estar vacía");
            return;
        }

        if (!identificacion.matches("\\d{10}")) {
            errors.rejectValue("identificacion", "identificacion.invalid", "La identificación debe tener exactamente 10 dígitos");
            return;
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(identificacion.charAt(i));
            if (i % 2 == 0) {
                digit *= 2;
                if (digit > 9) digit -= 9;
            }
            sum += digit;
        }
        int checkDigit = (10 - (sum % 10)) % 10;
        int lastDigit = Character.getNumericValue(identificacion.charAt(9));
        if (checkDigit != lastDigit) {
            errors.rejectValue("identificacion", "identificacion.checksum", "La identificación no es válida según el dígito verificador");
        }
    }

    private void validateTelefono(String telefono, Errors errors) {
        if (telefono == null || telefono.trim().isEmpty()) {
            errors.rejectValue("telefono", "telefono.empty", "El teléfono no puede estar vacío");
            return;
        }

        if (!telefono.matches("09\\d{8}")) {
            errors.rejectValue("telefono", "telefono.invalid", "El teléfono debe comenzar con '09' y tener exactamente 10 dígitos");
        }
    }
}
