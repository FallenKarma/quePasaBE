package uib.swarchitecture.quepasa.domain.services;

import uib.swarchitecture.quepasa.domain.models.Token;
import uib.swarchitecture.quepasa.domain.models.User;

public interface AuthService {
    Token register(User user);
    Token authenticate(User user);
    Token refreshToken(String authentication);
}
