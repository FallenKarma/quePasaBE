package uib.swarchitecture.quepasa.domain.models;

import uib.swarchitecture.quepasa.domain.models.enums.MessageType;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
public class Message {
    private long id;
    private String content;
    private LocalDateTime timestamp;
    private MessageType type;
    private User author;
}
