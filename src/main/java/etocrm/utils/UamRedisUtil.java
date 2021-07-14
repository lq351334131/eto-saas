package etocrm.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;

@Component
public class UamRedisUtil {

    @Resource
    private RedisTemplate redisTemplate;

    public void deletePrefixKey(String prefixKey) {
        Set<String> keys = redisTemplate.keys(prefixKey.concat("*"));
        redisTemplate.delete(keys);
    }
}