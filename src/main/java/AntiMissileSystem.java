public class AntiMissileSystem {

    //------------------ Help construction -----------

    // The Logical Connector Matrix (LCM) consists of these types of entries.
    enum LcmEntry {
        ANDD, ORR, NOTUSED
    }

    //------------------ Global Data ------------------

    // The number of planar data points. 2 <= NUMPOINTS <= 100
    private int numPoints;
    // Array of planar data points.
    private Point[] points;

    // Struct holding parameters for Launch Interceptor Conditions (LIC’s).
    private Parameters parameters;

    // The Logical Connector Matrix (LCM) is a 15x15 symmetric matrix that
    // defines which individual LIC’s that must be considered jointly in some way.
    private LcmEntry[][] lcm;

    // The Conditions Met Vector (CMV) is set according to the results of each LIC.
    private boolean[] cmv;

    // The Preliminary Unlocking Vector (PUV) represents which LIC actually matters
    // in this particular launch determination.
    private boolean[] puv;


    /**
     * Main method. Do not write anything here to make it easy to test.
     * (We want to be able to set in and out stream outside static main.)
     * @param args main args
     */
    public static void main(String args[]) {
        new AntiMissileSystem();
    }

    /**
     * Constructor.
     * DO YOUR STUFF HERE.
     */
    public AntiMissileSystem() {
        //---------------------
        // Do stuff here!
        //---------------------
    }

    /**
     *
     * @return whether an interceptor should be launched
     */
    //TODO: The actual return value should be a string of either "YES" or "NO"
    public boolean decide() {
        return false;
    }


    public boolean lic0() {
        return false;
    }

    public boolean lic1() {
        return false;
    }

    public boolean lic2() {
        return false;
    }

    public boolean lic3() {
        return false;
    }

    public boolean lic4() {
        return false;
    }

    public boolean lic5() {
        return false;
    }

    public boolean lic6() {
        return false;
    }

    public boolean lic7() {
        return false;
    }

    public boolean lic8() {
        return false;
    }

    public boolean lic9() {
        return false;
    }

    public boolean lic10() {
        return false;
    }

    public boolean lic11() {
        return false;
    }

    public boolean lic12() {
        return false;
    }

    public boolean lic13() {
        return false;
    }

    public boolean lic14() {
        return false;
    }
}
