package space.harbour.java.hw3;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class MyHashMap<K, V> implements Map<K, V> {

    /*  Notice how this class Pair is inside the other class!
            This is just a storage.
    */
    public static class Pair<K, V> {
        private K key;
        private V value;

        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }
    }

    private int bucketSize = 16;
    private LinkedList<Pair<K, V>>[] buckets = new LinkedList[bucketSize];

    public MyHashMap() {
        clear();
    }

    @Override
    public int size() {
        int result = 0;

        //iterate through the array and get the size of each LinkedList
        for (int i = 0; i < buckets.length; i++) {

            /*Notice that we don't have to check for null here:
            they are never null because of the constructor we used
            */
            result += buckets[i].size(); 
        }

        return result;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    private int keyToBucketIndex(Object key) {
        return Math.abs(key.hashCode() % bucketSize);
        //return key.hashCode() >> 27; //should do the same thing as above
    }

    @Override
    public boolean containsKey(Object key) {
        int i = keyToBucketIndex(key);
        for (Pair<K, V> pair : buckets[i]) {
            /* We can access pair.key, even though it is private
            because Pair is an inner class of our HashMap  
            */
            if (pair.key.equals(key)) { 
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < bucketSize; i++) {
            for (Pair<K, V> pair : buckets[i]) {
                if (pair.value.equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int i = keyToBucketIndex(key);
        for (Pair<K, V> pair : buckets[i]) {
            if (pair.key.equals(key)) {
                return pair.value;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        Pair<K, V> pair = new Pair<>(key, value);
        int i = keyToBucketIndex(key);

        if (buckets[i].contains(pair)) {
            buckets[i].remove(pair);
            buckets[i].add(pair);
        } else {
            buckets[i].add(pair);
        }

        return value;
    }

    @Override
    public V remove(Object key) {
        V result = null;
        int i = keyToBucketIndex(key);
        for (Pair<K, V> pair : buckets[i]) {
            if (pair.key.equals(key)) {
                buckets[i].remove(pair);
                result = pair.value;
            }
        }
        return result;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> pair : m.entrySet()) {
            put(pair.getKey(), pair.getValue());
        }
    }

    /* clear() is the same as the constructor
    we create empty linked list for each element of the array.
    Garbage collection will take care of whatever we had before
    and delete it at some point in time. */
    @Override
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    //methods below are optional for the homework
    @Override
    public Set<K> keySet() {
        Set<K> result = new HashSet<>();
        for (int i = 0; i < bucketSize; i++) {
            for (Pair<K, V> pair : buckets[i]) {
                result.add(pair.key);
            }
        }
        return result;
    }

    @Override
    public Collection<V> values() {
        Collection<V> result = new HashSet<>();
        for (int i = 0; i < bucketSize; i++) {
            for (Pair<K, V> pair : buckets[i]) {
                result.add(pair.value);
            }
        }
        
        return result;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> result = new HashSet<>();
        for (int i = 0; i < bucketSize; i++) {
            for (Pair<K, V> pair : buckets[i]) {
                result.add(new AbstractMap.SimpleEntry<K, V>(pair.key, pair.value));
            }
        }
        return result;
    }
}
