package uib.swarchitecture.quepasa.domain.model;

import java.time.LocalDateTime;

public class Message {
    private String id;
    private String sender;
    private String reciver;
    private String content;
    private LocalDateTime timestamp;

    public Message(String id, String sender, String reciver, String content, LocalDateTime timestamp) {
        this.id = id;
        this.sender = sender;
        this.reciver = reciver;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
