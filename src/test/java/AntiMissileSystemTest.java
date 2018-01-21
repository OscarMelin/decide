import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    void testLic2_1() {
        // Contract: The function "lic2" returns true if there exists a set of three consecutive
        // points that form an angle (where the second point is the vertex of the angel)
        // that is greater than PI+epsilon

        int numPoints = 3;
        Point[] points = {new Point(-1.0,-1.0), new Point(0.0,0.0), new Point(1.0,0.0)};
        Parameters parameters = new Parameters();
        parameters.epsilon = Math.PI/18.0; // Epsilon is 10 degrees (measured in radians)
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null,null);
        assert(testSystem.lic2());
    }

    @Test
    void testLic2_2() {
        // Contract: The function "lic2" returns true if there exists a set of three consecutive
        // points that form an angle (where the second point is the vertex of the angel)
        // that is less than PI-epsilon

        int numPoints = 3;
        Point[] points = {new Point(-1.0,-1.0), new Point(0.0,0.0), new Point(1.0,0.0)};
        Parameters parameters = new Parameters();
        parameters.epsilon = Math.PI/4.001; // Epsilon is a little bit less than 45 degrees (measured in radians)
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null,null);
        assert(testSystem.lic2());
    }
}