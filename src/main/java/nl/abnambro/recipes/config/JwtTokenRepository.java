package nl.abnambro.recipes.config;

public interface JwtTokenRepository {

    void add(String username, String token);

    String findToken(String username);

    void deleteToken(String username);
}
