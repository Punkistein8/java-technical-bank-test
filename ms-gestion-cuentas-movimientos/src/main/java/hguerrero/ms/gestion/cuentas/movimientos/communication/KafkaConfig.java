package hguerrero.ms.gestion.cuentas.movimientos.communication;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic clienteRequestTopic() {
        return new NewTopic("cliente-request", 1, (short) 1);
    }

    @Bean
    public NewTopic clienteResponseTopic() {
        return new NewTopic("cliente-response", 1, (short) 1);
    }
}