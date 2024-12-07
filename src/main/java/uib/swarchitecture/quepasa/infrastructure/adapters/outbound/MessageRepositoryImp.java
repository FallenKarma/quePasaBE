package uib.swarchitecture.quepasa.infrastructure.adapters.outbound;

import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.model.Message;
import uib.swarchitecture.quepasa.domain.respository.MessageRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MessageRepositoryImp implements MessageRepository {
    private final List<Message> baseDatosSimulada = new ArrayList<>();

    @Override
    public Message save(Message mensaje) {
        mensaje.setId(UUID.randomUUID().toString());
        baseDatosSimulada.add(mensaje);
        return mensaje;
    }

    @Override
    public List<Message> obtenerPorDestinatario(String destinatario) {
        return baseDatosSimulada.stream()
                .filter(mensaje -> mensaje.getReciver().equals(destinatario))
                .toList();
    }

}
