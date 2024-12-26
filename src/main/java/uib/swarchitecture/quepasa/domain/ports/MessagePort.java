package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.infrastructure.web.models.SendMessageRequest;

import java.util.List;
import java.util.Optional;

public interface MessagePort {
    Optional<Message> getLastMessage(long chatId);
    Optional<Message> getMessageById(long messageId);
    Message saveMessage(SendMessageRequest message, long chatId, long userId);
    List<Message> getMessagesFromChat(long chatId);
}
