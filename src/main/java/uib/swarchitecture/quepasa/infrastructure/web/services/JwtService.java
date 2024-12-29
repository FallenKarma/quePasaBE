package uib.swarchitecture.quepasa.infrastructure.web.services;

import uib.swarchitecture.quepasa.infrastructure.web.models.Token;
import uib.swarchitecture.quepasa.domain.models.User;

import java.util.Date;

public interface JwtService {
    // METHODS TO GENERATE TOKENS
    Token generateToken (final User user);
    String generateAccessToken(final User user);
    String generateRefreshToken(final User user);
    // METHODS TO EXTRACT DATA FROM TOKENS
    long extractUserId(final String token);
    Date extractExpiration(final String token);
    // UTIL METHODS
    boolean isTokenValid(final String token, final User user);
}
