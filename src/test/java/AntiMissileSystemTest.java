import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AntiMissileSystemTest {

    @Test
    void test1() {
        //Example of how you can manipulate the parameters.
        int numPoints = 1;
        Point[] points = {new Point(20.0, 30.0)};
        Parameters parameters = new Parameters();
        Connector[][] lcm = new Connector[15][15];
        lcm[0][0] = Connector.NOTUSED;
        lcm[0][1] = Connector.ANDD;
        boolean[] puv = new boolean[15];
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, lcm, puv);

        assertFalse(antiMissileSystem.decide());
    }
  
    @Test
    void testLic0() {
        // contract: lic0 returns true if two consecutive data point are a greater distance than the length,
        // false otherwise
        int numPoints = 5;
        Point[] points = {
                new Point(0, 0),
                new Point(1, 1),
                new Point(3, 5),
                new Point(10, 10),
                new Point(20, 20)
        };
        Parameters parameters = new Parameters();
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);

        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 4;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 6;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 10;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 20;
        assertFalse(antiMissileSystem.lic0());
    }
}