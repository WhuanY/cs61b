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
        front = -1; // Invariant: front always points to the pos of first item. -1 when deque is empty
        back = -1; // Invariant: back always points to the pos of the back item. -1 when deque is empty
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
        if (size == 1) {
            back = front;
        }
    }

    @Override
    public void addLast(T item) {
        if (size() == capacity) {
            resize(capacity * 2);
        }
        if (isEmpty()) {
            back = 0;
        }
        else {
            back = (back - 1 + capacity) % capacity;
        }
        items[back] = item;
        size += 1;
        if (size == 1) {
            front = back;
        }
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
            int ptr = (front + capacity - i) % capacity;
            System.out.print(items[ptr] + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T retItem = items[front];
        front = (front + capacity - 1) % capacity;
        size -= 1;
        if (reachCapacityMinimum()) {
            int newCapacity = size;
            resize(newCapacity);
        }
        if (isEmpty()) {
            back = -1;
            front = -1;
        }
        return retItem;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T retItem = items[back];
        back = (back + 1) % capacity;
        size -= 1;
        if (reachCapacityMinimum()) {
            int newCapacity = size;
            resize(newCapacity);
        }
        if (isEmpty()) {
            back = -1;
            front = -1;
        }
        return retItem;
    }

    @Override
    public T get(int index) {
        if (isEmpty()) {
            return null;
        }
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
        if (!((o instanceof LinkedListDeque) || (o instanceof ArrayDeque))) {
            return false;
        }
        // has the same object?
        if (o instanceof LinkedListDeque) {
            LinkedListDeque<?> other = (LinkedListDeque<?>) o;

            if (!(other.size() == this.size)) {
                return false;
            }
            Iterator<T> thisIter = this.iterator();
            Iterator<?> otherIter = other.iterator();

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
        } else {
            ArrayDeque<?> other = (ArrayDeque<?>) o;
            if (!(other.size() == this.size)) {
                return false;
            }
            Iterator<T> thisIter = this.iterator();
            Iterator<?> otherIter = other.iterator();

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
        }
        return true;
    }


    private float getUsageFactor() {
        return (float) size / capacity;
    }

    private boolean reachCapacityMinimum() {
        float usageFactor;
        usageFactor = (float) size / capacity;
        return size > 16 && usageFactor < 0.25;
    }

    private void resize(int newCapacity){
        T[] a = (T[]) new Object[newCapacity];
        for (int pos = 0; pos < size; pos++) {
            int currentAdd = ((back + pos) % capacity);
            a[pos] = items[currentAdd];
        }
        back = 0;
        front = size - 1;
        items = a;
        capacity = newCapacity;
    }
}
