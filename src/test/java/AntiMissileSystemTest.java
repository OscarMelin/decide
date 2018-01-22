import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
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
}