package java;

import junit.framework.*;

public class MathTest extends TestCase {
    @Test
    public void testSum(){
        var math = new Math();
        org.junit.Assert.assertEqual(5, math.sum(3, 2));
    }
}