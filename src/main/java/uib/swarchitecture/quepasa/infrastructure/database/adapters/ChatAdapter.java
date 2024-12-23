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
    public boolean addChat(List<Long> adminIds, List<Long> participantsId, String chatName, ChatType chatType) {
        ChatJPA chat = new ChatJPA();
        List<UserJPA> participants = new ArrayList<>();
        List<UserJPA> admins = new ArrayList<>();

        // Llenar lista de participantes
        for (Long participantId : participantsId) {
            Optional<UserJPA> userOptional = userRepository.findById(participantId);
            if (userOptional.isPresent()) {
                participants.add(userOptional.get());
            } else {
                throw new IllegalArgumentException("Usuario participante con ID " + participantId + " no encontrado.");
            }
        }

        // Llenar lista de administradores
        for (Long adminId : adminIds) {
            Optional<UserJPA> userOptional = userRepository.findById(adminId);
            if (userOptional.isPresent()) {
                admins.add(userOptional.get());
            } else {
                throw new IllegalArgumentException("Usuario administrador con ID " + adminId + " no encontrado.");
            }
        }

        // Configurar los datos del chat
        chat.setParticipants(participants); // Asignar los participantes
        chat.setAdmins(admins);             // Asignar los administradores (debes tener este atributo en ChatJPA)
        chat.setName(chatName);         // Asignar el nombre del chat
        chat.setType(convertToChatTypeJPA(chatType));         // Asignar el tipo de chat

        // Guardar el chat en la base de datos
        chatRepository.save(chat);

        return true;
    }

    public ChatTypeJPA convertToChatTypeJPA(ChatType chatType) {
        switch (chatType) {
            case DIRECT:
                return ChatTypeJPA.DIRECT;
            case GROUP:
                return ChatTypeJPA.GROUP;
            default:
                throw new IllegalArgumentException("Tipo de chat desconocido: " + chatType);
        }
    }


}
