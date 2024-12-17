package uib.swarchitecture.quepasa.infrastructure.database.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uib.swarchitecture.quepasa.domain.model.User;
import uib.swarchitecture.quepasa.domain.port.UserPort;
import uib.swarchitecture.quepasa.infrastructure.database.model.UserJPA;
import uib.swarchitecture.quepasa.infrastructure.database.repository.UserRepository;

@Component
public class UserAdapter implements UserPort {

    private UserRepository userRepository;

    @Autowired
    public UserAdapter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User addUser(User user) {
        // Convertir el User a UserJPA
        UserJPA userJPA = new UserJPA();
        userJPA.setUsername(user.getUsername());
        userJPA.setEmail(user.getEmail());
        userJPA.setPassword(user.getPassword());

        // Guardar el UserJPA en la base de datos
        UserJPA savedUserJPA = userRepository.save(userJPA);

        // Convertir de nuevo a User y devolverlo
        return User.builder()
                .id(savedUserJPA.getId())
                .username(savedUserJPA.getUsername())
                .email(savedUserJPA.getEmail())
                .password(savedUserJPA.getPassword())
                .build();
    }
}
