package uib.swarchitecture.quepasa.domain.services;

import uib.swarchitecture.quepasa.domain.models.UserChat;

import java.util.List;

public interface ChatService {
    List<UserChat> getUserChats(String authentication);
}
