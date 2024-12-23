package uib.swarchitecture.quepasa.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.Message;
import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.domain.ports.AuthPort;
import uib.swarchitecture.quepasa.domain.ports.ChatPort;
import uib.swarchitecture.quepasa.domain.ports.MessagePort;
import uib.swarchitecture.quepasa.infrastructure.web.models.CreateChatRequest;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {

    private AuthPort authPort;
    private ChatPort chatPort;
    private MessagePort messagePort;

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
    public boolean createChat( CreateChatRequest request ) {


        return chatPort.addChat(request.getAdminId(),request.getUsersId(), request.getName(), request.getType());
    }
}
