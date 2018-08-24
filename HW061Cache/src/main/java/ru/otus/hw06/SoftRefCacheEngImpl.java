package ru.otus.hw06;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public class SoftRefCacheEngImpl<K, V> implements SoftRefCacheEng<K, V> {

    private final HashMap<K, SoftReference<V>> mCache= new HashMap<>();
    private int hit = 0;
    private int miss = 0;

    public void put(K key, V value) {
        mCache.put(key, new SoftReference<V>(value));
    }

    public V get(K key) {
        V value=null;
        SoftReference<V> reference = mCache.get(key);
        value=reference.get();

        if (value != null ){
            hit++;
        }
        else{
            miss++;
        }
        return value;
    }

    public int getHitCount() {
        return hit;
    }

    public int getMissCount() {
        return miss;
    }
}