package hguerrero.ms.gestion.cuentas.movimientos.communication;


import db.bank.core.dtos.ClienteResponseDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class KafkaPetitionManager {
    private static final KafkaPetitionManager INSTANCE = new KafkaPetitionManager();
    private final Map<String, CompletableFuture<ClienteResponseDTO>> responseFutures = new ConcurrentHashMap<>();

    // Constructor privado para evitar instanciación externa
    private KafkaPetitionManager() {}

    // Método para obtener la instancia única
    public static KafkaPetitionManager getInstance() {
        return INSTANCE;
    }

    // Registrar un nuevo future para una solicitud
    public CompletableFuture<ClienteResponseDTO> registerResponse(String correlationId) {
        CompletableFuture<ClienteResponseDTO> future = new CompletableFuture<>();
        responseFutures.put(correlationId, future);
        log.debug("Registrado future para correlationId: {}", correlationId);
        return future;
    }

    // Completar un future cuando llega una respuesta
    public void completeResponse(String correlationId, ClienteResponseDTO response) {
        CompletableFuture<ClienteResponseDTO> future = responseFutures.remove(correlationId);
        if (future != null) {
            future.complete(response);
            log.debug("Completado future para correlationId: {}", correlationId);
        } else {
            log.warn("No se encontró future para correlationId: {}", correlationId);
        }
    }


}
