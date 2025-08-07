package hguerrero.ms.gestion.persona.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"hguerrero.ms.gestion.persona.cliente", "db.bank.core"})
@EnableJpaRepositories(basePackages = {"db.bank.core.repository"})
@EntityScan(basePackages = {"db.bank.core.entity"})
public class MsGestionPersonaClienteApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsGestionPersonaClienteApplication.class, args);
    }

}
