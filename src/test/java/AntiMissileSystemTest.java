import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(3.0, 5.0),
                new Point(10.0, 10.0),
                new Point(20.0, 20.0)
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
    void testLic1() {
        // contract: lic1 returns true if three consecutive data point are not all contained within or on a circle
        // of radius radius1, false otherwise

        int numPoints = 3;
        Point[] points = {
                new Point(-1.0, 0.0),
                new Point(0.0, 1.0),
                new Point(1.0, 0.0)
        };

        Parameters parameters = new Parameters();
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);

        assertTrue(antiMissileSystem.lic1());

        antiMissileSystem.parameters.radius1 = -1; // negative radius1 is not allowed
        assertFalse(antiMissileSystem.lic1());

        antiMissileSystem.parameters.radius1 = 1.5; // should fail
        assertFalse(antiMissileSystem.lic1());

        // Assigning new points where all points are outside the circle with radius radius1
        antiMissileSystem.points[0] = new Point(-2.0, 0.0);
        antiMissileSystem.points[1] = new Point(0.0, 2.0);
        antiMissileSystem.points[2] = new Point(2.0, 0.0);
        assertTrue(antiMissileSystem.lic1());

        // A radius of 10 should return false
        antiMissileSystem.parameters.radius1 = 10;
        assertFalse(antiMissileSystem.lic1());
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

    @Test
    void testLic4() {
        // Contract: Lic4 returns true iff there exists at least one set of
        // Q_PTS consecutive data points that lie in more than QUADS quadrants.

        int numPoints = 4;
        Point[] points1 = {new Point(1.0, 1.0), new Point(1.0, -1.0), new Point(-1.0, 1.0), new Point(-1.0, -1.0)};
        Parameters parameters = new Parameters();

        parameters.qPts = 2;
        parameters.qUads = 2;
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertFalse(testSystem.lic4());

        parameters.qPts = 3;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertTrue(testSystem.lic4());

        parameters.qUads = 3;
        parameters.qPts = numPoints;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertTrue(testSystem.lic4());

        parameters.qPts = 2;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertFalse(testSystem.lic4());
    }
    
    @Test
    void testLic9() {
        // Contract: Lic9 returns true iff there exists a set of three consecutive points
        // (separated by C_PTS and D_PTS points) that form an angle
        // (where the second point is the vertex of the angel) that is greater than PI+epsilon
        // or less than PI-epsilon. Lic9 returns false otherwise and if numpoints < 5.
        int numpoints = 3;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0), new Point(-1.0,-1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numpoints,points,parameters,null,null);
        assertFalse(testSystem.lic9());

        testSystem.numPoints+=2;
        Point[] newPoints = {new Point(-1.0,-1.0), new Point(24.534,232.4),
                new Point(0.0,0.0), new Point(1.0,0.0), new Point(1.0, -1.0)};
        testSystem.points = newPoints;
        testSystem.parameters.cPTS = 1;
        testSystem.parameters.dPTS = 1;
        testSystem.parameters.epsilon = PI*0.5; // Epsilon is 90 degrees (measured in radians)
        assertFalse(testSystem.lic9());

        testSystem.parameters.epsilon = PI*0.499; // Epsilon is almost 90 degrees (measured in radians)
        assertTrue(testSystem.lic9());
    }
  
    @Test
    void testLic10() {
        // Contract: Lic10 returns true iff there exists at least one set of three data points
        // separated by exactly E_PTS and F_PTS consecutive intervening points, respectively,
        // that are the vertices of a triangle with area greater than AREA1.
        // The condition is not met when NUMPOINTS < 5.

        int numPoints = 4;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0), new Point(1.0,0.0), new Point(-1.0,-1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);
        assertFalse(testSystem.lic10());

        Point[] newPoints = {new Point(0.0,0.0), new Point(1.0,1.0), new Point(1.0,0.0), new Point(-1.0,-1.0), new Point(1.0,2.0)};
        testSystem.numPoints++;
        testSystem.points = newPoints;
        testSystem.parameters.ePTS = 1;
        testSystem.parameters.fPTS = 1;
        assertTrue(testSystem.lic10());

        testSystem.parameters.area1 = 1.0;
        assertFalse(testSystem.lic10());
    }
}
