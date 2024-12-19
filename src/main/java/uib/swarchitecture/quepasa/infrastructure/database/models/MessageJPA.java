package uib.swarchitecture.quepasa.infrastructure.database.models;

import jakarta.persistence.*;
import lombok.*;
import uib.swarchitecture.quepasa.infrastructure.database.models.enums.MessageTypeJPA;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "message")
@Getter
@Setter
@NoArgsConstructor
@ToString  // Lombok genera getters, setters, toString y constructor sin parametros
@EqualsAndHashCode(of = "id")  // Solo usa 'id' para equals y hashCode
public class MessageJPA {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MessageTypeJPA type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    private UserJPA author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="read",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserJPA> readers;
}