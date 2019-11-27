package com.java.sale.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author 曾伟
 * @date 2019/11/16 10:09
 */
@Service
public class RedisService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * set
     *
     * @param keyPrefix
     * @param key
     * @param value
     * @return
     */
    public boolean set(KeyPrefix keyPrefix, String key, Object value) {
        boolean result = false;
        String realKey = keyPrefix.getPrefix() + key;
        try {
            redisTemplate.opsForValue().set(realKey, value);
            if (keyPrefix.expireSeconds() > 0) {
                redisTemplate.expire(realKey, keyPrefix.expireSeconds(), TimeUnit.SECONDS);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过key，get对象
     */
    public Object get(KeyPrefix prefix, String key) {
        Object result = null;
        //真正的key
        if (StringUtils.isEmpty(key))
            return null;
        String realKey = prefix.getPrefix() + key;
        try {
            result = redisTemplate.opsForValue().get(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 减1
     *
     * @param prefix
     * @param key
     * @return
     */
    public long decrease(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        if (StringUtils.isEmpty(realKey)) return 0;
        long result = 0;
        try {
            result = redisTemplate.opsForValue().decrement(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return result;
        }
    }

    public boolean exists(KeyPrefix prefix, String key) {
        String realKey = prefix.getPrefix() + key;
        if (StringUtils.isEmpty(realKey)) return false;
        return redisTemplate.hasKey(realKey);
    }

    /**
     * 删除
     * @param verifyCode
     * @param s
     */
    public boolean delete(MiaoshaKey verifyCode, String key) {
        String realKey=verifyCode.getPrefix()+key;
        Boolean delete = redisTemplate.delete(realKey);
        return delete;

    }

    /**
     * 加1
     * @param withExpire
     * @param key
     */
    public void incr(AccessKey withExpire, String key) {
        String realKey=withExpire.getPrefix()+key;
        redisTemplate.opsForValue().increment(realKey);
    }
}
