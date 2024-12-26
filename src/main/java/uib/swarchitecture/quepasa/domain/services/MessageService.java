package uib.swarchitecture.quepasa.domain.services;

import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.infrastructure.web.models.SendMessageRequest;

import java.util.List;

public interface MessageService {
    Message sendMessage(long chatId, SendMessageRequest content, String authentication);
    Message getMessageById(long messageId);
    List<Message> getMessagesFromChat(long chatId);
}
