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
import uib.swarchitecture.quepasa.domain.ports.UserPort;
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
    private final UserPort userPort;

    @Autowired
    public MessageServiceImpl(AuthPort authPort, MessagePort messagePort, ChatPort chatPort, UserPort userPort) {
        this.authPort = authPort;
        this.messagePort = messagePort;
        this.chatPort = chatPort;
        this.userPort = userPort;
    }

    @Override
    public Message sendMessage(long chatId, SendMessageRequest data, String authentication) {
        long userId = authPort.getIdFromAuthentication(authentication);
        Optional<User> user = userPort.getUserById(userId);

        if (user.isEmpty()){
            throw new IllegalArgumentException("invalid userId");
        }
        // Guardar el mensaje
        return messagePort.saveMessage(data, chatId, userId);
    }

    @Override
    public List<Message> getMessagesFromChat(long chatId, String authentication) {
        long userId = authPort.getIdFromAuthentication(authentication);

        if (!chatPort.existsById(chatId)){
            throw new IllegalArgumentException("invalid chatId");
        }

        // Obtener mensajes del chat
        return messagePort.getMessagesFromChat(chatId, userId);
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
