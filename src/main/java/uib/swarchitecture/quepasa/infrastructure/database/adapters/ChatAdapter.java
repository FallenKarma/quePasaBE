package uib.swarchitecture.quepasa.infrastructure.database.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;
import uib.swarchitecture.quepasa.domain.ports.ChatPort;
import uib.swarchitecture.quepasa.infrastructure.database.models.ChatJPA;
import uib.swarchitecture.quepasa.infrastructure.database.models.UserJPA;
import uib.swarchitecture.quepasa.infrastructure.database.models.enums.ChatTypeJPA;
import uib.swarchitecture.quepasa.infrastructure.database.repository.ChatRepository;
import uib.swarchitecture.quepasa.infrastructure.database.repository.MessageRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatAdapter implements ChatPort {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;

    @Autowired
    public ChatAdapter(ChatRepository chatRepository, MessageRepository messageRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Chat> getUserChats(long id) {
        List<ChatJPA> chatJPAS = chatRepository.findByParticipants_Id(id);
        List<Chat> chats = new ArrayList<>();

        for (ChatJPA chatJPA : chatJPAS) {
            Chat chat = Chat.builder()
                    .id(chatJPA.getId())
                    .name(chatJPA.getName())
                    .type(chatJPA.getType() == ChatTypeJPA.DIRECT ? ChatType.DIRECT : ChatType.GROUP)
                    .build();
            chats.add(chat);
        }

        return chats;
    }

    @Override
    public int getUnreadMessages(long userId, long chatId) {
        return messageRepository.countUnreadMessagesByChatIdAndUserId(chatId, userId);
    }

    @Override
    public String getChatName(long userId, long chatId) {
        // Obtener el chat
        ChatJPA chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("Chat with ID " + chatId + " not found"));

        // Obtener el nombre del grupo
        if (chat.getType() == ChatTypeJPA.GROUP) {
            return chat.getName();
        }

        // Obtener el otro participante
        return chat.getParticipants().stream()
                .filter(user -> user.getId() != userId)
                .findFirst()
                .map(UserJPA::getUsername)
                .orElseThrow(() -> new IllegalArgumentException("No other participant found in the direct chat with ID " + chatId));
    }

}
