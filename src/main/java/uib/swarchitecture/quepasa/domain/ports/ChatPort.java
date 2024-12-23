package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.Chat;

import java.util.List;

public interface ChatPort {
    List<Chat> getUserChats(long id);
    int getUnreadMessages(long userId, long chatId);
    String getChatName(long userId, long chatId);

    // ChatUser[] getUserChats(long id);
}
