package hguerrero.ms.gestion.persona.cliente.communication;

import db.bank.core.dtos.ClienteLogicalDeleteDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import hguerrero.ms.gestion.persona.cliente.cons.KafkaCons;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, ClienteLogicalDeleteDTO> kafkaTemplate;

    public void sendEliminacionLogica(Long clienteId) {
        ClienteLogicalDeleteDTO mensaje = new ClienteLogicalDeleteDTO();
        mensaje.setClienteId(clienteId);
        kafkaTemplate.send(KafkaCons.TOPIC_ELIMINACION_LOGICA, String.valueOf(clienteId), mensaje);
        log.info("Mensaje de eliminación lógica enviado a Kafka para clienteId: {}", clienteId);
    }
}
