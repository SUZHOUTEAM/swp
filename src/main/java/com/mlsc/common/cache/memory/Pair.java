package com.mlsc.common.cache.memory;

/**
 * Created by yinxl on 2017/6/30.
 */
public class Pair<K, V> {
    private K first;

    private V second;

    public K getFirst() {
        return first;
    }

    public void setFirst(K first) {
        this.first = first;
    }

    public V getSecond() {
        return second;
    }

    public void setSecond(V second) {
        this.second = second;
    }

    public Pair() {}

    public Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }
}
