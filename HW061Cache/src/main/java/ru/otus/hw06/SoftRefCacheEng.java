package ru.otus.hw06;

public interface SoftRefCacheEng<K, V>  {

    void put(K key, V value);
    V get(K key);
    int getHitCount();
    int getMissCount();

}
