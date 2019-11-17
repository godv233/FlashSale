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
     * set对象，没有过期时间
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key,Object value){
        boolean result=false;
        try {
            redisTemplate.opsForValue().set(key,value);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    /**
     * set对象，并且设置过期时间
     * @param key
     * @param value
     * @param expireTime
     * @return
     */
    public boolean set(String key,Object value,Long expireTime){
        boolean result=false;
        try {
            redisTemplate.opsForValue().set(key,value);
            redisTemplate.expire(key,expireTime.longValue(), TimeUnit.SECONDS);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 通过key，得到对象
     */
    public Object get(String key){
        Object result=null;
        if (StringUtils.isEmpty(key))
            return null;
        try {
            Object result = redisTemplate.opsForValue().get(key);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
