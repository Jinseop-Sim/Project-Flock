package fouriting.flockproject.utility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisUtil {
    private final RedisTemplate<String, Object> redisTemplate;

    public void setData(String key, String value){
        redisTemplate.opsForValue().set(key, value);
    }

    public void setDataWithExpiry(String key, String value, Long expiry){
        redisTemplate.opsForValue().set(key, value, expiry, TimeUnit.MILLISECONDS);
    }

    public String getData(String key){
        return (String) redisTemplate.opsForValue().get(key);
    }

    public void removeData(String key){
        redisTemplate.delete(key);
    }
}
