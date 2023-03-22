package com.example.backstage.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author HuaRunSheng
 * @date 2022/10/23 15:04
 * @description :
 */
@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向redis存值
     * @param key: 键
     * @param value: 值
     * @return :是否存入成功
     */
    public boolean setValue(String key, Object value){
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e){
            log.error("向redis中存入值时异常-->{}", e.getMessage());
            return false;
        }
    }

    /**
     * 向redis中存值并指定过期时间
     * @param key: 键
     * @param value: 值
     * @param time: 时间,分为单位
     * @return :是否存入成功
     */
    public boolean setValueTime(String key, Object value, long time){
        try {
            if (time > 0){
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.MINUTES);
            }else{
                redisTemplate.opsForValue().set(key, value);
            }

        } catch (Exception e){
            log.error("设置缓存并指定过期事件异常-->{}", e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 根据key获取redis中的值
     * @param key
     * @return
     */
    public Object getValue(String key){
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public void delKey(String... keys){
        if (keys != null && keys.length > 0){
            if (keys.length == 1){
                redisTemplate.delete(keys[0]);
            }else{
                for (int i = 0; i < keys.length; i++) {
                    redisTemplate.delete(keys[i]);
                }
            }
        }
    }

    public boolean hasKey(String key){
        try {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        } catch (Exception e){
            log.error("redis值不存在-->{}", e.getMessage());
            return false;
        }
    }

    /**
     * 0代表永久有效,大于0就代表多少分钟失效
     * @param key
     * @return
     */
    public Long isExpire(String key){
        return redisTemplate.getExpire(key, TimeUnit.MINUTES);
    }

    /**
     * 给key加过期时间
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time){
        try {
            if (time > 0){
                redisTemplate.expire(key, time, TimeUnit.MINUTES);
            }
            return true;
        }catch (Exception e){
            log.error("给旧的缓存设置新的过期时间异常-->{}", e.getMessage());
            return false;
        }
    }

}
