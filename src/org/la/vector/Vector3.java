package org.la.vector;

import org.la.Vector;

public class Vector3 extends Vector {
    public Vector3(double x, double y, double z) {
        super(x, y, z);
    }

    public Vector3(double[] v) {
        super(v);

        if (v.length != 3) 
            throw new IllegalArgumentException("Invalid size for 3D Vector");
    }

    public Vector3(Vector v) {
        this(v.toArray());
    }   

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public double getZ() {
        return get(2);
    }

    public Vector3 cross(Vector v) {
        Vector3 v3 = new Vector3(v);
        return new Vector3( getY() * v3.getZ() - getZ() * v3.getY(),
                            getZ() * v3.getX() - getX() * v3.getZ(),
                            getX() * v3.getY() - getY() * v3.getX());
    }

    public double scalarTripleProduct(Vector v, Vector u) {
        Vector3 v3 = new Vector3(v);
        return this.dot(v3.cross(u));
    }
}