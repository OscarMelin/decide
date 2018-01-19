import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathTest {
    @Test
    void testSum(){
        Math math = new Math();
        assertEquals(5, math.sum(3, 2));
    }
}