import org.junit.jupiter.api.Test;

public class AntiMissileSystemTest {
    @Test
    void test() {
        //---------------------
	    // Do stuff here!
	    //---------------------

        //Example of how you can manipulate the parameters.
        AntiMissileSystem.numPoints = 5;
        AntiMissileSystem.lcm = new Connector[15][15];
        AntiMissileSystem.lcm[0][0] = Connector.NOTUSED;
        AntiMissileSystem.lcm[0][1] = Connector.ANDD;
        AntiMissileSystem test = new AntiMissileSystem();

        assert(test.decide() == false);
    }
}