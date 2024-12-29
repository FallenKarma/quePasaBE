package uib.swarchitecture.quepasa.domain.services;

import uib.swarchitecture.quepasa.domain.models.User;

public interface UserService {
    User addUser(User user);
    User getUserByUsername(String username);
    User getUserById(long id);
}