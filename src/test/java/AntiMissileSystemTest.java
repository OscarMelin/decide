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
    void testLic12() {
        // Contract: Lic 12 returns true iff there exists a set of two points separated by K_PTS consecutive
        // intervening points such that the distance between the points is greater than LENGTH1 and there exists
        // a set of two points (possible the same set as previously mentioned) separated by K_PTS consecutive
        // intervening points such that the distance between the points is less than LENGTH2.
        // Lic 12 returns false if NUMPOINTS < 3.
        int numPoints = 2;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints,points,parameters,null,null);
        assertFalse(testSystem.lic12());

        testSystem.numPoints++;
        Point[] newPoints = {new Point(0.0,0.0), new Point(5.0,0.0), new Point(3.0,0.0)};
        testSystem.points = newPoints;
        testSystem.parameters.kPTS = 1;
        testSystem.parameters.length1 = 2.0;
        testSystem.parameters.length2 = 5.0;
        assertTrue(testSystem.lic12());
    }
}