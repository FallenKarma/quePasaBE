package uib.swarchitecture.quepasa.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
@Builder // Genera el patrón builder
public class UserChat implements Comparable<UserChat>{
    private long id;
    private String name;
    private int unreadMessages;
    private LocalDateTime lastMessageTimestamp;


    @Override
    public int compareTo(UserChat other) {
        if (this.lastMessageTimestamp == null && other.lastMessageTimestamp == null) {
            return 0;
        }
        if (this.lastMessageTimestamp == null) {
            return 1;  // Si el mensaje de este ChatUser es null, va después
        }
        if (other.lastMessageTimestamp == null) {
            return -1; // Si el mensaje del otro ChatUser es null, va después
        }

        return other.lastMessageTimestamp.compareTo(this.lastMessageTimestamp); // Orden descendente
    }
}
