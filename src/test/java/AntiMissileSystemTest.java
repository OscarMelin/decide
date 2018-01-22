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