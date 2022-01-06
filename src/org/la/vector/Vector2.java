package org.la.vector;

import org.la.Vector;

/**
 * A 2D vector is represented by an array of 2 real numbers. It has a fixed size.
 * @see org.la.Vector
 */
public class Vector2 extends Vector {
    /**
     * Creates a 2D vector from {@code x} and {@code y}
     */
    public Vector2(double x, double y) {
        super(x, y);
    }    

    /**
     * Creates a 2D vector from {@code v} while copying the elements
     * <p>If the length of {@code v} is not 2, an {@code IllegalArgumentException}
     * will be thrown</p>
     * @throws IllegalArgumentException
     */
    public Vector2(double[] v) {
        super(v);

        if (v.length != 2) {
            throw new IllegalArgumentException("Invalid size for 2D Vector");
        }
    }

    /**
     * Creates a 2D vector from {@code v}.
     * <p> If {@code v} has more than 2 components then they are ignored
     * and only the first 2, x and y, are taken </p>
     * <p>This constructor does not handle the case 
     * when the size of {@code v} is less than 2 and it will fail</p>
     */
    public Vector2(Vector v) {
        this(v.get(0), v.get(1));
    }

    /**
     * Returns the X component of the vector
     */
    public double getX() {
        return get(0);
    }

    /**
     * Returns the Y component of the vector
     */
    public double getY() {
        return get(1);
    }

    /**
     * Returns the cross product of vector with {@code u}
     * @param u a vector
     * @return v x u
     */
    public Vector3 cross(Vector u) {
        Vector2 v2 = new Vector2(u);
        return new Vector3(0, 0, getX()*v2.getY() - v2.getX()*getY());
    }
}