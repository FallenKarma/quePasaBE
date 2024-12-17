package uib.swarchitecture.quepasa.infrastructure.database.repository;

import org.springframework.data.repository.CrudRepository;
import uib.swarchitecture.quepasa.infrastructure.database.model.UserJPA;

public interface UserRepository extends CrudRepository<UserJPA, Long> {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
