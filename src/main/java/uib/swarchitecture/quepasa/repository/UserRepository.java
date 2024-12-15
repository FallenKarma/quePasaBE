package uib.swarchitecture.quepasa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uib.swarchitecture.quepasa.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Puedes añadir métodos de consulta personalizados aquí si lo necesitas
    User findByUsername(String username); // Ejemplo: Buscar un usuario por su nombre
}
