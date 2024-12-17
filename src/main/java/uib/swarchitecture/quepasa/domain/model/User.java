package uib.swarchitecture.quepasa.domain.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data // Genera getters, setters, toString, equals, hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los campos
@Builder // Genera el patrón builder
public class User {
    private long id;
    private String username;
    private String password;
    private String email;
}
