package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Message;

import java.util.Optional;

public interface MessagePort {
    Optional<Message> getLastMessage(long chatId);
}
