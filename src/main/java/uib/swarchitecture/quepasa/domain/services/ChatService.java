package uib.swarchitecture.quepasa.domain.services;

import uib.swarchitecture.quepasa.domain.models.UserChat;
import uib.swarchitecture.quepasa.infrastructure.web.models.CreateChatRequest;

import java.util.List;

public interface ChatService {
    List<UserChat> getUserChats(String authentication);
    boolean createChat(CreateChatRequest createChatRequest);
}
