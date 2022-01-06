package org.la.vector;

import org.la.Vector;

/**
 * A #D vector is represented by an array of 3 real numbers. It has a fixed size.
 * @see org.la.Vector
 */
public class Vector3 extends Vector {
    /**
     * Creates a 3D vector from {@code x}, {@code y} and {@code z}
     */
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    /**
     * Creates a 3D vector from {@code v} while copying the elements
     * <p>If the length of {@code v} is not 3, an {@code IllegalArgumentException}
     * will be thrown</p>
     * @throws IllegalArgumentException
     */
    public Vector3(double[] v) {
        super(v);

        if (v.length != 3) 
            throw new IllegalArgumentException("Invalid size for 3D Vector");
    }

    /**
     * Creates a 3D vector from {@code v}.
     * <p> If {@code v} has more than 3 components then they are ignored
     * and only the first 3, x,y and z, are taken </p>
     * <p>This constructor does not handle the case 
     * when the size of {@code v} is less than 3 and it will fail</p>
     */
    public Vector3(Vector v) {
        this(v.get(0), v.get(1), v.get(2));
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
     * Returns the Z component of the vector
     */
    public double getZ() {
        return get(2);
    }

    /**
     * Returns the cross product of vector with {@code u}
     * @param u a vector
     * @return v x u
     */
    public Vector3 cross(Vector u) {
        Vector3 v3 = new Vector3(u);
        return new Vector3( getY() * v3.getZ() - getZ() * v3.getY(),
                            getZ() * v3.getX() - getX() * v3.getZ(),
                            getX() * v3.getY() - getY() * v3.getX());
    }

    /**
     * Returns the scalar triple product of the vector {@code v} with
     * {@code u} and {@code w} 
     * @param u 2nd vector
     * @param w 3rd vector
     * @return v dot (u cross w)
     */
    public double tripleProduct(Vector u, Vector w) {
        Vector3 v3 = new Vector3(u);
        return this.dot(v3.cross(w));
    }
}