package deque;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.princeton.cs.algs4.StdRandom;

public class ArrayDequeTest {
    @Test
    public void addIsEmptySizeTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();

        assertTrue("A newly initialized ArrayDeque should be empty", ad1.isEmpty());
        ad1.addFirst("front");
        assertEquals(1, ad1.size());
        assertFalse("ad1 should now contain 1 item", ad1.isEmpty());

        ad1.addLast("middle");
        assertEquals(2, ad1.size());

        ad1.addLast("back");
        assertEquals(3, ad1.size());

        System.out.println("Printing out deque: ");
        ad1.printDeque();
    }

    @Test
    public void addRemoveTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        assertTrue("ad1 should be empty upon initialization", ad1.isEmpty());

        ad1.addFirst(10);
        assertFalse("ad1 should contain 1 item", ad1.isEmpty());

        ad1.removeFirst();
        assertTrue("ad1 should be empty after removal", ad1.isEmpty());
    }

    @Test
    public void removeEmptyTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(3);

        ad1.removeLast();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeFirst();

        int size = ad1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    public void multipleParamTest() {
        ArrayDeque<String> ad1 = new ArrayDeque<>();
        ArrayDeque<Double> ad2 = new ArrayDeque<>();
        ArrayDeque<Boolean> ad3 = new ArrayDeque<>();

        ad1.addFirst("string");
        ad2.addFirst(3.14159);
        ad3.addFirst(true);

        String s = ad1.removeFirst();
        double d = ad2.removeFirst();
        boolean b = ad3.removeFirst();
    }

    @Test
    public void getTest(){
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        int N = 100;
        for (int i = 0; i < N; i++) {
            ad1.addLast(i);
        }
        for (int i = 0; i < N; i++) {
            int expectedItem = ad1.get(i);
            assertEquals("get should return expected value", i, expectedItem);
        }
    }

    @Test
    public void bigADequeTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        for (int i = 0; i < 100000; i++) {
            ad1.addLast(i);
        }
        for (int i = 0; i < 50000; i++) {
            assertEquals("Should have the same value", i, (int) ad1.removeFirst());
        }

        for (int i = 99999; i >= 50000; i--) {
            assertEquals("Should have the same value", i, (int) ad1.removeLast());
        }
    }
}


