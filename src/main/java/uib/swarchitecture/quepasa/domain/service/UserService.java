package uib.swarchitecture.quepasa.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.exceptions.EmailAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.exceptions.UsernameAlreadyExistsException;
import uib.swarchitecture.quepasa.domain.model.User;
import uib.swarchitecture.quepasa.domain.port.UserPort;

@Component
public class UserService {

    private UserPort userPort;

    @Autowired
    public UserService(UserPort userPort) {
        this.userPort = userPort;
    }

    public User addUser(User user) {
        if (userPort.existsByEmail(user.getEmail())) {
            throw new EmailAlreadyExistsException(user.getEmail());
        }

        if (userPort.existsByUsername(user.getUsername())) {
            throw new UsernameAlreadyExistsException(user.getUsername());
        }

        try {
            return userPort.addUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user", e);
        }
    }
}