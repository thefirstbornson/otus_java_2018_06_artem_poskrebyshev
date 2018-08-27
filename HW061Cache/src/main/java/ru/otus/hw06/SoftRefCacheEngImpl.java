package ru.otus.hw06;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class SoftRefCacheEngImpl<K, V> implements CacheEngine<K, V> {

    private final HashMap<K, SoftReference<V>> mCache= new LinkedHashMap<>();
    private final int maxElements;
    private int hit = 0;
    private int miss = 0;

    SoftRefCacheEngImpl(int maxElements){
        this.maxElements = maxElements;
    }

    @Override
    public void put(K key, V value) {
        if (mCache.size() == maxElements) {
            K firstKey = mCache.keySet().iterator().next();
            mCache.remove(firstKey);
        }

        mCache.put(key, new SoftReference<V>(value));
    }

    @Override
    public V get(K key) {
        V value=null;
        SoftReference<V> reference = mCache.get(key);
        value = reference != null ? reference.get() : null;

        if (value != null ){
            hit++;
        }
        else{
            miss++;
        }
        return value;
    }
    @Override
    public int getHitCount() {
        return hit;
    }
    @Override
    public int getMissCount() {
        return miss;
    }

    @Override
    public void dispose() {

    }
}