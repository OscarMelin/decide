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
    void testLic11() {
        // Contract: Lic11 returns true iff there exists a set of two data points, (X[i],Y[i]) and (X[j],Y[j]),
        // separated by exactly G_PTS consecutive intervening points, such that X[j] - X[i] < 0 (where i < j ).
        // The condition is not met when NUMPOINTS < 3.

        int numpoints = 2;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numpoints,points,parameters,null,null);
        assertFalse(testSystem.lic11());

        testSystem.numPoints++;
        Point[] newPoints = {new Point(0.0,0.0), new Point(1.0,1.0), new Point(-1.0,323434.4)};
        testSystem.points = newPoints;
        testSystem.parameters.gPTS = 1;
        assertTrue(testSystem.lic11());
    }
}