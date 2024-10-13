package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testRandomExample() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> BuggyL = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                BuggyL.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int Lsize = L.size();
                int BuggyLSize = BuggyL.size();
                Assert.assertEquals(Lsize, BuggyLSize);
            }
            else if (operationNumber == 2) {
                // get last
                if ((L.size() > 0) && (BuggyL.size() > 0)) {
                    Assert.assertEquals( L.getLast(), BuggyL.getLast());
                }
            }
            else if (operationNumber == 3) {
                // remove last
                if ((L.size() > 0) && (BuggyL.size() > 0))  {
                    Assert.assertEquals(L.removeLast(), BuggyL.removeLast());
                }
            }
        }
    }
}

