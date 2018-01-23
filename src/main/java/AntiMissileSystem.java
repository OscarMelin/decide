import java.util.ArrayList;

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
        if (parameters.radius1 < 0) {
            return false;
        }

        Point a, b, c;
        for (int i = 0; i < this.points.length - 2; i++) {
            a = this.points[i];
            b = this.points[i+1];
            c = this.points[i+2];

            // Length between point a and b
            double lengthAB = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2));
            double lengthAC = sqrt(pow(a.x - c.x, 2) + pow(a.y - c.y, 2));
            double lengthBC = sqrt(pow(b.x - c.x, 2) + pow(b.y - c.y, 2));

            // Calculating the radius of the circumcircle
            double multipliedLengths = lengthAB * lengthAC * lengthBC;
            double multipliedLengthDiffs =
                    (lengthAB + lengthAC + lengthBC) *
                    (lengthAB + lengthAC - lengthBC) *
                    (lengthAC + lengthBC - lengthAB) *
                    (lengthBC + lengthAB - lengthAC);
            double radius = multipliedLengths / sqrt(multipliedLengthDiffs);

            // Check if points b or c is inside or on the radius radius1 away from a
            if (radius > parameters.radius1) {
                return true;
            }
        }
        return false;
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

    /**
     *
     * @return true iff there exists at least one set of Q_PTS consecutive
     * data points that lie in more than QUADS quadrants.
     * 2 <= Q_PTS <= NUMPOINTS , 1 <= QUADS <= 3
     */
    public boolean lic4() {
        // check the boundaries
        if (parameters.qPts > numPoints || parameters.qPts < 2) {
            return false;
        }

        // check the boundaries
        if (parameters.qUads > 3 || parameters.qUads < 1) {
            return false;
        }

        //Iterate over all sets of qPts consecutive points
        for (int i = 0; i <= numPoints-parameters.qPts; i++) {
            ArrayList<Point> consecPoints = new ArrayList<Point>();
            //Iterate qPts steps to gather the set
            for (int j = i; j < parameters.qPts+i; j++) {
                consecPoints.add(points[j]);
            }

            //Keep track of visited quadrants
            boolean[] diffQuads = new boolean[4];

            for (Point p: consecPoints) {
                if (p.x >= 0){
                    if(p.y >= 0){
                        diffQuads[0] = true;
                    }
                    else if (p.x == 0 && p.y < 0){
                        diffQuads[2] = true;
                    }
                    else {
                        diffQuads[3] = true;
                    }
                }
                else {
                    if (p.y >= 0) {
                        diffQuads[1] = true;
                    }
                    else {
                        diffQuads[2] = true;
                    }
                }
            }

            //Did the set lie in more than qUads quadrants?
            int trueCount = 0;
            for (int k = 0; k < diffQuads.length; k++) {
                if (diffQuads[k]) {
                    trueCount++;
                }
                if (trueCount > parameters.qUads) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return true iff there exists at least one set of two consecutive
     * data points where X[j] - X[i] < 0. (where i = j-1)
     */
    public boolean lic5() {
        Point i, j;
        for (int index = 0; index < this.points.length - 1; index++) {
            i = this.points[index];
            j = this.points[index+1];

            if ((j.x - i.x) < 0){
                return true;
            }
        }
        return false;
    }

    public boolean lic6() {
        return false;
    }
    /**
     *
     * @return true iff there exists at least one set of two data points
     * separated by exactly K PTS consecutive intervening points that are
     * a distance greater than the length, LENGTH1, apart
     */
    public boolean lic7() {
        if (numPoints < 3) {
            return false;
        }

        for (int index = 0; index < numPoints - 1 - parameters.kPTS; index++) {
            Point point1 = points[index];
            Point point2 = points[index + 1 + parameters.kPTS];

            // Calculate the distance between the two points
            double distance = sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));

            if(distance > parameters.length1) {
                return true;
            }
        }
        return false;
    }

    public boolean lic8() {
        return false;
    }

    /**
     *
     * @return whether three consecutive points separate by C_PTS and D_PTS consecutive intervening points
     * form an angle greater than PI+epsilon or less than PI-epsilon.
     * When NUMPOINTS < 5, the condition is not met.
     */
    public boolean lic9() {
        if(numPoints < 5) {
            return false;
        }

        //Iterate over all sets of three consecutive points separated by C_PTS and D_PTS points
        for (int index = 0; index < numPoints-2-parameters.cPTS-parameters.dPTS; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1+parameters.cPTS];
            Point point3 = points[index+2+parameters.cPTS+parameters.dPTS];

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
     * @return whether there exists at least one set of three data points
     * separated by exactly E_PTS and F_PTS consecutive intervening points,
     * respectively, that are the vertices of a triangle with area
     * greater than AREA1. The condition is not met when NUMPOINTS < 5.
     */
    public boolean lic10() {
        if(numPoints < 5) {
            return false;
        }

        //Iterate over all sets of three consecutive points separated by E_PTS and F_PTS points
        for (int index = 0; index < numPoints-2-parameters.ePTS-parameters.fPTS; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1+parameters.ePTS];
            Point point3 = points[index+2+parameters.ePTS+parameters.fPTS];

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

    /**
     *
     * @return whether there exists a set of two data points, (X[i],Y[i]) and (X[j],Y[j]), separated by exactly
     * G_PTS consecutive intervening points, such that X[j] - X[i] < 0. (where i < j )
     * The condition is not met when NUMPOINTS < 3.
     */
    public boolean lic11() {
        if(numPoints < 3) {
            return false;
        }

        for(int index = 0; index < numPoints-1-parameters.gPTS; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1+parameters.gPTS];

            if(point2.x - point1.x < 0.0) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @return whether there exists a set of two points separated by K_PTS consecutive points
     * such that the distance between the points are greater than LENGTH1 and there
     * exists a set of two points (might be the same as the first set) also separated by K_PTS
     * consecutive points such that the distance between the points is less than LENGTH2. The lic is
     * not met if NUMPOINTS < 3.
     */
    public boolean lic12() {
        if(numPoints < 3) {
            return false;
        }

        boolean foundLengthGreater = false;
        boolean foundLengthShorter = false;

        for(int index = 0; index < numPoints-1-parameters.kPTS; index++) {
            Point point1 = points[index];
            Point point2 = points[index+1+parameters.kPTS];

            // Calculate the distance between the two points
            double distance = sqrt(pow(point2.x - point1.x, 2) + pow(point2.y - point1.y, 2));

            if(distance > parameters.length1) {
                foundLengthGreater = true;
            }

            if(distance < parameters.length2) {
                foundLengthShorter = true;
            }
        }
        return (foundLengthGreater && foundLengthShorter);
    }

    /**
     *
     * @return There exists at least one set of three data points, separated by exactly A PTS and B PTS consecutive
     * intervening points, respectively, that cannot be contained within or on a circle of radius RADIUS1.
     *
     * In addition, there exists at least one set of three data points (which can be the same or different from the three
     * data points just mentioned) separated by exactly A PTS and B PTS consecutive intervening points, respectively,
     * that can be contained in or on a circle of radius RADIUS2.
     *
     * Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5. 0 ≤ RADIUS2.
     */
    public boolean lic13() {
        if(parameters.radius2 <= 0 || numPoints < 5) {
            return false;
        }
        for (int i = 0; i < (numPoints - parameters.aPTS - parameters.bPTS - 2); i++) {
            int ii = i + parameters.aPTS + 1;
            int iii = ii + parameters.bPTS + 1;

            if (!inCircle(points[i], points[ii], points[iii], parameters.radius1) && inCircle(points[i], points[ii], points[iii], parameters.radius2))
                return true;
        }
        return false;
    }

    /**
     *
     * @return whether the two following conditions are met.
     *  - there exists at least one set of three data points
     * separated by exactly E_PTS and F_PTS consecutive intervening points,
     * respectively, that are the vertices of a triangle with area
     * greater than AREA1.
     * - there exists at least one set of three data points
     * separated by exactly E_PTS and F_PTS consecutive intervening points,
     * respectively, that are the vertices of a triangle with area
     * less than AREA2.
     * The complete condition is not met when NUMPOINTS < 5.
     */
    public boolean lic14() {
        if(numPoints < 5) {
            return false;
        }

        boolean foundAreaGreater = false;
        boolean foundAreaLess = false;

        //Iterate over all sets of three consecutive points separated by E_PTS and F_PTS points
        for (int i = 0; i < numPoints-2-parameters.ePTS-parameters.fPTS; i++) {
            Point a = points[i];
            Point b = points[i+1+parameters.ePTS];
            Point c = points[i+2+parameters.ePTS+parameters.fPTS];

            // Calculate the sides of the triangle
            double lengthAB = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2));
            double lengthAC = sqrt(pow(a.x - c.x, 2) + pow(a.y - c.y, 2));
            double lengthBC = sqrt(pow(b.x - c.x, 2) + pow(b.y - c.y,2));

            // Calculate the area of the triangle using Heron's formula
            double tmp = (lengthAB + lengthAC + lengthBC) / 2;
            double area = sqrt(tmp*(tmp - lengthAB) * (tmp - lengthAC) * (tmp - lengthBC));

            if(area > parameters.area1) {
                foundAreaGreater = true;
            }

            if(area < parameters.area2) {
                foundAreaLess = true;
            }

            if (foundAreaGreater && foundAreaLess) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param a
     * @param b
     * @param c
     * @return true if all three points can be contained in a circle.
     */
    boolean inCircle(Point a, Point b, Point c, double radius) {
        // Length between point a and b
        double lengthAB = sqrt(pow(a.x - b.x, 2) + pow(a.y - b.y, 2));
        double lengthAC = sqrt(pow(a.x - c.x, 2) + pow(a.y - c.y, 2));
        double lengthBC = sqrt(pow(b.x - c.x, 2) + pow(b.y - c.y, 2));

        // If all points are identical
        if (lengthAB == 0 && lengthAC == 0)
            return true;
        // If points form a line
        if ((a.y - b.y) * (a.x - c.x) == (a.y - c.y) * (a.x - b.x)) {
            if (lengthAB <= radius && lengthAC <= radius && lengthBC <= radius)
                return true;
        }

        // Calculating the radius of the circumcircle
        double multipliedLengths = lengthAB * lengthAC * lengthBC;
        double multipliedLengthDiffs =
            (lengthAB + lengthAC + lengthBC) *
            (lengthAB + lengthAC - lengthBC) *
            (lengthAC + lengthBC - lengthAB) *
            (lengthBC + lengthAB - lengthAC);
        double r = multipliedLengths / sqrt(multipliedLengthDiffs);

        // Check if points b or c is inside or on the radius radius1 away from a
        if (r <= radius)
            return true;
        return false;
    }
}
