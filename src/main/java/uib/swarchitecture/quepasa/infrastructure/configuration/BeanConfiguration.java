package uib.swarchitecture.quepasa.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uib.swarchitecture.quepasa.application.usecase.SendMessage;
import uib.swarchitecture.quepasa.domain.respository.MessageRepository;

@Configuration
public class BeanConfiguration {

    @Bean
    public SendMessage sendMessage(MessageRepository mensajeRepository) {
        return new SendMessage(mensajeRepository);
    }
}
