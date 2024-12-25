package uib.swarchitecture.quepasa.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;
import uib.swarchitecture.quepasa.domain.ports.AuthPort;
import uib.swarchitecture.quepasa.domain.ports.ChatPort;
import uib.swarchitecture.quepasa.domain.ports.MessagePort;
import uib.swarchitecture.quepasa.infrastructure.web.models.CreateChatRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {

    private final AuthPort authPort;
    private final ChatPort chatPort;
    private final MessagePort messagePort;

    @Autowired
    public ChatServiceImpl(AuthPort authPort, ChatPort chatPort, MessagePort messagePort) {
        this.authPort = authPort;
        this.chatPort = chatPort;
        this.messagePort = messagePort;
    }

    @Override
    public List<UserChat> getUserChats(String authentication) {
        // Obtener id del usuario
        long id = authPort.getIdFromAuthentication(authentication);

        // Obtener chats del usuario
        List<Chat> chats = chatPort.getUserChats(id);

        List<UserChat> userChats = new ArrayList<>();

        for (Chat chat : chats) {
            // Obtener nombre del chat para el usuario (si es un chat directo no es solamente el nombre)
            String name = chatPort.getChatName(id, chat.getId());

            // Obtener número de mensajes no leídos
            int unreadMessages = chatPort.getUnreadMessages(id, chat.getId());

            // Obtener último mensaje del chat
            Message lastMessage = messagePort.getLastMessage(chat.getId()).orElse(null);

            // Obtener momento de creación del chat
            LocalDateTime createdAt = chatPort.getChatCreationTimestamp(chat.getId());

            // Obtener momento de la última acción en el chat
            LocalDateTime lastAction = lastMessage != null ? lastMessage.getTimestamp() : createdAt;

            // Crear UserChat
            UserChat userChat = UserChat.builder()
                    .id(chat.getId())
                    .name(name)
                    .unreadMessages(unreadMessages)
                    .lastActionTimestamp(lastAction)
                    .build();

            userChats.add(userChat);
        }

        // Ordenar chats por fecha del último mensaje
        userChats.sort(null);
        return userChats;
    }

    @Override
    public UserChat createChat(CreateChatRequest request, String authentication) {
        // Obtener id del usuario administrador
        Long adminId = authPort.getIdFromAuthentication(authentication);

        // Obtener datos de la petición
        List<Long> participantIds = request.getUserIds();
        ChatType chatType = request.getType();
        String chatName = chatType == ChatType.GROUP ? request.getName() : null;

        // Verificar los datos de la petición
        if (participantIds == null || participantIds.isEmpty()) {
            throw new IllegalArgumentException("Participants list is empty");
        } else if (chatType == null) {
            throw new IllegalArgumentException("Chat type is null");
        } else if (chatType == ChatType.DIRECT && participantIds.size() != 2) {
            throw new IllegalArgumentException("Direct chat must have exactly one participant");
        } else if (chatType == ChatType.GROUP && (chatName == null || chatName.isEmpty())) {
            throw new IllegalArgumentException("Group chat must have a name");
        }

        // Crear el chat
        Optional<Long> optionalId = chatPort.addChat(adminId, participantIds, chatName, chatType);

        // Verificar si se ha creado el chat
        if (optionalId.isEmpty()) {
            throw new NoSuchElementException("Chat could not be created");
        }
        Long chatId = optionalId.get();

        // Obtener fecha de creación
        LocalDateTime createdAt = chatPort.getChatCreationTimestamp(chatId);

        // Devolver el chat creado
        return UserChat.builder()
                .id(chatId)
                .name(chatName)
                .unreadMessages(0)
                .lastActionTimestamp(createdAt)
                .build();
    }
}
