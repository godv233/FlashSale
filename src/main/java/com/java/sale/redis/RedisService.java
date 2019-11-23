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
     * @param keyPrefix
     * @param key
     * @param value
     * @return
     */
    public boolean set( KeyPrefix keyPrefix,String key, Object value) {
        boolean result = false;
        String realKey=keyPrefix.getPrefix()+key;
        try {
            redisTemplate.opsForValue().set(realKey, value);
            redisTemplate.expire(realKey,keyPrefix.expireSeconds(), TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 通过key，get对象
     */
    public Object get(KeyPrefix prefix,String key) {
        Object result = null;
        //真正的key
        if (StringUtils.isEmpty(key))
            return null;
        String realKey=prefix.getPrefix()+key;
        try {
            result = redisTemplate.opsForValue().get(realKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
