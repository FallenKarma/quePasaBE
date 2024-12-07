package uib.swarchitecture.quepasa.domain.respository;

import uib.swarchitecture.quepasa.domain.model.Message;

import java.util.List;

public interface MessageRepository {
    Message save(Message mensaje);
    List<Message> obtenerPorDestinatario(String destinatario);
}
