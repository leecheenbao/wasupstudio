package com.betball.cache.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedisUtil {

    @Resource(name = "kryoRedisTemplate")
    RedisTemplate redisTemplate;

    @Resource(name = "objectRedisTemplate")
    RedisTemplate redisStringTemplate;

    /**
     * 设置 String 类型 key-value
     *
     * @param keyFormat
     * @param value
     */
    public void set(String keyFormat, Object value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (毫秒单位)
     *
     * @param keyFormat
     * @param value
     * @param time      过期时间,秒单位
     */
    public void setExpire(String keyFormat, Object value, long time, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public void setBatchExpire(String keyFormat, Map<Object, Object> keyValues, long timeout) {
        if (!CollectionUtils.isEmpty(keyValues)) {
            redisTemplate.executePipelined((RedisCallback<Object>) connection -> {
                for (Map.Entry<Object, Object> e : keyValues.entrySet()) {
                    String key = String.format(keyFormat, e.getKey());
                    byte[] rawKey = redisTemplate.getKeySerializer().serialize(key);
                    byte[] rawValue = redisTemplate.getValueSerializer().serialize(e.getValue());

                    connection.setEx(rawKey, TimeoutUtils.toSeconds(timeout, TimeUnit.SECONDS), rawValue);
                }
                return null;
            });
        }
    }

    public void setObjectExpire(String keyFormat, Object value, long time, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisStringTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置 String 类型 key-value 并添加过期时间 (毫秒单位)
     *
     * @param keyFormat
     * @param value
     * @param time      过期时间,分钟单位
     */
    public void setMinutes(String keyFormat, Object value, long time, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
    }

    /**
     * 获取 String 类型 key 的过期时间 (毫秒单位)
     *
     * @param keyFormat
     */
    public long getExpire(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 获取 String 类型 key-value
     *
     * @param keyFormat
     * @return
     */
    public <T> T get(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return (T) redisTemplate.opsForValue().get(key);
    }

    public Object getObject(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisStringTemplate.opsForValue().get(key);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     *
     * @param keyFormat
     * @param number
     */
    public Long increment(String keyFormat, long number, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForValue().increment(key, number);
    }

    public Long incrementObject(String keyFormat, long number, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisStringTemplate.opsForValue().increment(key, number);
    }

    /**
     * 添加 key-value 只有在键不存在时,才添加
     *
     * @param keyFormat
     * @param value
     */
    public boolean setIfAbsent(String keyFormat, Object value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    /**
     * 如果 key 存在则覆盖,并返回旧值.
     * 如果不存在,返回null 并添加
     *
     * @param keyFormat
     * @param value
     * @return
     */
    public String getAndSet(String keyFormat, String value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return (String) redisTemplate.opsForValue().getAndSet(key, value);
    }


    /**
     * 给一个指定的 key 值附加过期时间
     *
     * @param keyFormat
     * @param time
     * @param type
     * @return
     */
    public boolean expire(String keyFormat, long time, TimeUnit type, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.boundValueOps(key).expire(time, type);
    }

    /**
     * 删除 key-value
     *
     * @param keyFormat
     * @return
     */
    public void delete(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.delete(key);
    }

    public void deleteObject(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisStringTemplate.delete(key);
    }

    public void deleteWildcardObject(String keyFormat, Object... keyValues) {

        Set<String> keys = (Set<String>) redisStringTemplate.execute((RedisCallback<Set<String>>) redisConnection -> {
            Set<String> k = new HashSet<>();
            Cursor<byte[]> cursor = redisConnection.scan(new ScanOptions.ScanOptionsBuilder().match(keyFormat + "*").build());
            while (cursor.hasNext()) {
                k.add(new String(cursor.next()));
            }
            return k;
        });
        for (String key : keys) {
            redisStringTemplate.delete(key);
        }
    }

    public void deleteWildcard(String keyFormat, Object... keyValues) {

        Set<String> keys = (Set<String>) redisTemplate.execute((RedisCallback<Set<String>>) redisConnection -> {
            Set<String> k = new HashSet<>();
            Cursor<byte[]> cursor = redisConnection.scan(new ScanOptions.ScanOptionsBuilder().match(keyFormat + "*").build());
            while (cursor.hasNext()) {
                k.add(new String(cursor.next()));
            }
            return k;
        });
        for (String key : keys) {
            redisTemplate.delete(key);
        }
    }


    /**
     * 获得set中所有元素
     */
    public Set getSet(String keyFormat) {
        String key = String.format(keyFormat);
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 添加 set 元素
     *
     * @param keyFormat
     * @param values
     * @return
     */
    public Long add(String keyFormat, String values, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForSet().add(key, values);
    }

    /**
     * 添加多个 set 元素
     *
     * @param setKey
     * @param values
     * @param <V>
     * @return
     */
    public <V> Long addValues(String setKey, List<V> values) {
        return redisTemplate.opsForSet().add(setKey, values.toArray());
    }

    /**
     * 删除 set 元素
     *
     * @param setkey
     * @param value
     * @return
     */
    public Long removeSet(String setkey, Object value) {
        String key = String.format(setkey);
        return redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 添加 ZSet 元素
     *
     * @param keyFormat
     * @param value
     * @param score
     */
    public boolean addZSet(String keyFormat, Object value, double score, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 获取索引区间内的排序结果集合(从0开始,从大到小,只有列名)
     *
     * @param keyFormat
     * @param start
     * @param end
     * @return
     */
    public Set<Object> reverseRange(String keyFormat, long start, long end, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * 获取索引区间内的排序结果集合
     *
     * @param keyFormat
     * @param start
     * @param end
     * @return
     */
    public Set<Object> rangeSet(String keyFormat, long start, long end, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 移除分数区间的所有成员
     *
     * @param keyFormat
     * @param start
     * @param end
     * @param keyValues
     * @return
     */
    public long removeRangeByScore(String keyFormat, long start, long end, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
    }

    /**
     * 返回指定分数区间 [min,max] 的元素个数
     *
     * @param keyFormat
     * @param min
     * @param max
     * @return
     */
    public long countZSet(String keyFormat, double min, double max, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().count(key, min, max);
    }

    /**
     * 返回 ZSet 集合成员数
     *
     * @param keyFormat
     * @param keyValues
     * @return
     */
    public long countZcard(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().zCard(key);
    }

    /**
     * 移除索引区间内的集合
     *
     * @param keyFormat
     * @param start
     * @param end
     * @param keyValues
     * @return
     */
    public long removeRange(String keyFormat, long start, long end, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().removeRange(key, start, end);
    }

    /**
     * 移除 Zset 单个元素
     *
     * @param keyFormat
     * @param value
     * @param keyValues
     * @return
     */
    public long removeZSet(String keyFormat, Object value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 移除并返回集合的一个随机元素
     *
     * @param keyFormat
     * @param keyValues
     * @return
     */
    public Object sPop(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 在集合最右侧添加元素
     *
     * @param keyFormat
     * @return
     */
    public Object rightPush(String keyFormat, Object... values) {
        return redisTemplate.opsForList().rightPush(keyFormat, values);
    }

    public Object rightPushOneByOPne(String keyFormat, Object values) {
        return redisTemplate.opsForList().rightPush(keyFormat, values);
    }

    public List<Object> getElementsByRange(String keyFormat, long start, long end, Object... keyValues) {
        return redisTemplate.opsForList().range(String.format(keyFormat, keyValues), start, end);
    }

    /**
     * 在集合最左侧添加元素(单元素)
     *
     * @param keyFormat
     * @return
     */
    public Object leftPush(String keyFormat, Object value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 移除最左元素
     *
     * @param keyFormat
     * @param keyValues
     * @return
     */
    public Object leftPop(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 判断是否有对应的key
     *
     * @param keyFormat
     * @param keyValues
     * @return
     */
    public boolean exists(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.hasKey(key);
    }

    public boolean objectExists(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisStringTemplate.hasKey(key);
    }

    /**
     * 哈希获取所有给定字段的值
     *
     * @param keyFormat
     * @param keyValues
     * @return
     */
    public Map<Object, Object> hGetAll(String keyFormat, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 哈希指定的字段是否存在
     *
     * @param keyFormat
     * @param field
     * @param keyValues
     * @return
     */
    public boolean hExists(String keyFormat, String field, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForHash().hasKey(key, field);
    }

    /**
     * 哈希添加
     *
     * @param keyFormat
     * @param hashKey
     * @param value
     * @param keyValues
     */
    public void hmSet(String keyFormat, Object hashKey, Object value, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 哈希获取数据
     *
     * @param keyFormat
     * @param hashKey
     * @param keyValues
     * @return
     */
    public Object hmGet(String keyFormat, Object hashKey, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 哈希删除数据
     *
     * @param keyFormat
     * @param hashKey
     * @param keyValues
     * @return
     */
    public Object hmDel(String keyFormat, Object hashKey, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 哈希指定字段整数值增加增量
     *
     * @param keyFormat
     * @param field
     * @param increment
     * @param keyValues
     * @return
     */
    public Long hIncrBy(String keyFormat, Object field, long increment, Object... keyValues) {
        String key = String.format(keyFormat, keyValues);
        return redisTemplate.opsForHash().increment(key, field, increment);
    }

    public long getAndIncrFromHash(String keyFormat, String field, Object... keyValues) {
        long result = 0;
        if (this.hExists(keyFormat, field, keyValues)) {
            return this.hIncrBy(keyFormat, field, 1, keyValues);
        }
        this.hmSet(keyFormat, field, "1", keyValues);
        return result;
    }

    public long getAndIncrFromHash(String keyFormat, String field, int time, TimeUnit unit, Object... keyValues) {
        long result = 0;
        if (this.hExists(keyFormat, field, keyValues)) {
            return this.hIncrBy(keyFormat, field, 1, keyValues);
        }
        this.hmSet(keyFormat, field, "1", keyValues);
        this.expire(keyFormat, time, unit, keyValues);
        return result;
    }

    public String getStringFromHash(String keyFormat, String field, Object... keyValues) {
        if (!this.hExists(keyFormat, field, keyValues)) {
            return null;
        }
        String json = (String) this.hmGet(keyFormat, field, keyValues);
        return json == null ? null : JSON.parseObject(json, String.class);
    }

    /**
     * 将脚本 script 添加到脚本缓存中，但并不立即执行这个脚本
     *
     * @param script
     * @param <T>
     * @return
     */
    public <T> T scriptLoad(String script) {
        return (T) redisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection) {
                return connection.scriptLoad(script.getBytes());
            }
        });
    }

    private byte[][] getByteParams(String... params) {
        byte[][] p = new byte[params.length][];
        for (int i = 0; i < params.length; i++) {
            p[i] = params[i].getBytes();
        }
        return p;
    }

    /**
     * 对 Lua 脚本进行求值
     *
     * @param scriptSha
     * @param resultType
     * @param args
     * @param <T>
     * @return
     */
    public <T> T evalsha(String scriptSha, Class<T> resultType, String... args) {
        return (T) redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) {
                ReturnType returnType = null;
                if (resultType == null) {
                    returnType = ReturnType.STATUS;
                } else if (resultType.isAssignableFrom(String.class)) {
                    returnType = ReturnType.STATUS;
                } else if (resultType.isAssignableFrom(List.class)) {
                    returnType = ReturnType.MULTI;
                } else if (resultType.isAssignableFrom(Boolean.class)) {
                    returnType = ReturnType.BOOLEAN;
                } else if (resultType.isAssignableFrom(Long.class)) {
                    returnType = ReturnType.INTEGER;
                }
                System.out.println(args);
                return connection.evalSha(scriptSha, returnType, args.length, getByteParams(args));
            }
        });
    }

    /**
     * 获取所有key
     */
    public Set<String> getKeys(String key) {
        Set<String> keys = redisTemplate.keys(key);
        return keys;
    }

}
