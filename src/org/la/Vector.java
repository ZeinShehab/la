package org.la;

import java.util.NoSuchElementException;

import org.la.factory.VectorFactory;
import org.la.iterator.VectorIterator;

public class Vector implements Iterable<Double> {
    private double[] v;
    private int length;

    public Vector(double ... v) {
        this.length = v.length;
        this.v = new double[length];
        System.arraycopy(v, 0, this.v, 0, length);
    }

    public static Vector fromArray(double ... v) {
        return VectorFactory.fromArray(v);
    }

    public static Vector fromString(String vecString) {
        return VectorFactory.fromString(vecString);
    }

    public static Vector fromLength(int length) {
        return VectorFactory.fromLength(length);
    }

    public static Vector fromRange(double start, double end, double step) {
        return VectorFactory.fromRange(start, end, step);
    }

    public static Vector fromRange(double start, double end) {
        return VectorFactory.fromRange(start, end);
    }

    public static Vector fromRange(int end) {
        return VectorFactory.fromRange(end);
    }

    public static Vector fromMatrixRow(Matrix m, int row) {
        return m.getRow(row);
    }

    public static Vector fromMatrixCol(Matrix m, int col) {
        return m.getCol(col);
    }

    public double get(int index) {
        return this.v[index];
    }

    public void set(int index, double value) {
        this.v[index] = value;
    }

    public void setAll(double value) {
        VectorIterator it = iterator();

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    public int length() {
        return length;
    }

    public boolean isZero() {
        VectorIterator it = iterator();
        
        while (it.hasNext()) {
            if (it.next() != 0) 
                return false;
        }
        return true;
    }

    public Vector blank() {
        return blankOfLength(length);
    }

    public static Vector blankOfLength(int length) {
        return fromLength(length);
    }

    public Vector negate() {
        return mul(-1);
    }

    public Vector add(double a) {
        VectorIterator it = iterator();
        Vector result = blank();

        while(it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x + a);
        }
        return result;
    }

    public Vector add(Vector v) {
        checkLengths(v);

        VectorIterator it = iterator();
        Vector result = blank();

        while(it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x + v.get(i));
        }
        return result;
    }

    public Vector sub(double a) {
        return add(-a);
    }

    public Vector sub(Vector v) {
        return add(v.negate());
    }

    public Vector mul(double a) {
        VectorIterator it = iterator();
        Vector result = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x * a);
        }
        return result;
    }

    public Vector mul(Vector v) {
        checkLengths(v);

        VectorIterator it = iterator();
        Vector result = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x * v.get(i));
        }
        return result;
    }

    public Vector mul(Matrix m) {
        VectorIterator it = iterator();
        Vector res = blank();

        if (!m.isSquare())
            // TODO non square matrices?
            throw new IllegalArgumentException("Linear transformation matrix must be square for now");

        while (it.hasNext()) {
            it.next();
            int i = it.index();
            res.set(i, dot(m.getRow(i)));
        }
        return res;
    }

    public double dot(Vector v) {
        checkLengths(v);

        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result += x * v.get(i);
        }
        return result;
    }

    public Vector div(double a) {
        return mul(1.0/a);
    }

    public double norm() {
        return euclideanNorm();
    }

    public double normSq() {
        double x = norm();
        return x*x;
    }

    public double euclideanNorm() {
        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            double x = it.next();
            result += x*x;
        }
        return Math.sqrt(result);
    }

    public double manhattanNorm() {
        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            result += Math.abs(it.next());
        }
        return result;
    }

    public double infinityNorm() {
        VectorIterator it = iterator();
        double result = 0;

        if (it.hasNext())
            result = it.next();

        while (it.hasNext()) {
            double x = Math.abs(it.next());
            if (x > result)
                result = x;
        }
        return result;
    }

    public double angle(Vector v) {
        return Math.acos(dot(v) / (norm() * v.norm()));
    }

    public Vector normalize() {
        if (isZero()) {
            fail("Cannot normalize zero vector");
        }
        return div(norm());
    }

    public double[] toArray() {
        double[] res = new double[length];
        System.arraycopy(v, 0, res, 0, length);
        return res;
    }

    @Override
    public String toString() {
        String vec = "[";

        for (int i = 0; i < length; i++) {
            String fmt = String.format("%.3f", v[i]);

            if (v[0] < 0 && i != 0)
                vec += " ";

            if (fmt.charAt(0) != '-' && i != 0)
                vec += " " + fmt;
            else vec += fmt;
        
            if (i < length - 1)
                vec += "\n";
        }
        return vec + "]";
        // return Arrays.toString(v);
    } 

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector)
            return equals((Vector) obj, Linear.EPS);
        return false;
    }

    public boolean equals(Vector v, double tolerance) {
        if (this == v)
            return true;
        
        if (this.length != v.length())
            return false;

        VectorIterator it = iterator();
        boolean res = true;

        while (it.hasNext() && res) {
            double a = it.next();
            double b = v.get(it.index());
            double d = Math.abs(a - b);

            res = (a == b) || (d < tolerance);
        }       
        return res;
    }

    @Override
    public Vector clone() {
        return new Vector(v);
    }

    @Override
    public VectorIterator iterator() {
        return new VectorIterator() {
            private int index = -1;

            @Override
            public int index() {
                return index;
            }

            @Override
            public double get() {
                return Vector.this.get(index);
            }

            @Override
            public void set(double value) {
                Vector.this.set(index, value);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < length;
            }

            @Override
            public Double next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                index++;
                return get();
            }
        };
    }

    private void checkLengths(Vector v) {
        if (length != v.length())
            fail("Vectors have different sizes");
    }
    
    private void fail(String message) {
        throw new IllegalArgumentException(message);
    }
}