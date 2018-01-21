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
    void testLic3() {
        // Contract: Lic3 returns true iff there exists at least one set of three consecutive
        // data points that are the vertices of a triangle with area greater than AREA1.

        int numPoints = 5;
        Point[] points = {new Point(1.0,1.0), new Point(1.0,0.0), new Point(0.0,0.0), new Point(-3.0,3.0), new Point(-3.0,0.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints,points,parameters,null,null);
        assertTrue(testSystem.lic3());

        testSystem.parameters.area1 = 1.0;
        assertTrue(testSystem.lic3());

        testSystem.parameters.area1 = 5.0;
        assertFalse(testSystem.lic3());
    }
}