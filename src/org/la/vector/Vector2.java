package org.la.vector;

import org.la.Vector;

public class Vector2 extends Vector {
    public Vector2(double x, double y) {
        super(x, y);
    }    

    public Vector2(double[] v) {
        super(v);

        if (v.length != 2) {
            throw new IllegalArgumentException("Invalid size for 2D Vector");
        }
    }

    public Vector2(Vector v) {
        this(v.toArray());
    }

    public double getX() {
        return get(0);
    }

    public double getY() {
        return get(1);
    }

    public Vector3 cross(Vector v) {
        Vector2 v2 = new Vector2(v);
        return new Vector3(0, 0, getX()*v2.getY() - v2.getX()*getY());
    }
}
