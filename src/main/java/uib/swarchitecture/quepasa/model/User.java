package uib.swarchitecture.quepasa.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users") // Opcional: asigna el nombre de la tabla
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true) // Nombre de usuario único y no nulo
    private String username;

    @Column(nullable = false) // La contraseña no puede ser nula
    private String password;

    @Column(nullable = false, unique = true) // Email único y no nulo
    private String email;

    // Constructor vacío requerido por JPA
    public User() {}

    // Constructor completo
    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
