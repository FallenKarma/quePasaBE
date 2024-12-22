package uib.swarchitecture.quepasa.infrastructure.database.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.enums.MessageType;
import uib.swarchitecture.quepasa.domain.ports.MessagePort;
import uib.swarchitecture.quepasa.infrastructure.database.models.MessageJPA;
import uib.swarchitecture.quepasa.infrastructure.database.models.enums.MessageTypeJPA;
import uib.swarchitecture.quepasa.infrastructure.database.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Component
public class MessageAdapter implements MessagePort {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageAdapter(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Optional<Message> getLastMessage(long chatId) {
        Pageable pageable = PageRequest.of(0, 1); // Página 0, tamaño de página 1
        List<MessageJPA> messages = messageRepository.findLastMessagesByChatId(chatId, pageable);
        Optional<MessageJPA> lastMessage = messages.isEmpty() ? Optional.empty() : Optional.of(messages.getFirst());

        return lastMessage.map(this::convertToMessage);
    }

    private Message convertToMessage(MessageJPA messageJPA) {
        return Message.builder()
                .id(messageJPA.getId())
                .content(messageJPA.getContent())
                .timestamp(messageJPA.getTimestamp())
                .type(convertMessageType(messageJPA.getType()))
                .build();
    }

    private MessageType convertMessageType(MessageTypeJPA messageTypeJPA) {
        return switch (messageTypeJPA) {
            case TEXT -> MessageType.TEXT;
            case IMAGE -> MessageType.IMAGE;
        };
    }
}
