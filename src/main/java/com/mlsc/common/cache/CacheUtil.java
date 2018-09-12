package com.mlsc.common.cache;

import com.mlsc.common.cache.memory.MemoryCache;

import java.util.concurrent.TimeUnit;

/**
 * 缓存工具类
 */
public class CacheUtil {
    /**
     * 创建缓存对象
     */
    private static Cache<Object, Object> cache = new MemoryCache<Object, Object>();

    /**
     * 存放缓存
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    public static void put(Object key, Object value, long time, TimeUnit unit){
        cache.put(key,value,time,unit);
    }

    /**
     * 获取缓存
     * @param key
     * @return
     */
    public static Object get(Object key){
        return cache.get(key);
    }

}
