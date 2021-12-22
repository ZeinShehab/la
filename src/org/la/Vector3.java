package org.la;

public class Vector3 implements Vector {
    private double x;
    private double y;
    private double z;

    public Vector3() {
        this(0, 0, 0);
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(double[] v) {
        if (v.length != 3)
            Vector.fail("Invalid array size for 3D vector");

        this.x = v[0];
        this.y = v[1];
        this.y = v[2];
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double[] toArray() {
        return new double[]{x, y, z};
    }

    /** {@inheritDoc} */
    public boolean isZero() {
        return (x == 0 && y == 0 && z == 0);
    }

    /** {@inheritDoc} */
    public double norm() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    /** {@inheritDoc} */
    public double normSq() {
        return x*x + y*y + z*z;
    }

    /** {@inheritDoc} */
    public double norm1() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    /** {@inheritDoc} */
    public double normInf() {
        return Math.max(Math.abs(x), Math.max(Math.abs(y), Math.abs(y)));
    }

    /** {@inheritDoc} */
    public Vector3 add(Vector v) {
        Vector3 v3 = (Vector3) v;
        return new Vector3(x + v3.getX(), y + v3.getY(), z + v3.getZ());
    }

    /** {@inheritDoc} */
    public Vector3 sub(Vector v) {
        Vector3 v3 = (Vector3) v;
        return new Vector3(x - v3.getX(), y - v3.getY(), z - v3.getZ());
    }

    /** {@inheritDoc} */
    public Vector3 normalize() {
        if (isZero())
            throw new ArithmeticException("Cannot normalize zero vector");

        return scale(1.0/norm());
    }

    /** {@inheritDoc} */
    public Vector3 scale(double a) {
        return new Vector3(x*a, y*a, z*a);
    }

    /**
     * returns the cross product of instance and given vector {@code v} as Vector3
     * @param v Vector
     * @return cross product of instance and v
     */
    public Vector3 cross(Vector v) {
        Vector3 v3 = (Vector3) v;
        return new Vector3(y * v3.getZ() - z * v3.getY(),
                            z * v3.getX() - x * v3.getZ(),
                            x * v3.getY() - y * v3.getX());
    }

    /** {@inheritDoc} */
    public double dot(Vector v) {
        Vector3 v3 = (Vector3) v;
        return x*v3.getX() + y*v3.getY() + z*v3.getZ();
    }

    /**
     * returns the scalar triple product of instance, {@code v} and {@code u}
     * this.dot(cross(v, u))
     * @param v second Vector
     * @param u third Vector
     * @return scalar triple product of instance v and u
     */
    public double scalarTripleProduct(Vector v, Vector u) {
        Vector3 v3 = (Vector3) v;
        return this.dot(v3.cross(u));
    }

    /** {@inheritDoc} */
    public double angle(Vector v) {
        Vector3 v3 = (Vector3) v;
        return Math.acos(this.dot(v) / (this.norm() * v3.norm()));
    }

    /** {@inheritDoc} */
    public double distance(Vector v) {
        Vector3 v3 = (Vector3) v;
        double dx = v3.getX() - x;
        double dy = v3.getY() - y;
        double dz = v3.getZ() - z;
        return Math.sqrt(dx*dx + dy*dy + dz*dz);
    } 

    /** {@inheritDoc} */
    public double distance(Point p) {
        return distance((Vector3) p);
    }

    /** {@inheritDoc} */
    public double distance1(Vector v) {
        Vector3 v3 = (Vector3) v;
        double dx = Math.abs(v3.getX() - x);
        double dy = Math.abs(v3.getY() - y);
        double dz = Math.abs(v3.getZ() - z);
        return dx + dy + dz;
    }

    /** {@inheritDoc} */
    public double distanceSq(Vector v) {
        Vector3 v3 = (Vector3) v;
        double dx = v3.getX() - x;
        double dy = v3.getY() - y;
        double dz = v3.getZ() - z;
        return dx*dx + dy*dy + dz*dz;
    } 

    /** {@inheritDoc} */
    public double distanceInf(Vector v) {
        Vector3 v3 = (Vector3) v;
        double dx = Math.abs(v3.getX() - x);
        double dy = Math.abs(v3.getY() - y);
        double dz = Math.abs(v3.getZ() - z);
        return Math.max(dx, Math.max(dy, dz));
    }

    /** {@inheritDoc} */
    public Vector3 negate() {
        return new Vector3(-x, -y, -z);
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;

        if (obj instanceof Vector3) {
            Vector3 v3 = (Vector3) obj;
            return (x == v3.getX()) && (y == v3.getY()) && (z == v3.getZ());
        }
        return false;
    }

    /**
     * returns clone of instance
     * @return clone of instance
     */
    @Override
    public Vector3 clone() {
        return new Vector3(x, y, z);
    }
}