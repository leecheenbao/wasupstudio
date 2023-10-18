package com.wasupstudio.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class RedisUtil {

    private final RedisTemplate<String, String> redisTemplate;

    public RedisUtil(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void setKeyWithExpiration(String key, String value, long timeout, TimeUnit timeUnit) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(key, value, timeout, timeUnit);
    }

    public void setKey(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public String getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key){
        redisTemplate.delete(key);
    }

    public void setExpire(String keyFormat, Object value, long time, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.opsForValue().set(key, (String) value, time, TimeUnit.SECONDS);
    }

}