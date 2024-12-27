package uib.swarchitecture.quepasa.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.models.enums.MessageType;
import uib.swarchitecture.quepasa.domain.ports.AuthPort;
import uib.swarchitecture.quepasa.domain.ports.ChatPort;
import uib.swarchitecture.quepasa.domain.ports.MessagePort;
import uib.swarchitecture.quepasa.infrastructure.web.models.SendMessageRequest;
import uib.swarchitecture.quepasa.domain.exceptions.MessageNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final AuthPort authPort;
    private final MessagePort messagePort;
    private final ChatPort chatPort;

    @Autowired
    public MessageServiceImpl(AuthPort authPort, MessagePort messagePort, ChatPort chatPort) {
        this.authPort = authPort;
        this.messagePort = messagePort;
        this.chatPort = chatPort;
    }

    @Override
    public Message sendMessage(long chatId, SendMessageRequest data, String authentication) {
        long userId = authPort.getIdFromAuthentication(authentication);

        if (userId <= 0){
            throw new IllegalArgumentException("userId invalido");
        }
        // Guardar el mensaje
        return messagePort.saveMessage(data, chatId, userId);
    }

    @Override
    public List<Message> getMessagesFromChat(long chatId) {
        if (chatId <= 0){
            throw new IllegalArgumentException("chatid invalido");
        }

        // Obtener mensajes del chat
        return messagePort.getMessagesFromChat(chatId);
    }

    @Override
    public Message getMessageById(long messageId) {
        Optional<Message> messageOptional = messagePort.getMessageById(messageId);

        if (messageOptional.isEmpty()) {
            throw new MessageNotFoundException(messageId);
        }

        return messageOptional.get();
    }

}
