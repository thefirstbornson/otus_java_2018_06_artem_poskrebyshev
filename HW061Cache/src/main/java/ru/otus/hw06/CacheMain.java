package ru.otus.hw06;
//-Xms512m -Xmx512m

public class CacheMain {

    public static void main(String[] args) throws InterruptedException {
     // new CacheMain().eternalCacheExample();
     // new CacheMain().idleCacheExample();
     // new CacheMain().lifeCacheExample();
      new CacheMain().softRefCacheExample();

    }
    private void softRefCacheExample() {

        SoftRefCacheEngImpl<Integer, String> cache = new SoftRefCacheEngImpl<>(5000);
        int sum = 0;
        for (int i = 0; i < 3111500; i++) {
            cache.put(i, "String: " + i);
        }

        for (int k = 0; k < 3111500; k++) {
           String element = cache.get(k);
           System.out.println("String for " + k + ": " + (element != null ? element : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());
    }
    private void eternalCacheExample() {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 0, true);

        for (int i = 0; i < 10; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println("String for " + i + ": " + (cache.get(i) != null ? cache.get(i) : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }
    private void lifeCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 1000, 0, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            System.out.println("String for " + i + ": " + (cache.get(i) != null ? cache.get(i) : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(1000);

        for (int i = 0; i < size; i++) {
            System.out.println("String for " + i + ": " + (cache.get(i) != null ? cache.get(i) : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

    private void idleCacheExample() throws InterruptedException {
        int size = 5;
        CacheEngine<Integer, String> cache = new CacheEngineImpl<>(size, 0, 3500, false);

        for (int i = 0; i < size; i++) {
            cache.put(i, "String: " + i);
        }

        for (int i = 0; i < size; i++) {
            System.out.println("String for " + i + ": " + (cache.get(i) != null ? cache.get(i) : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        Thread.sleep(3000);

        for (int i = 0; i < size; i++) {
            System.out.println("String for " + i + ": " + (cache.get(i) != null ? cache.get(i) : "null"));
        }

        System.out.println("Cache hits: " + cache.getHitCount());
        System.out.println("Cache misses: " + cache.getMissCount());

        cache.dispose();
    }

}
