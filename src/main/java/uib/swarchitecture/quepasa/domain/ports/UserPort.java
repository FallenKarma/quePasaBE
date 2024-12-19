package uib.swarchitecture.quepasa.domain.ports;

import uib.swarchitecture.quepasa.domain.models.User;

import java.util.Optional;

public interface UserPort {
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
    public User addUser(User user);
    public Optional<User> getUserByUsername(String username);
    public Optional<User> getUserById(long id);
}
