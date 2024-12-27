package uib.swarchitecture.quepasa.domain.models;

import lombok.Builder;
import uib.swarchitecture.quepasa.domain.models.enums.MessageType;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import uib.swarchitecture.quepasa.infrastructure.web.models.UserDTO;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
@Builder // Genera el patrón builder
public class Message implements Comparable<Message> {
    private long id;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;
    private UserDTO author;

    @Override
    public int compareTo(Message o) {
        return o.getTimestamp().compareTo(this.timestamp);
    }
}
