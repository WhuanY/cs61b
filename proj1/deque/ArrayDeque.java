package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private T[] items;
    private int size;
    private int front;
    private int back;
    private int capacity;
    /** Create an Array Deque*/
    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        front = 0;
        back = 0;
        capacity = 8;
    }

    @Override
    public void addFirst(T item){
        if (size() == capacity) {
            resize(capacity * 2);
        }
        front = (front + 1) % capacity;
        items[front] = item;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        if (size() == capacity) {
            resize(capacity * 2);
        }
        back = (back - 1 + capacity) % capacity;
        items[back] = item;
        size += 1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++){
            System.out.print(items[(front - i + capacity) % capacity] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        T retItem = items[front];
        front = (front + capacity - 1) % capacity;
        size -= 1;
        if (reachCapacityMinimum()) {
            int newCapacity = size;
            resize(newCapacity);
        }
        return retItem;
    }

    @Override
    public T removeLast() {
        T retItem = items[back];
        back = (back + 1) % capacity;
        size -= 1;
        if (reachCapacityMinimum()) {
            int newCapacity = size;
            resize(newCapacity);
        }
        return retItem;
    }

    @Override
    public T get(int index) {
        return items[(front + capacity - index) % capacity];
    }

    /** Return an iterator of the ArrayDeque that supports enhanced for loop
     * from queue back to front*/

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            pos = 0;
        }

        public boolean hasNext() {
            return pos < size;
        }

        public T next() {
            T returnItem = items[(front + capacity - pos) % capacity];
            pos += 1;
            return returnItem;
        }
    }

    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    /**
     * Compares this deque with the specified object for equality.
     *
     * @param o the object to be compared for equality with this deque
     * @return true if the specified object is equal to this deque
     *
     * @details Two deques are considered equal if:
     *          1) The parameter o is also a Deque
     *          2) Both deques contain the same contents in the same order
     *          3) The equality of contents is determined by the equals method of the generic type T */
    public boolean equals(Object o) {
        // is it a Deque?
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<T> other = (Deque<T>) o;
        // Both deque contain the same contents in the same order
        if (!(other.size() == this.size)) {
            return false;
        }
        Iterator<T> thisIter = this.iterator();
        Iterator<T> otherIter = other.iterator();

        while (thisIter.hasNext()) {
            T thisElem = thisIter.next();
            Object otherElem = otherIter.next();

            if (thisElem == null) {
                if (otherElem != null) {
                    return false;
                }
            } else if (!thisElem.equals(otherElem)) {
                return false;
            }
        }
        return true;
    }


    private float getUsageFactor() {
        return (float) size / capacity;
    }

    private boolean reachCapacityMinimum() {
        float usageFactor;
        usageFactor = size / capacity;
        return usageFactor < 0.25;
    }

    private void resize(int newCapacity){
        T[] a = (T[]) new Object[newCapacity];
        capacity = newCapacity;
        int pos = 0;
        for (T item : this) {
            a[pos] = item;
            pos++;
        }
        back = 0;
        front = size - 1;
        items = a;
    }
}
