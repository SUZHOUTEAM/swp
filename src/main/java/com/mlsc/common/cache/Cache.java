package com.mlsc.common.cache;

import java.util.concurrent.TimeUnit;


/**
 * 缓存接口
 *
 * @param <K>
 * @param <V>
 */
public interface Cache<K, V> {
    /**
     * 添加缓存对象
     * @param key
     * @param value
     * @param time
     * @param unit
     */
    void put(K key, V value, long time, TimeUnit unit);

    /**
     * 获取缓存对象
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 删除缓存对象
     * @param key
     */
    void remove(K key);
}
