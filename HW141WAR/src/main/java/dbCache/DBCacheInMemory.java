package dbCache;

import org.apache.commons.collections4.MapIterator;
import org.apache.commons.collections4.map.LRUMap;

import java.util.ArrayList;

public class DBCacheInMemory implements DBCache{

    private long timeToLive;
    private LRUMap CacheMap;

    protected class DBCacheObject<V> {
        public long lastAccessed = System.currentTimeMillis();
        public V value;

        protected DBCacheObject(V value) {
            this.value = value;
        }
    }

    public DBCacheInMemory(long TimeToLive, final long TimerInterval, int maxItems) {
        this.timeToLive = TimeToLive * 1000;

        CacheMap = new LRUMap(maxItems);

        if (timeToLive > 0 && TimerInterval > 0) {

            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(TimerInterval * 1000);
                        } catch (InterruptedException ex) {
                        }
                        cleanup();
                    }
                }
            });

            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    public<K,V> void put(K key, V value) {
        synchronized (CacheMap) {
            CacheMap.put(key, new DBCacheObject(value));
        }
    }

    @Override
    public<K,V> V get(K key) {
        synchronized (CacheMap) {
            DBCacheObject c = (DBCacheObject) CacheMap.get(key);

            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return (V) c.value;
            }
        }
    }
    @Override
    public <K> void remove(K key) {
        synchronized (CacheMap) {
            CacheMap.remove(key);
        }
    }


    public int size() {
        synchronized (CacheMap) {
            return CacheMap.size();
        }
    }

    public <K> void cleanup() {

        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;

        synchronized (CacheMap) {
            MapIterator itr = CacheMap.mapIterator();

            deleteKey = new ArrayList<>();
            K key = null;
            DBCacheObject c = null;

            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (DBCacheObject) itr.getValue();

                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    deleteKey.add(key);
                }
            }
        }

        for (K key : deleteKey) {
            synchronized (CacheMap) {
                CacheMap.remove(key);
            }

            Thread.yield();
        }
    }
}