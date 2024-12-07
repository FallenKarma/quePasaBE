package uib.swarchitecture.quepasa.application.usecase;

import uib.swarchitecture.quepasa.domain.model.Message;
import uib.swarchitecture.quepasa.domain.respository.MessageRepository;

import java.time.LocalDateTime;

public class SendMessage {
    private final MessageRepository messageRepository;

    public SendMessage(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    public Message execute(String sender, String reciver, String content){
        Message message = new Message(
                null, sender, reciver, content, LocalDateTime.now()
        );
        return messageRepository.save(message);
    }
}
