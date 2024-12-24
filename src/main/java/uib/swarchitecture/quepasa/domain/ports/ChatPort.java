package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Chat;
import uib.swarchitecture.quepasa.domain.models.enums.ChatType;
import uib.swarchitecture.quepasa.infrastructure.web.models.CreateChatRequest;

import java.util.List;

public interface ChatPort {
    List<Chat> getUserChats(long id);
    int getUnreadMessages(long userId, long chatId);
    String getChatName(long userId, long chatId);
    boolean addChat(Long adminId, List<Long> participantsId, String chatName, ChatType chatType);
    // ChatUser[] getUserChats(long id);
}
