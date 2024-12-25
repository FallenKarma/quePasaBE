package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;

import java.util.List;

public interface ChatPort {
    List<Chat> getUserChats(long id);

    int getUnreadMessages(long userId, long chatId);

    String getChatName(long userId, long chatId);

    boolean addChat(Long adminId, List<Long> participantIds, String chatName, ChatType chatType);
}
