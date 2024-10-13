package deque;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private class DLNode {
        public T item;
        public DLNode prev;
        public DLNode next;

        public DLNode(T i, DLNode p, DLNode n) {
            item = i;
            prev = p;
            next = n;
        }
    }

    private int size;
    private DLNode sentF;
    private DLNode sentL;


    /** Creates an empty linked list deque. */
    public LinkedListDeque() {
        sentF = new DLNode(null, null, null);
        sentL = new DLNode(null, null, null);
        sentF.prev = sentL;
        sentL.next = sentF;
        size = 0;
    }

    @Override
    public void addFirst(T item){
        DLNode newFirstNode = new DLNode(item, sentF, null);
        if (size == 0)  {
            newFirstNode.next = sentL;
            sentL.prev = newFirstNode;
        }
        else {
            newFirstNode.next = sentF.next;
            sentF.next.prev = newFirstNode;
        }
        sentF.next = newFirstNode;
        size += 1;
    }

    @Override
    public void addLast(T item) {
        DLNode newLastNode = new DLNode(item,null,sentL);
        if (size == 0) {
            newLastNode.prev = sentF;
            sentF.next = newLastNode;
        }
        else {
            newLastNode.prev = sentL.prev;
            sentL.prev.next = newLastNode;
        }
        sentL.prev = newLastNode;
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
        DLNode cur = sentF;
        if (size() > 0) {
            for (int i = 0; i < size(); i++) {
                cur = cur.next;
                System.out.print(cur.item + " ");
            }
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size() == 0) {
            return null;
        }
        size -= 1;
        T ret = sentF.next.item;
        sentF.next = sentF.next.next;
        sentF.next.prev = sentF;
        return ret;
    }

    @Override
    public T removeLast() {
        if (size() == 0) {
            return null;
        }
        size -= 1;
        T ret = sentL.prev.item;
        sentL.prev = sentL.prev.prev;
        sentL.prev.next = sentL;
        return ret;
    }

    @Override
    public T get(int index) {
        if ((index >= size()) || index < 0) {
            return null;
        }
        DLNode cur = sentF;
        for (int i = 0; i <= index; i++) {
            cur = cur.next;
        }
        return cur.item;
    }

    /**
     * Returns the item at the given index using a recursive approach.
     * @param index the index of the item to be retrieved
     * @return the item at the specified index if it exists, null otherwise
     */
    public T getRecursive(int index){
        // Check if the index is out of bounds
        if (index < 0 || index >= size()) {
            return null;
        }
        return getRecursiveHelper(sentF.next, index); // Start recursion from the first element
    }
    /**
     * Private helper method to facilitate the recursion for getRecursive method.
     * @param node the starting node for the recursion
     * @param index the index to be decremented until it reaches 0
     * @return the item of the node at the current recursion depth
     */
    private T getRecursiveHelper(DLNode node, int index) {
        if (index == 0) {
            return node.item; // Base case: if index is 0, return the item of the current node
        } else {
            return getRecursiveHelper(node.next, index - 1); // Recursive call with the next node and index decremented by 1
        }
    }


    private class LinkedListDequeIterator implements Iterator<T> {
        private DLNode current;

        public LinkedListDequeIterator() {
            current = sentF;
        }

        public boolean hasNext() {
            return current.next != sentL;
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            current = current.next;
            return current.item;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }
    /**
     * o is considered equal if it is a Deque and if it contains the same contents
     * @param o
     * @return whether or not the parameter o is equal to the Deque.
     */
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
        }
        else {
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
}

