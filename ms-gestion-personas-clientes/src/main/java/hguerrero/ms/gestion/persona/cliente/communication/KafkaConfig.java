package hguerrero.ms.gestion.persona.cliente.communication;

import hguerrero.ms.gestion.persona.cliente.cons.KafkaCons;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic clienteRequestTopic() {
        return new NewTopic(KafkaCons.CLIENTE_REQUEST_TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic clienteResponseTopic() {
        return new NewTopic(KafkaCons.CLIENTE_RESPONSE_TOPIC, 1, (short) 1);
    }
}
