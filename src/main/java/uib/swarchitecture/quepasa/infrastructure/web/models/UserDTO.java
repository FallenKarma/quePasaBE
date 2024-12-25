package uib.swarchitecture.quepasa.infrastructure.web.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
@Builder // Genera el patrón builder
public class UserDTO {
    private long id;
    private String username;
}
