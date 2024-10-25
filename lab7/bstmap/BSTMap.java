package bstmap;

import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;
    private class Node {
        K key;
        V value;
        Node left; // left children
        Node right; // right children
        private int size; // number of nodes in subtree

        Node(K key, V value, int size) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.size = size;
        }
    }

    public BSTMap() {

    }

    /** Removes all the mappings from this map. */
    @Override
    public void clear() {
        this.root = null;
    }

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key) {
        if (key == null) throw new IllegalArgumentException("calls containsKey() with a null key");
        Node currentNode = this.root;
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return true;
            }
            else if (currentNode.key.compareTo(key) > 0) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        return false;
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    public V get(K key) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        Node currentNode = this.root;
        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            else if (currentNode.key.compareTo(key) > 0) {
                currentNode = currentNode.left;
            }
            else {
                currentNode = currentNode.right;
            }
        }
        return null;
    }


    /* Returns the number of key-value mappings in this map.
    * Implementation logic: track size from node method. */
    @Override
    public int size() {
        return size(this.root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        else {
            return x.size;
        }
    }

    /* Associates the specified value with the specified key in this map. */
    public void put(K key, V value) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        root = put(root, key, value);
    }

    private Node put(Node x, K key, V value) {
        if (x == null) return new Node(key, value, 1);
        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, key, value);
        }
        else if (cmp > 0){
            x.right = put(x.right, key, value);
        }
        else {
            x.value = value;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /* Returns a Set view of the keys contained in this map. Not required for Lab 7.
     * If you don't implement this, throw an UnsupportedOperationException. */
    public Set<K> keySet(){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* Removes the mapping for the specified key from this map if present.
     * Not required for Lab 7. If you don't implement this, throw an
     * UnsupportedOperationException. */
    public V remove(K key){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for Lab 7. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    public V remove(K key, V value){
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException("Iterator not implemented yet");
    }
}
