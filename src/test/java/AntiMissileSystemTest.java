import org.junit.jupiter.api.Test;

import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AntiMissileSystemTest {

    @Test
    void testLic0() {
        // contract: lic0 returns true if two consecutive data point are a greater distance than the length length1
        // defined in the parameters, false otherwise
        int numPoints = 5;
        Point[] points = {
                new Point(0.0, 0.0),
                new Point(1.0, 1.0),
                new Point(3.0, 5.0),
                new Point(10.0, 10.0),
                new Point(20.0, 20.0)
        };
        Parameters parameters = new Parameters();
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);

        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 4;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 6;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 10;
        assertTrue(antiMissileSystem.lic0());

        antiMissileSystem.parameters.length1 = 20;
        assertFalse(antiMissileSystem.lic0());
    }
  
    @Test
    void testLic1() {
        // contract: lic1 returns true if three consecutive data point are not all contained within or on a circle
        // of radius radius1, false otherwise

        int numPoints = 3;
        Point[] points = {
                new Point(-1.0, 0.0),
                new Point(0.0, 1.0),
                new Point(1.0, 0.0)
        };

        Parameters parameters = new Parameters();
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);

        assertTrue(antiMissileSystem.lic1());

        antiMissileSystem.parameters.radius1 = -1; // negative radius1 is not allowed
        assertFalse(antiMissileSystem.lic1());

        antiMissileSystem.parameters.radius1 = 1.5; // should fail
        assertFalse(antiMissileSystem.lic1());

        // Assigning new points where all points are outside the circle with radius radius1
        antiMissileSystem.points[0] = new Point(-2.0, 0.0);
        antiMissileSystem.points[1] = new Point(0.0, 2.0);
        antiMissileSystem.points[2] = new Point(2.0, 0.0);
        assertTrue(antiMissileSystem.lic1());

        // A radius of 10 should return false
        antiMissileSystem.parameters.radius1 = 10;
        assertFalse(antiMissileSystem.lic1());
    }
  
    @Test
    void testLic2() {
        // Contract: Lic2 returns true iff there exists a set of three consecutive
        // points that form an angle (where the second point is the vertex of the angel)
        // that is greater than PI+epsilon or less than PI-epsilon. Otherwise it returns false.

        int numPoints = 4;
        Point[] points = {new Point(-1.0,-1.0), new Point(0.0,0.0), new Point(1.0,0.0), new Point(1.0, -1.0)};
        Parameters parameters = new Parameters();
        parameters.epsilon = PI/18.0; // Epsilon is 10 degrees (measured in radians)
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null,null);
        assertTrue(testSystem.lic2());

        testSystem.parameters.epsilon = PI*0.499; // Epsilon is almost 90 degrees (measured in radians)
        assertTrue(testSystem.lic2());

        testSystem.parameters.epsilon = PI*0.50; // Epsilon is 90 degrees (measured in radians)
        assertFalse(testSystem.lic2());
    }
  
    @Test
    void testLic3() {
        // Contract: Lic3 returns true iff there exists at least one set of three consecutive
        // data points that are the vertices of a triangle with area greater than AREA1.

        int numPoints = 5;
        Point[] points = {new Point(1.0,1.0), new Point(1.0,0.0), new Point(0.0,0.0), new Point(-3.0,3.0), new Point(-3.0,0.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints,points,parameters,null,null);
        assertTrue(testSystem.lic3());

        testSystem.parameters.area1 = 1.0;
        assertTrue(testSystem.lic3());

        testSystem.parameters.area1 = 5.0;
        assertFalse(testSystem.lic3());
    }

    @Test
    void testLic4() {
        // Contract: Lic4 returns true iff there exists at least one set of
        // Q_PTS consecutive data points that lie in more than QUADS quadrants.

        int numPoints = 4;
        Point[] points1 = {new Point(1.0, 1.0), new Point(1.0, -1.0), new Point(-1.0, 1.0), new Point(-1.0, -1.0)};
        Parameters parameters = new Parameters();

        parameters.qPts = 2;
        parameters.qUads = 2;
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertFalse(testSystem.lic4());

        parameters.qPts = 3;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertTrue(testSystem.lic4());

        parameters.qUads = 3;
        parameters.qPts = numPoints;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertTrue(testSystem.lic4());

        parameters.qPts = 2;
        testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertFalse(testSystem.lic4());
    }

    @Test
    void testLic5() {
        // Contract: Lic5 returns true iff there exists at least one set of two data points
        // [X(i), Y(i)], [X(j), Y(j)] where X(j) - X(i) < 0 and i = j-1

        int numpoints = 1;
        Point[] points = {new Point(0.0,0.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numpoints,points,parameters,null,null);
        assertFalse(testSystem.lic5());

        numpoints++;
        Point[] invalidPoints = {new Point(0.0,0.0), new Point(1.0,0.0)};
        parameters = new Parameters();
        testSystem = new AntiMissileSystem(numpoints,invalidPoints,parameters,null,null);
        assertFalse(testSystem.lic5());

        Point[] invalidXPoints = {new Point(0.0,1.0), new Point(0.0,1.0)};
        parameters = new Parameters();
        testSystem = new AntiMissileSystem(numpoints,invalidXPoints,parameters,null,null);
        assertFalse(testSystem.lic5());

        Point[] validPoints = {new Point(1.0,0.0), new Point(0.0,0.0)};
        parameters = new Parameters();
        testSystem = new AntiMissileSystem(numpoints,validPoints,parameters,null,null);
        assertTrue(testSystem.lic5());
    }

    @Test
    void testLic7() {
        // Contract: Lic9 returns true iff there exists at least one set of two data points
        // separated by exactly K_PTS consecutive intervening points that are a distance greater than the length, LENGTH1, apart.
        // The condition is not met when NUMPOINTS < 3.
        int numpoints = 2;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numpoints,points,parameters,null,null);
        assertFalse(testSystem.lic7());

        testSystem.numPoints++;
        Point[] unvalidPoints = {new Point(0.0,0.0), new Point(1.0,0.0), new Point(0.0,1.0)};
        testSystem.points = unvalidPoints;
        testSystem.parameters.kPTS = 1;
        testSystem.parameters.length1 = 2.0;
        assertFalse(testSystem.lic7());

        Point[] validPoints = {new Point(0.0,0.0), new Point(3.0,0.0), new Point(5.0,0.0)};
        testSystem.points = validPoints;
        assertTrue(testSystem.lic7());
    }

    @Test
    void testLic8() {
        // Contract: There exists at least one set of three data points
        // separated by exactly A PTS and B PTS consecutive intervening
        // points, respectively, that cannot be contained within or on a
        // circle of radius RADIUS1. The condition is not met when
        // NUMPOINTS < 5.
        //1≤A PTS,1≤B PTS
        //A PTS+B PTS ≤ (NUMPOINTS−3)

        int numPoints = 3;
        Point[] points1 = {
                new Point(-1.0, 0.0),
                new Point(0.0, 1.0),
                new Point(1.0, 0.0)
        };
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points1, parameters, null, null);
        assertFalse(testSystem.lic8());

        // Add consecutive intervening points
        numPoints = 6;
        Point[] points2 = {
                new Point(-1.0, 0.0), new Point(1.5, 0.5), new Point(1.5, -0.5),
                new Point(0.0, 1.0), new Point(-1.5, 0.5),
                new Point(1.0, 0.0)
        };
        parameters.aPTS = 2;
        parameters.bPTS = 1;

        // Small radius
        parameters.radius1 = 0.5;
        testSystem = new AntiMissileSystem(numPoints, points2, parameters, null, null);
        assertTrue(testSystem.lic8());

        // Big radius
        parameters.radius1 = 2.0;
        testSystem = new AntiMissileSystem(numPoints, points2, parameters, null, null);
        assertFalse(testSystem.lic8());

        // Assigning new points where all points are outside the circle with radius radius1
        parameters.radius1 = 1.0;
        points2[0] = new Point(-2.0, 0.0);
        points2[3] = new Point(0.0, 2.0);
        points2[5] = new Point(2.0, 0.0);
        testSystem = new AntiMissileSystem(numPoints, points2, parameters, null, null);
        assertTrue(testSystem.lic8());
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

    @Test
    void testinCircle() {
        // Contract: inCircle() returns true if points a, b and c can be contained in a circle with radius 'radius'.
        int numPoints = 0;
        Point[] points = {};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);

        Point a = new Point(0,0);
        Point b = new Point(0,0);
        Point c = new Point(0,0);
        double radius = 1;
        assertTrue(testSystem.inCircle(a, b, c, radius));

        a = new Point(0,0);
        b = new Point(0,1);
        c = new Point(1,0);
        radius = 0.5;
        assertFalse(testSystem.inCircle(a, b, c, radius));

        a = new Point(0,0);
        b = new Point(0,1);
        c = new Point(1,1);
        radius = sqrt(2);
        assertTrue(testSystem.inCircle(a, b, c, radius));

        a = new Point(-1,0);
        b = new Point(0,0);
        c = new Point(1,1);
        radius = 0;
        assertFalse(testSystem.inCircle(a, b, c, radius));

        a = new Point(-1,0);
        b = new Point(0,0);
        c = new Point(1,1);
        radius = 0.1;
        assertFalse(testSystem.inCircle(a, b, c, radius));

        a = new Point(0,0);
        b = new Point(1,1);
        c = new Point(2,2);
        radius = 10;
        assertTrue(testSystem.inCircle(a, b, c, radius));
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
  
    @Test
    void testLic12() {
        // Contract: Lic 12 returns true iff there exists a set of two points separated by K_PTS consecutive
        // intervening points such that the distance between the points is greater than LENGTH1 and there exists
        // a set of two points (possible the same set as previously mentioned) separated by K_PTS consecutive
        // intervening points such that the distance between the points is less than LENGTH2.
        // Lic 12 returns false if NUMPOINTS < 3.
        int numPoints = 2;
        Point[] points = {new Point(0.0,0.0), new Point(1.0,1.0)};
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints,points,parameters,null,null);
        assertFalse(testSystem.lic12());

        testSystem.numPoints++;
        Point[] newPoints = {new Point(0.0,0.0), new Point(5.0,0.0), new Point(3.0,0.0)};
        testSystem.points = newPoints;
        testSystem.parameters.kPTS = 1;
        testSystem.parameters.length1 = 2.0;
        testSystem.parameters.length2 = 5.0;
        assertTrue(testSystem.lic12());
    }

    @Test
    void testLic13() {
        // Contract: Lic13 return true if there exists at least one set of three data points, separated by exactly A PTS
        // and B PTS consecutive intervening points, respectively, that cannot be contained within or on a circle of
        // radius RADIUS1.
        //
        // In addition, there exists at least one set of three data points (which can be the same or different from the three
        // data points just mentioned) separated by exactly A PTS and B PTS consecutive intervening points, respectively,
        // that can be contained in or on a circle of radius RADIUS2.
        //
        // Both parts must be true for the LIC to be true. The condition is not met when NUMPOINTS < 5. 0 ≤ RADIUS2.

        int numPoints = 4;
        Point[] points = {
                new Point(0.0, 0.0),
                new Point(1.0, 0.0),
                new Point(2.0, 0.0),
                new Point(3.0, 0.0),
                new Point(3.0, 3.0)
        };
        Parameters parameters = new Parameters();
        AntiMissileSystem testSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);
        assertFalse(testSystem.lic13());

        testSystem.parameters.radius2 = 0;
        testSystem.numPoints = 5;
        assertFalse(testSystem.lic13());

        testSystem.parameters.radius1 = 10;
        testSystem.parameters.radius2 = 1;
        testSystem.parameters.aPTS = 0;
        testSystem.parameters.bPTS = 0;
        assertFalse(testSystem.lic13());

        testSystem.parameters.radius1 = 1;
        testSystem.parameters.radius2 = 10;
        assertTrue(testSystem.lic13());
    }

    @Test
    void testLic14() {
        // Contract: Lic14 returns true iff there exists at least one set of three data points
        // separated by exactly E_PTS and F_PTS consecutive intervening points, respectively,
        // that are the vertices of a triangle with area greater than AREA1. In addition, there
        // exist three data points (which can be the same or different from the three data points
        // just mentioned) separated by exactly E PTS and F PTS consecutive intervening points,
        // respectively, that are the vertices of a triangle with area less than AREA2.
        // Both parts must be true for the LIC to be true
        // The condition is not met when NUMPOINTS < 5.

        int numPoints = 4;
        Point[] points = {
                new Point(0.0,0.0),
                new Point(1.0,1.0),
                new Point(1.0,0.0),
                new Point(-1.0,-1.0)
        };
        Parameters parameters = new Parameters();
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);
        assertFalse(antiMissileSystem.lic14());

        Point[] newPoints = {
                new Point(0.0,0.0),
                new Point(1.0,1.0),
                new Point(1.0,0.0),
                new Point(-1.0,-1.0),
                new Point(1.0,2.0)
        };

        // The same set of points meet both conditions
        antiMissileSystem.numPoints++;
        antiMissileSystem.points = newPoints;
        antiMissileSystem.parameters.ePTS = 1;
        antiMissileSystem.parameters.fPTS = 1;
        antiMissileSystem.parameters.area2 = 2;
        assertTrue(antiMissileSystem.lic14());

        // points 1, 3, 5 form a triangle with area = 1
        // points 4, 6, 8 form a triangle with area = 2,5
        Point[] diffPoints = {
                new Point(0.0,0.0),
                new Point(1.0,1.0),
                new Point(1.0,0.0),
                new Point(-1.0,-1.0),
                new Point(1.0,2.0),
                new Point(-2.0,0.0),
                new Point(1.0,-1.0),
                new Point(0.0,2.0)
        };

        // Different sets of points meet each conditions
        antiMissileSystem.numPoints = 8;
        antiMissileSystem.points = diffPoints;
        antiMissileSystem.parameters.ePTS = 1;
        antiMissileSystem.parameters.fPTS = 1;
        antiMissileSystem.parameters.area1 = 0.5;
        antiMissileSystem.parameters.area2 = 3;
        assertTrue(antiMissileSystem.lic14());
    }

    @Test
    void testPopulateCMV() {
        // Contract: global variable cmv gets populated with boolean values.
        Parameters parameters = new Parameters();
        int numPoints = 5;
        Point[] points = {
                new Point(0.0, 0.0),
                new Point(1.0, 0.0),
                new Point(2.0, 0.0),
                new Point(3.0, 0.0),
                new Point(3.0, 3.0)
        };
        AntiMissileSystem antiMissileSystem = new AntiMissileSystem(numPoints, points, parameters, null, null);
        antiMissileSystem.parameters.radius1 = 1;
        antiMissileSystem.parameters.radius2 = 10;
        antiMissileSystem.parameters.qUads = 1;
        antiMissileSystem.parameters.aPTS = 1;
        antiMissileSystem.parameters.bPTS = 1;
        antiMissileSystem.parameters.cPTS = 1;
        antiMissileSystem.parameters.dPTS = 1;
        antiMissileSystem.parameters.ePTS = 1;
        antiMissileSystem.parameters.fPTS = 1;
        antiMissileSystem.parameters.gPTS = 1;
        antiMissileSystem.parameters.kPTS = 1;
        antiMissileSystem.parameters.nPTS = 3;
        antiMissileSystem.parameters.dist = 1.5;
        antiMissileSystem.parameters.area2 = 2;

        for (int i = 0; i < 15; i++) {
            assertFalse(antiMissileSystem.cmv[i]);
        }

        antiMissileSystem.populateCMV();

        boolean[] bosse = {
                true,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                true,
                true,
                false,
                false,
                true,
                false
        };
        assertArrayEquals(bosse, antiMissileSystem.cmv);
    }
}
