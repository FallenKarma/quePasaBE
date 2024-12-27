package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ChatPort {
    List<Chat> getUserChats(long id);

    int getUnreadMessages(long userId, long chatId);

    String getChatName(long userId, long chatId);

    LocalDateTime getChatCreationTimestamp(long chatId);

    Optional<Long> addChat(Long adminId, List<Long> participantIds, String chatName, ChatType chatType);

    boolean existsByIdAndUserId(long chatId, long userId);
}
