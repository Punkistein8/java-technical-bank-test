package hguerrero.ms.gestion.cuentas.movimientos;

import db.bank.core.entity.Cuenta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsGestionCuentasMovimientosApplicationTests {

    @LocalServerPort
    private int port = 8085;

    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createCuenta_ValidClient_ReturnsSavedCuenta() throws Exception {
        // Arrange
        Cuenta cuentaInput = new Cuenta();
        cuentaInput.setTipoCuenta("AHORRO");
        cuentaInput.setSaldoInicial(new BigDecimal("500.00"));
        cuentaInput.setEstado(true);
        cuentaInput.setClienteId(1L);

        HttpEntity<Cuenta> entity = new HttpEntity<>(cuentaInput, headers);

        // Act
        ResponseEntity<Cuenta> response = restTemplate.exchange(
                createURLWithPort("/cuentas/create"),
                HttpMethod.POST,
                entity,
                Cuenta.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getNumCuenta());
        assertEquals("AHORRO", response.getBody().getTipoCuenta());
        assertEquals(new BigDecimal("500.00"), response.getBody().getSaldoInicial());
        assertTrue(response.getBody().isEstado());
        assertEquals(1L, response.getBody().getClienteId());
    }

    @Test
    void createCuenta_InvalidClient_ReturnsNotFound() {
        // Arrange
        Cuenta cuentaInput = new Cuenta(); // Cliente no existe
        cuentaInput.setClienteId(null);
        cuentaInput.setTipoCuenta("AHORRO");
        cuentaInput.setSaldoInicial(new BigDecimal("500.00"));
        cuentaInput.setEstado(true);
        cuentaInput.setClienteId(999L);
        HttpEntity<Cuenta> entity = new HttpEntity<>(cuentaInput, headers);

        // Act
        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/cuentas/create"),
                HttpMethod.POST,
                entity,
                String.class
        );

        // Assert
        assertEquals(HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value()), response.getStatusCode());
        assertTrue(response.getBody().contains("No se pudo obtener información del cliente con ID: 999"));
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}
