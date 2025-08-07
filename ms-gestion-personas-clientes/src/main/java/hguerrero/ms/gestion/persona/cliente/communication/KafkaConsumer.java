package hguerrero.ms.gestion.persona.cliente.communication;

import db.bank.core.dtos.ClienteRequestDTO;
import db.bank.core.dtos.ClienteResponseDTO;
import db.bank.core.entity.Cliente;
import db.bank.core.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import hguerrero.ms.gestion.persona.cliente.cons.KafkaCons;
import hguerrero.ms.gestion.persona.cliente.customExceptions.RecordNotFoundException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaConsumer {
    private final ClienteRepository clienteRepository;
    private final KafkaTemplate<String, ClienteResponseDTO> kafkaTemplate;

    @KafkaListener(topics = KafkaCons.CLIENTE_REQUEST_TOPIC, groupId = KafkaCons.CLIENTE_GROUP_ID)
    public void listenClienteRequest(ClienteRequestDTO request) {
        log.info("Recibida solicitud para clienteId: {} con correlationId: {}", request.getClienteId(), request.getCorrelationId());
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RecordNotFoundException("No se encontró el cliente con el id: " + request.getClienteId()));

        ClienteResponseDTO response = new ClienteResponseDTO();
        response.setCliente(cliente);
        response.setCorrelationId(request.getCorrelationId());

        kafkaTemplate.send(KafkaCons.CLIENTE_RESPONSE_TOPIC, String.valueOf(request.getClienteId()), response);
        log.info("Enviada respuesta para clienteId: {} con correlationId: {}, response: {}", request.getClienteId(),
                request.getCorrelationId(), response.toString());
    }
}
