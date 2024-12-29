package uib.swarchitecture.quepasa.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import uib.swarchitecture.quepasa.domain.exceptions.EmailAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.exceptions.UsernameAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.models.User;
import uib.swarchitecture.quepasa.domain.ports.UserPort;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserPort userPort;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserPort userPort, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userPort = userPort;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User addUser(User user) {
        if (userPort.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        if (userPort.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        // Cifrar la contraseña antes de guardarla
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        try {
            return userPort.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }

    public User getUserByUsername(String username) {
        Optional<User> userOptional = userPort.getUserByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return userOptional.get();
    }

    public User getUserById(long id) {
        Optional<User> userOptional = userPort.getUserById(id);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User " + id + " not found");
        }

        return userOptional.get();
    }
}