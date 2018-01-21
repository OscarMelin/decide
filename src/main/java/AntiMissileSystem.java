
public class AntiMissileSystem {

    //------------------ Global Data ------------------

    // The number of planar data points. 2 <= NUMPOINTS <= 100
    public int numPoints;
    // Array of planar data points.
    public Point[] points;

    // Struct holding parameters for Launch Interceptor Conditions (LIC’s).
    public Parameters parameters;

    // The Logical Connector Matrix (LCM) is a 15x15 symmetric matrix that
    // defines which individual LIC’s that must be considered jointly in some way.
    public Connector[][] lcm;

    // The Preliminary Unlocking Vector (PUV) represents which LIC actually matters
    // in this particular launch determination.
    public boolean[] puv;

    // The Conditions Met Vector (CMV) is set according to the results of each LIC.
    private boolean[] cmv = new boolean[15];

    // The combination of LCM and CMV is stored in the
    // Preliminary Unlocking Matrix (PUM), a 15x15 symmetric matrix.
    private boolean[][] pum = new boolean[15][15];

    // The Final Unlocking Vector (FUV) is a 15-element vector.
    // If, and only if, all the values in the FUV are true the launch-unlock
    // signal should be generated.
    private boolean[] fuv = new boolean[15];

    /**
     * Main method. Do not write anything here to make it easy to test.
     * (We want to be able to set in and out stream outside static main.)
     * @param args main args
     */
    public static void main(String args[]) {
        //Todo: implement when standard input is provided
    }

    /**
     *
     * @param numPoints
     * @param points
     * @param parameters
     * @param lcm
     * @param puv
     * @return instance of AntiMissileSystem
     */
    public AntiMissileSystem(int numPoints, Point[] points, Parameters parameters, Connector[][] lcm, boolean[] puv) {
        this.numPoints = numPoints;
        this.points = points;
        this.parameters = parameters;
        this.lcm = lcm;
        this.puv = puv;
    }

    /**
     *
     * @return whether an interceptor should be launched
     */
    //TODO: The actual return value should be a string of either "YES" or "NO"
    public boolean decide() {
        return false;
    }

    public void generatePUM() {}

    public void generateFUV() {}


    public boolean lic0() {
        return false;
    }

    public boolean lic1() {
        return false;
    }

    /**
     *
     * @return whether three consecutive points form an angle grater than PI+epsilon or less than PI-epsilon
     */
    public boolean lic2() {
        //Iterate over all three consecutive points
        for (int index = 0; index < numPoints-3; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1];
            Point point3 = points[index+2];

            // Calculate the two vectors using point 2 as vertex
            Point vector1 = new Point(point1.x - point2.x, point1.y - point2.y);
            Point vector2 = new Point(point3.x - point2.x, point3.x - point2.y);

            double dotProduct = vector1.x*vector2.x + vector1.y*vector2.y;
            double vector1Len = Math.sqrt(vector1.x*vector1.x + vector1.y*vector1.y);
            double vector2Len = Math.sqrt(vector2.x*vector2.x + vector2.y*vector2.y);

            // If any two points coincide then move on
            if(vector1Len == 0 || vector2Len == 0) {
                continue;
            }
            // Obtain the angle through the definition of dot product in euclidean space
            double angle = Math.acos(dotProduct/(vector1Len*vector2Len));

            if(angle < (Math.PI - parameters.epsilon) || angle > (Math.PI + parameters.epsilon)) {
                return true;
            }
        }
        // No points where found that satisfied the criteria
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
