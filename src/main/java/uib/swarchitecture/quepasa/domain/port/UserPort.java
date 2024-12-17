package uib.swarchitecture.quepasa.domain.port;

import uib.swarchitecture.quepasa.domain.model.User;

import java.util.List;
import java.util.Optional;

public interface UserPort {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public User addUser(User user);
    public Optional<User> getUserByUsername(String username);
}
