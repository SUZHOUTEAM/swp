package com.mlsc.common.cache.memory;

import com.mlsc.common.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by yinxl on 2017/6/30.
 */
@SuppressWarnings("unchecked")
public class MemoryCache<K, V> implements Cache<K, V> {
    private final static Logger logger = LoggerFactory.getLogger(MemoryCache.class);

    private ConcurrentMap<K, V> cacheObjMap = new ConcurrentHashMap<K, V>();

    private DelayQueue<DelayItem<Pair<K, V>>> q = new DelayQueue<DelayItem<Pair<K, V>>>();

    private Thread daemonThread;

    public MemoryCache() {

        Runnable daemonTask = new Runnable() {
            @Override
            public void run() {
                daemonCheck();
            }
        };

        daemonThread = new Thread(daemonTask);
        daemonThread.setDaemon(true);
        daemonThread.setName("MemoryCache Daemon");
        daemonThread.start();
    }

    private void daemonCheck() {

        if (logger.isErrorEnabled())
            logger.info("cache service started.");

        for (; ; ) {
            try {
                DelayItem<Pair<K, V>> delayItem = q.take();
                if (delayItem != null) {
                    // 超时对象处理
                    Pair<K, V> pair = delayItem.getItem();
                    cacheObjMap.remove(pair.getFirst(), pair.getSecond()); // compare and remove
                }
            } catch (InterruptedException e) {
                if (logger.isErrorEnabled())
                    logger.error(e.getMessage(), e);
                break;
            }
        }

        if (logger.isErrorEnabled())
            logger.info("cache service stopped.");
    }

    // 添加缓存对象
    @Override
    public void put(K key, V value, long time, TimeUnit unit) {
        long nanoTime = TimeUnit.NANOSECONDS.convert(time, unit);
        DelayItem newValue = new DelayItem<Pair<K, V>>(new Pair<K, V>(key, value), nanoTime);
        V oldValue = cacheObjMap.put(key, value);
        if (oldValue != null)
            q.remove(newValue);

        q.put(newValue);
    }

    @Override
    public V get(K key) {
        return cacheObjMap.get(key);
    }

    @Override
    public void remove(K key) {
       V value =  cacheObjMap.get(key);
       if(value != null ){
           DelayItem delayItem = new DelayItem<Pair<K, V>>(new Pair<K, V>(key, value), 0);
           q.remove(delayItem);
           cacheObjMap.remove(key,value);
       }
    }

}
