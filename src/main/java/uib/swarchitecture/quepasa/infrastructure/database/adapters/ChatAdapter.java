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
import uib.swarchitecture.quepasa.infrastructure.database.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ChatAdapter implements ChatPort {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatAdapter(ChatRepository chatRepository, MessageRepository messageRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
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

    @Override
    public boolean addChat(Long adminId, List<Long> participantsId, String chatName, ChatType chatType) {
        List<UserJPA> participants = new ArrayList<>();
        List<UserJPA> admins = new ArrayList<>();

        // Populate the list of participants
        for (Long participantId : participantsId) {
            Optional<UserJPA> userOptional = userRepository.findById(participantId);
            if (userOptional.isPresent()) {
                participants.add(userOptional.get());
            } else {
                throw new IllegalArgumentException("Participant user with ID " + participantId + " not found.");
            }
        }

        // Populate the list of admins
        Optional<UserJPA> adminOptional = userRepository.findById(adminId);
        if (adminOptional.isPresent()) {
            admins.add(adminOptional.get());
        } else {
            throw new IllegalArgumentException("Administrator user with ID " + adminId + " not found.");
        }

        // Crear el objeto ChatJPA
        ChatJPA chat = ChatJPA.builder()
                .name(chatName)
                .type(chatType == ChatType.DIRECT ? ChatTypeJPA.DIRECT : ChatTypeJPA.GROUP)
                .participants(participants)
                .admins(admins)
                .build();

        // Save the chat in the database
        chatRepository.save(chat);

        return true;
    }

}
