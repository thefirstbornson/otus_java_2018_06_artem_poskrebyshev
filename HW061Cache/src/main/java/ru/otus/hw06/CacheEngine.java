package ru.otus.hw06;

/**
 * Created by tully.
 */
public interface CacheEngine<K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    void dispose();
}
