package dev.peermaute.mealsquare.authentication;

public interface AuthenticationHandler {
    String getBearerToken(String fullToken);
    boolean verifyToken(String bearerToken);
    String getUid(String bearerToken);
}
