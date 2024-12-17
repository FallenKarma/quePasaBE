package uib.swarchitecture.quepasa.infrastructure.database.model;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;

@Entity
@Table(name = "users") // user es una palabra reservada
@Getter @Setter @NoArgsConstructor @ToString  // Lombok genera getters, setters, toString y constructor sin parametros
@EqualsAndHashCode(of = "id")  // Solo usa 'id' para equals y hashCode
public class UserJPA {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    private List<MessageJPA> messagesSent;

    @ManyToMany(mappedBy = "readers", fetch = FetchType.LAZY)
    private List<MessageJPA> read;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private List<ChatJPA> chats;

    @ManyToMany(mappedBy = "admins", fetch = FetchType.LAZY)
    private List<ChatJPA> adminChats;
}
