package deque;

import edu.princeton.cs.algs4.StdRandom;
import java.util.ArrayDeque;
import org.junit.Assert;
import org.junit.Test;

public class DequeCompareTest {
    @Test
    public void dequeRandomTest() {
        int testOperations = 1000000; // Number of operations
        int maxValue = 100; // Range of integer values to add

        // Assuming your custom ArrayDeque is in your.package.ArrayDeque
        deque.ArrayDeque<Integer> myArrayDeque = new deque.ArrayDeque<>();
        ArrayDeque<Integer> javaArrayDeque = new ArrayDeque<>(); // Java's built-in ArrayDeque
        LinkedListDeque<Integer> linkedListDeque = new LinkedListDeque<>(); // Your LinkedListDeque

        for (int i = 0; i < testOperations; i++) {
            int operation = StdRandom.uniform(4); // Random operation type
            int value = StdRandom.uniform(maxValue); // Random value

            switch (operation) {
                case 0: // addFirst
                    myArrayDeque.addFirst(value);
                    javaArrayDeque.addFirst(value);
                    linkedListDeque.addFirst(value);
                    break;
                case 1: // addLast
                    myArrayDeque.addLast(value);
                    javaArrayDeque.addLast(value);
                    linkedListDeque.addLast(value);
                    break;
                case 2: // removeFirst
                    if (!myArrayDeque.isEmpty() && !javaArrayDeque.isEmpty() && !linkedListDeque.isEmpty()) {
                        Integer myFirst = myArrayDeque.removeFirst();
                        Integer javaFirst = javaArrayDeque.removeFirst();
                        Integer linkedFirst = linkedListDeque.removeFirst();
                        if (!(myFirst.equals(javaFirst))){
                            Assert.assertEquals(myFirst, javaFirst);
                            Assert.assertEquals(javaFirst, linkedFirst);
                        }
                    }
                    break;
                case 3: // removeLast
                    if (!myArrayDeque.isEmpty() && !javaArrayDeque.isEmpty() && !linkedListDeque.isEmpty()) {
                        Integer myLast = myArrayDeque.removeLast();
                        Integer javaLast = javaArrayDeque.removeLast();
                        Integer linkedLast = linkedListDeque.removeLast();
                        Assert.assertEquals(myLast, javaLast);
                        Assert.assertEquals(javaLast, linkedLast);
                    }
                    break;
            }

            // Periodic consistency checks
            if (i % 100 == 0) {
                Assert.assertEquals(myArrayDeque.size(), javaArrayDeque.size());
                Assert.assertEquals(javaArrayDeque.size(), linkedListDeque.size());
            }
        }
    }
}
