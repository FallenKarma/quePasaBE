package uib.swarchitecture.quepasa.infrastructure.database.models;

import uib.swarchitecture.quepasa.infrastructure.database.models.enums.ChatTypeJPA;

import java.util.List;

public class ChatJPABuilder {
    private String name;
    private ChatTypeJPA type;
    private List<UserJPA> participants;
    private List<UserJPA> admins;

    public ChatJPABuilder name(String name) {
        this.name = name;
        return this;
    }

    public ChatJPABuilder type(ChatTypeJPA type) {
        this.type = type;
        return this;
    }

    public ChatJPABuilder participants(List<UserJPA> participants) {
        this.participants = participants;
        return this;
    }

    public ChatJPABuilder admins(List<UserJPA> admins) {
        this.admins = admins;
        return this;
    }

    public ChatJPA build() {
        ChatJPA chat = new ChatJPA();
        chat.setName(this.name);
        chat.setType(this.type);
        chat.setParticipants(this.participants);
        chat.setAdmins(this.admins);
        return chat;
    }
}
