package uib.swarchitecture.quepasa.infrastructure.database.model;

import jakarta.persistence.*;
import lombok.*;
import uib.swarchitecture.quepasa.infrastructure.database.model.enums.ChatTypeJPA;

import java.util.List;

@Entity
@Table(name = "chat")
@Getter
@Setter
@NoArgsConstructor
@ToString  // Lombok genera getters, setters, toString y constructor sin parametros
@EqualsAndHashCode(of = "id")  // Solo usa 'id' para equals y hashCode
public class ChatJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = true)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatTypeJPA type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="participate",
            joinColumns = @JoinColumn(name = "chat_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    private List<UserJPA> participants;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="admins",
            joinColumns = @JoinColumn(name = "chat_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "user_id", nullable = false)
    )
    private List<UserJPA> admins;
}
