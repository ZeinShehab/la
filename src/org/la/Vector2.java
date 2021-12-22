package org.la;

public class Vector2 implements Vector {
    private double x;
    private double y;

    public Vector2() {
        this(0,0);
    }

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(double[] v) {
        if (v.length != 2)  
            Vector.fail("Invalid array size for 2D vector");

        this.x = v[0];
        this.y = v[1];
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double[] toArray() {
        return new double[]{x, y};
    }

    /** {@inheritDoc} */
    public boolean isZero() {
        return (x == 0 && y == 0);
    }

    /** {@inheritDoc} */
    public double norm() {
        return Math.sqrt(x*x + y*y);
    }

    /** {@inheritDoc} */
    public double normSq() {
        return x*x + y*y;
    }

    /** {@inheritDoc} */
    public double norm1() {
        return Math.abs(x) + Math.abs(y);
    }

    /** {@inheritDoc} */
    public double normInf() {
        return Math.max(Math.abs(x), Math.abs(y));
    }

    /** {@inheritDoc} */
    public Vector2 add(Vector v) {
        Vector2 v2 = (Vector2) v;
        return new Vector2(x + v2.getX(), y + v2.getY());
    }

    /** {@inheritDoc} */
    public Vector2 sub(Vector v) {
        Vector2 v2 = (Vector2) v;
        return new Vector2(x - v2.getX(), y - v2.getY());
    }

    /** {@inheritDoc} */
    public Vector2 normalize() {
        if (isZero())
            throw new ArithmeticException("Cannot normalize a zero vector");

        return scale(1.0/norm());
    }

    /** {@inheritDoc} */
    public Vector2 scale(double a) {
        return new Vector2(x * a, y * a);
    }

    /** {@inheritDoc} */
    public double angle(Vector v) {
        Vector2 v2 = (Vector2) v;
        return Math.acos(this.dot(v) / (this.norm() * v2.norm()));
    }

    /** {@inheritDoc} */
    public double dot(Vector v) {
        Vector2 v2 = (Vector2) v;
        return x * v2.getX() + y * v2.getY();
    }

    /** {@inheritDoc} */
    public double distance(Vector v) {
        Vector2 v2 = (Vector2) v;
        double dx = v2.getX() - x;
        double dy = v2.getY() - y;
        return Math.sqrt(dx*dx + dy*dy);
    }

    /** {@inheritDoc} */
    public double distance(Point p) {
        return distance((Vector2) p);
    }

    /** {@inheritDoc} */
    public double distance1(Vector v) {
        Vector2 v2 = (Vector2) v;
        double dx = Math.abs(v2.getX() - x);
        double dy = Math.abs(v2.getY() - y);
        return dx + dy;
    }

    /** {@inheritDoc} */
    public double distanceSq(Vector v) {
        Vector2 v2 = (Vector2) v;
        double dx = v2.getX() - x;
        double dy = v2.getY() - y;
        return dx*dx + dy*dy;
    }

    /** {@inheritDoc} */
    public double distanceInf(Vector v) {
        Vector2 v2 = (Vector2) v;
        double dx = Math.abs(v2.getX() - x);
        double dy = Math.abs(v2.getY() - y);
        return Math.max(dx, dy);
    }

    /** {@inheritDoc} */
    public Vector2 negate() {
        return new Vector2(-x, -y);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof Vector2) {
            Vector2 v2 = (Vector2) obj;
            return (x == v2.getX()) && (y == v2.getY()); 
        }
        return false;
    }

    /**
     * returns clone of instance
     * @return clone of instance
     */
    @Override
    public Vector2 clone() {
        return new Vector2(x, y);
    }
}