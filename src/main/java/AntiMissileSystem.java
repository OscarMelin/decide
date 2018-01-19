public class AntiMissileSystem {

    //------------------ Help construction -----------

    // The Logical Connector Matrix (LCM) consists of these types of entries.
    enum LcmEntry {
        ANDD, ORR, NOTUSED
    }

    //------------------ INPUT ------------------------

    // The number of planar data points. 2 <= NUMPOINTS <= 100
    private int numPoints;
    // Array of planar data points.
    private Point[] points;

    // Struct holding parameters for Launch Interceptor Conditions (LIC’s).
    private Parameters parameters;

    // The Logical Connector Matrix (LCM)is a 15x15 symmetric matrix that
    // defines which individual LIC’s that must be considered jointly in some way.
    private LcmEntry[][] lcm;
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

    



}
