import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static java.lang.Math.PI;

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

    /**
     *
     * @return true if two consecutive data points are a distance greater than the length1 defined in the parameters,
     * else false is returned
     */
    public boolean lic0() {
        Point a, b;
        for (int i = 0; i < this.points.length - 1; i++) {
            a = this.points[i];
            b = this.points[i+1];

            // Calculate the distance between point a and b
            double distance = sqrt(pow(b.x - a.x, 2) + pow(b.y - a.y, 2));

            // Check if the distance is greater than length1 in the parameters
            if (distance > parameters.length1) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return true if three consecutive data points are not contained within or on a circle of radius1
     * defined in the parameters, otherwise false is returned
     */
    public boolean lic1() {
        Point a, b, c;
        for (int i = 0; i < this.points.length - 2; i++) {
            a = this.points[i];
            b = this.points[i+1];
            c = this.points[i+2];

            // Check if points b or c is inside or on the radius rasius1 away from a
            if (pow(b.x - a.x, 2) + pow(b.y - a.y, 2) <= pow(parameters.radius1, 2) ||
                    pow(c.x - a.x, 2) + pow(c.y - a.y, 2) <= pow(parameters.radius1, 2)) {
                return false;
            }
        }
        return true;
    }
  
    /**
     *
     * @return whether three consecutive points form an angle greater than PI+epsilon or less than PI-epsilon
     */
    public boolean lic2() {
        //Iterate over all sets of three consecutive points
        for (int index = 0; index < numPoints-2; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1];
            Point point3 = points[index+2];

            // Calculate the two vectors using point 2 as vertex
            Point vector1 = new Point(point1.x - point2.x, point1.y - point2.y);
            Point vector2 = new Point(point3.x - point2.x, point3.y - point2.y);

            double dotProduct = vector1.x*vector2.x + vector1.y*vector2.y;
            double vector1Len = sqrt(pow(vector1.x,2) + pow(vector1.y,2));
            double vector2Len = sqrt(pow(vector2.x,2) + pow(vector2.y,2));

            // If any two points coincide then move on
            if(vector1Len == 0 || vector2Len == 0) {
                continue;
            }
            // Obtain the angle through the definition of dot product in euclidean space
            double angle = Math.acos(dotProduct/(vector1Len*vector2Len));

            // Check if the angle is less than PI - epsilon. Note that since we
            // use the dot product to calculate the angle we'll always get the smaller
            // angle between the two vectors and thus we do not need to check if the angle is
            // greater than PI + epsilon.
            if(angle < (PI - parameters.epsilon)) {
                return true;
            }
        }
        // No points where found that satisfied the criteria
        return false;
    }
  
    /**
     *
     * @return whether there exists a set of three consecutive points that are the vertices of a triangle
     * with area greater than parameters.area1
     */
    public boolean lic3() {
        //Iterate over all sets of three consecutive points
        for (int index = 0; index < numPoints-2; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1];
            Point point3 = points[index+2];

            // Calculate the sides of the triangle
            double length1 = sqrt(pow(point1.x-point2.x,2)+pow(point1.y-point2.y,2));
            double length2 = sqrt(pow(point1.x-point3.x,2)+pow(point1.y-point3.y,2));
            double length3 = sqrt(pow(point2.x-point3.x,2)+pow(point2.y-point3.y,2));

            // Calculate the area of the triangle using Heron's formula
            double tmp = (length1+length2+length3)/2;
            double area = sqrt(tmp*(tmp-length1)*(tmp-length2)*(tmp-length3));

            if(area > parameters.area1) {
                return true;
            }
        }
        // No points where found that satisfied the criteria
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
