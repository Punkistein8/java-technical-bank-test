package hguerrero.ms.gestion.persona.cliente.unit;

import db.bank.core.entity.Cliente;
import db.bank.core.enums.Genero;
import db.bank.core.validators.ClienteValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import static org.junit.jupiter.api.Assertions.*;

public class ClienteTests {

    private Cliente cliente;
    private ClienteValidator validator;
    private Errors errors;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        validator = new ClienteValidator();
        errors = new BeanPropertyBindingResult(cliente, "cliente");
    }

    @Test
    void testValidCliente_NoErrors() {
        // Arrange
        cliente.setNombres("Juan Pérez");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setEdad(30);
        cliente.setIdentificacion("1714417753");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("0987654321");
        cliente.setContrasenia("securePass123");

        // Act
        validator.validate(cliente, errors);

        // Assert
        assertFalse(errors.hasErrors(), "No debe haber errores para un cliente válido");
    }

    @Test
    void testInvalidIdentificacion_WrongChecksum_AddsError() {
        // Arrange
        cliente.setNombres("María López");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setEdad(25);
        cliente.setIdentificacion("1717171716"); // Cédula inválida (dígito verificador incorrecto)
        cliente.setDireccion("Av. Siempre Viva");
        cliente.setTelefono("0998765432");
        cliente.setContrasenia("password123");

        // Act
        validator.validate(cliente, errors);

        // Assert
        assertTrue(errors.hasErrors(), "Debe haber errores por identificación inválida");
        assertEquals(1, errors.getErrorCount());
        assertEquals("identificacion.checksum", errors.getFieldError("identificacion").getCode());
        assertEquals("La identificación no es válida según el dígito verificador",
                errors.getFieldError("identificacion").getDefaultMessage());
    }

    @Test
    void testInvalidTelefono_WrongFormat_AddsError() {
        // Arrange
        cliente.setNombres("Carlos Ruiz");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setEdad(30);
        cliente.setIdentificacion("1717171712"); // Cédula válida
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("1234567890"); // Teléfono no comienza con "09"
        cliente.setContrasenia("securePass123");

        // Act
        validator.validate(cliente, errors);

        // Assert
        assertTrue(errors.hasErrors(), "Debe haber errores por teléfono inválido");
        assertEquals(1, errors.getErrorCount());
        assertEquals("telefono.invalid", errors.getFieldError("telefono").getCode());
        assertEquals("El teléfono debe comenzar con '09' y tener exactamente 10 dígitos",
                errors.getFieldError("telefono").getDefaultMessage());
    }

    @Test
    void testNullFields_AddsMultipleErrors() {
        // Arrange: Todos los campos nulos o vacíos
        cliente.setNombres(null);
        cliente.setGenero(null);
        cliente.setEdad(null);
        cliente.setIdentificacion(null);
        cliente.setDireccion(null);
        cliente.setTelefono(null);
        cliente.setContrasenia(null);

        // Act
        validator.validate(cliente, errors);

        // Assert
        assertTrue(errors.hasErrors(), "Debe haber errores por campos nulos");
        assertEquals(2, errors.getErrorCount()); // Solo identificacion y telefono tienen validaciones personalizadas
        assertNotNull(errors.getFieldError("identificacion"), "Debe haber error en identificación");
        assertNotNull(errors.getFieldError("telefono"), "Debe haber error en teléfono");
    }
}
