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

import java.util.ArrayList;
import java.util.List;

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

            // Crear UserChat
            UserChat userChat = UserChat.builder()
                    .id(chat.getId())
                    .name(name)
                    .unreadMessages(unreadMessages)
                    .lastMessageTimestamp(lastMessage != null ? lastMessage.getTimestamp() : null)
                    .build();

            userChats.add(userChat);
        }

        // Ordenar chats por fecha del último mensaje
        userChats.sort(null);
        return userChats;
    }

    @Override
    public boolean createChat(CreateChatRequest request, String authentication) {
        // Obtener id del usuario administrador
        Long adminId = authPort.getIdFromAuthentication(authentication);

        // Obtener datos de la petición
        List<Long> participantIds = request.getUserIds();
        String chatName = request.getName();
        ChatType chatType = request.getType();

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
        try {
            boolean response = chatPort.addChat(adminId, participantIds, chatName, chatType);
            return response;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Server couldn't create the chat");
        }
    }
}
