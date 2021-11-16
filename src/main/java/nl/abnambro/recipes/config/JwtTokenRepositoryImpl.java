package nl.abnambro.recipes.config;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JwtTokenRepositoryImpl implements JwtTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    private final HashOperations hashOperations;

    public JwtTokenRepositoryImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void add(String username, String token) {
        hashOperations.put("USER", username, token);
    }

    @Override
    public String findToken(String username) {
        return (String) hashOperations.get("USER", username);
    }

    @Override
    public void deleteToken(String username) {
        hashOperations.delete("USER", username);
    }
}
