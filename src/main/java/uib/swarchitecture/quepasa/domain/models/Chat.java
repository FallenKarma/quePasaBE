package uib.swarchitecture.quepasa.domain.models;

import uib.swarchitecture.quepasa.domain.models.enums.ChatType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
public class Chat {
    private long id;
    private String name;
    private ChatType type;
}
