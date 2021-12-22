package org.la;

/**
 * This interface was designed as a template to be implemented by other classes
 * It is not meant to be imported and used
 */
public interface Vector {
    /**
     * Checks if all components of the vector are zero
     * @return if instance vector is zero or not
     */
    boolean isZero();

    /**
     * Returns Euclidean norm of the vector
     * @return euclidean norm
     */
    double norm();
    
    /**
     * Returns the square of the euclidean norm
     * @return norm squared
     */
    double normSq();

    /**
     * Returns the Taxicab norm
     * @return taxicab norm
     */
    double norm1();

    /**
     * Infinity norm
     * @return infinity norm
     */
    double normInf();

    /**
     * Angle in radians between instance and {@code v}
     * @param v Vector
     * @return angle between instance and v
     */
    double angle(Vector v);

    /**
     * Dot product of instance and {@code v}
     * @param v Vector
     * @return dot product of instance and v
     */
    double dot(Vector v);

    /**
     * Euclidean distance between instance and vector {@code v}
     * @param v Vector
     * @return distance between instance and v
     */
    double distance(Vector v);

    /**
     * Euclidean distance between instance and point {@code p}
     * @param p Point
     * @return distance between instance and p
     * @see {@link #distance(Vector)}
     */
    double distance(Point p);

    /**
     * Manhattan distance between instance and vector {@code v}
     * @param v Vector
     * @return manhattan distance between instance and v
     */
    double distance1(Vector v);

    /**
     * Square of the distance between instance and vector {@code v}
     * @param v Vector
     * @return distance between instance and v sqaured
     */
    double distanceSq(Vector v);

    /**
     * Max difference in distance
     * @param v Vector
     * @return max difference
     */
    double distanceInf(Vector v);

    /**
     * sum of instance and given vector as a new Vector
     * @param v Vector
     * @return sum of instance and v
     */
    Vector add(Vector v);

    /**
     * difference of instance and given vector as a new Vector
     * @param v Vector
     * @return instance minus v
     */
    Vector sub(Vector v);

    /**
     * returns instance multiplied by value as new Vector
     * @param a a value
     * @return instance times a
     */
    Vector scale(double a);

    /**
     * returns the negation of instance as new Vector
     * @return -instance
     */
    Vector negate();

    /**
     * returns normalized Vector of instance
     * @return instance normalized
     * @throws ArithmeticException
     */
    Vector normalize() throws ArithmeticException;

    /**
     * Returns conventional string representation of instance
     * @return string of instance
     */
    String toString();

    static void fail(String message) {
        throw new IllegalArgumentException(message);
    }
}