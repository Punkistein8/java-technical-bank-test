package hguerrero.ms.gestion.persona.cliente.unit;

import db.bank.core.entity.Cliente;
import hguerrero.ms.gestion.persona.cliente.base.GenericResponse;
import hguerrero.ms.gestion.persona.cliente.controller.ClienteController;
import hguerrero.ms.gestion.persona.cliente.service.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClienteControllerTests {
    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_Success_ReturnsSavedCliente() throws Exception {
        // Arrange
        Cliente clienteInput = new Cliente();
        clienteInput.setNombres("Juan Pérez");
        clienteInput.setEdad(30);
        clienteInput.setIdentificacion("1717171712");
        clienteInput.setDireccion("Calle Falsa 123");
        clienteInput.setTelefono("1234567890");
        clienteInput.setContrasenia("securePass123");
        GenericResponse<Cliente> response = GenericResponse.<Cliente>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clienteInput)
                .build();
        when(clienteService.save(clienteInput)).thenReturn(response);

        // Act
        GenericResponse<Cliente> result = clienteController.save(clienteInput);

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getStatus().value());
        assertEquals("Success", result.getMessage());
        assertEquals(clienteInput, result.getPayload());
        verify(clienteService, times(1)).save(clienteInput);
    }

    @Test
    void findByEstado_ReturnsFilteredClientes() {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setNombres("Juan Pérez");
        cliente.setEdad(30);
        cliente.setIdentificacion("1717171712");
        cliente.setDireccion("Calle Falsa 123");
        cliente.setTelefono("0987654321");
        cliente.setContrasenia("securePass123");

        List<Cliente> clientesActivos = Arrays.asList(
                cliente
        );
        GenericResponse<List<Cliente>> response = GenericResponse.<List<Cliente>>builder()
                .status(HttpStatusCode.valueOf(HttpStatus.OK.value()))
                .message("Success")
                .payload(clientesActivos)
                .build();
        when(clienteService.findByEstado("true")).thenReturn(response);

        // Act
        GenericResponse<List<Cliente>> result = clienteController.findByEstado("true");

        // Assert
        assertEquals(HttpStatus.OK.value(), result.getStatus().value());
        assertEquals("Success", result.getMessage());
        assertEquals(1, result.getPayload().size());
        verify(clienteService, times(1)).findByEstado("true");
    }

}
