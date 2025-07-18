package edu.arizona.josesosa;

import java.util.HashMap;
import java.util.Map;

/**
 * Bi-directional map
 * <p>
 * use carefully
 * ! only simple types !
 * <p>
 * does not work on this !
 * String[] str1 = {"A","B"};
 * String[] str2 = {"B","C"};
 * map.put(new Integer(1),str1);
 * map.put(new Integer(2),str2);
 * map.get("B");
 * <p>
 * <p>
 * See http://commons.apache.org/collections/api-3.2/index.html
 *
 * @param <K>
 * @param <V>
 * @author cerny
 */
public class BiMap<K, V> {
    private final Map<K, V> keyToValue = new HashMap<K, V>(8);
    private final Map<V, K> valueToKey = new HashMap<V, K>(8);

    public void put(K key, V value) {
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }
}
