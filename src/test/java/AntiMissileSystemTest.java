import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.PI;

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
        // contract: lic0 returns true if two consecutive data point are a greater distance than the length length1
        // defined in the parameters, false otherwise
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
  
    @Test
    void testLic2() {
        // Contract: Lic2 returns true iff there exists a set of three consecutive
        // points that form an angle (where the second point is the vertex of the angel)
        // that is greater than PI+epsilon or less than PI-epsilon. Otherwise it returns false.

        int numPoints = 4;
        Point[] points = {new Point(-1.0,-1.0), new Point(0.0,0.0), new Point(1.0,0.0), new Point(1.0, -1.0)};
        Parameters parameters = new Parameters();
        parameters.epsilon = PI/18.0; // Epsilon is 10 degrees (measured in radians)
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null,null);
        assertTrue(testSystem.lic2());

        testSystem.parameters.epsilon = PI*0.499; // Epsilon is almost 90 degrees (measured in radians)
        assertTrue(testSystem.lic2());

        testSystem.parameters.epsilon = PI*0.50; // Epsilon is 90 degrees (measured in radians)
        assertFalse(testSystem.lic2());
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