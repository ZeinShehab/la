package org.la;

import java.util.NoSuchElementException;

import org.la.factory.VectorFactory;
import org.la.iterator.VectorIterator;

/**
 * A vector is represented by an array of real numbers. It has a fixed size
 */
public class Vector implements Iterable<Double> {
    private double[] v;
    private int length;

    /**
     * @see {@link #Vector(double[], boolean)}
     */
    public Vector(double ... v) {
        this(v, true);
    }

    /**
     * Creates a new vector from given array {@code arr}. If {@code copy} is set to true it copies the array
     * otherwise it points the internal vector array to the same array. It is true by default
     */
    public Vector(double[] arr, boolean copy) {
        if (!copy)
            this.v = arr;
        else
            this.v = ArrayUtils.copyOf(arr);
        this.length = v.length;
    }

    /**
     * Creates a new vector from given array {@code v} while copying the given array
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromArray(double ... v) {
        return new Vector(v, true);
    }

    /**
     * Creates a new vector from given string {@code vecString}
     * It uses strings of the format {@code [1 2 3]} where spaces seperate the elements
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromString(String vecString) {
        return VectorFactory.fromString(vecString);
    }

    /**
     * Creates a new vector of given length {@code length}
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromLength(int length) {
        return VectorFactory.fromLength(length);
    }

    /**
     * Creates a new vector of given range
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromRange(double start, double end, double step) {
        return VectorFactory.fromRange(start, end, step);
    }

    /**
     * Creates a new vector of given range with step set to 1 by default
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromRange(double start, double end) {
        return VectorFactory.fromRange(start, end);
    }

    /**
     * Creates a new vector from range with the {@code start=0} and {@code step=1}  
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromRange(int end) {
        return VectorFactory.fromRange(end);
    }

    /**
     * Creates a new vector of {@code value}s of length {@code length}
     * @see org.la.factory.VectorFactory
     */
    public static Vector fromConstant(int length, double value) {
        return VectorFactory.fromConstant(length, value);
    }

    /** 
     * Creates a new vector from given matrix {@code m} at row {@code row} 
     */
    public static Vector fromMatrixRow(Matrix m, int row) {
        return m.getRow(row);
    }

    /** 
     * Creates a new vector from given matrix {@code m} at col {@code col} 
     */
    public static Vector fromMatrixCol(Matrix m, int col) {
        return m.getCol(col);
    }

    /**
     * Returns the value at {@code index}
     * @param index index
     * @return value at index
     */
    public double get(int index) {
        return this.v[index];
    }

    /**
     * Sets the value at {@code index} to {@code value} 
     * @param index index
     * @param value a value
     */
    public void set(int index, double value) {
        this.v[index] = value;
    }

    /**
     * Sets all values of the vector to {@code value}
     * @param value a value
     */
    public void setAll(double value) {
        VectorIterator it = iterator();

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    /**
     * Returns the length of the vector
     */
    public int length() {
        return length;
    }

    /**
     * Returns the first element of the vector
     */
    public double head() {
        return v[0];
    }

    /**
     * Returns the last element of the vector
     */
    public double tail() {
        return v[length-1];
    }

    /**
     * Returns whether all the elements of the vector are zero or not
     */
    public boolean isZero() {
        VectorIterator it = iterator();
        
        while (it.hasNext()) {
            if (it.next() != 0) 
                return false;
        }
        return true;
    }

    /**
     * Creates a new vector of instance {@code length} initialized to all zeros
     * @see {@link #blankOfLength(int)}
     * @return blank vector
     */
    public Vector blank() {
        return blankOfLength(length);
    }

    /**
     * Creates a new vector of length {@code length} initialized to all zeros
     * @return blank vector
     */
    public static Vector blankOfLength(int length) {
        return fromLength(length);
    }

    /**
     * Returns negation of instance vector {@code v}
     * @return -v
     * @see {@link #mul(double)}
     */
    public Vector negate() {
        return mul(-1);
    }

    /**
     * Returns new vector with {@code a} added to all elements of instance {@code v}
     * @param a a value
     * @return v + a
     */
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

    /**
     * Returns sum of instance vector {@code v} and {@code u}
     * @param u a vector
     * @return v + u
     */
    public Vector add(Vector u) {
        checkLengths(u);

        VectorIterator it = iterator();
        Vector result = blank();

        while(it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x + u.get(i));
        }
        return result;
    }

    /**
     * Returns new vector with {@code a} subtracted from all elements of instance {@code v}
     * @param a a value
     * @return v - a
     * @see {@link #add(double)}
     */
    public Vector sub(double a) {
        return add(-a);
    }

    /**
     * Returns difference of instance vector {@code v} and {@code u}
     * @param u a vector
     * @return v - u
     * @see {@link #add(Vector)}
     * @see {@link #negate()}
     */
    public Vector sub(Vector u) {
        return add(u.negate());
    }

    /**
     * Returns new vector of instance {@code v} multiplied with {@code a}
     * @param a a value
     * @return v * a
     */
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

    /**
     * Returns a new vector of instance {@code v} multiplied by elements of vector {@code u}
     * @param u a vector
     * @return v * u
     */
    public Vector mul(Vector u) {
        checkLengths(u);

        VectorIterator it = iterator();
        Vector result = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result.set(i, x * u.get(i));
        }
        return result;
    }

    /**
     * Returns a new vector of instance vector {@code v} multiplied/transformed by matrix {@code m} 
     * @param m a square matrix
     * @return v * m
     */
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

    /**
     * Calculates the dot product of instance vector {@code v} and {@code u}
     * @param u a vector
     * @return v.u
     */
    public double dot(Vector u) {
        checkLengths(u);

        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            double x = it.next();
            int i = it.index();
            result += x * u.get(i);
        }
        return result;
    }

    /**
     * Returns new vector of instance {@code v} divided by {@code a}
     * @param a a non zero value
     * @return v / a
     */
    public Vector div(double a) {
        if (a == 0)
            throw new ArithmeticException("Can't divide by zero");
        return mul(1.0/a);
    }

    /**
     * Calculates the euclidean norm of instance vector {@code v}
     * @return euclidean norm
     * @see {@link #euclideanNorm()}
     */
    public double norm() {
        return euclideanNorm();
    }

    /**
     * Calculates the square of the euclidean norm of instance
     * @return eucliedean norm squared
     */
    public double normSq() {
        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            double x = it.next();
            result += x*x;
        }
        return result;
    }

    /**
     * Calculates the euclidean norm of the instance vector
     * @return euclidean norm
     */
    public double euclideanNorm() {
        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            double x = it.next();
            result += x*x;
        }
        return Math.sqrt(result);
    }

    /**
     * Calculates the manhattan norm of the instance vector
     * @return manhattan norm
     */
    public double manhattanNorm() {
        VectorIterator it = iterator();
        double result = 0;

        while (it.hasNext()) {
            result += Math.abs(it.next());
        }
        return result;
    }

    /**
     * Calculates the infinity norm of the instance vector
     * @return infinity norm
     */
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

    /**
     * Calculates the angle between instance vector {@code v} and {@code u}
     * @param u a vector
     * @return angle between v and u
     */
    public double angle(Vector u) {
        return Math.acos(dot(u) / (norm() * u.norm()));
    }

    /**
     * Returns normalized vector of instance vector
     */
    public Vector normalize() {
        if (isZero()) {
            fail("Cannot normalize zero vector");
        }
        return div(norm());
    }

    /**
     * Finds the minimum value of the instance
     */
    public double min() {
        VectorIterator it = iterator();
        double x = 0;

        if (length > 0)
            x = it.next();
        else
            fail("Can't find min of vector with length 0");

        while (it.hasNext()) {
            double y = it.next();
            if (y < x)
                x = y;
        }
        return x;
    }

    /**
     * Finds the maximum value of the instance
     */
    public double max() {
        VectorIterator it = iterator();
        double x = 0;

        if (length > 0)
            x = it.next();
        else
            fail("Can't find min of vector with length 0");

        while (it.hasNext()) {
            double y = it.next();
            if (y > x)
                x = y;
        }
        return x;
    }

    /**
     * Converts instance vector to an array
     */
    public double[] toArray() {
        return ArrayUtils.copyOf(v);
    }

    /**
     * Returns conventional string representation of vector
     */
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
    } 

    /**
     * Checks if instance vector is equal to {@code obj} with tolerance of {@code 1.0E-6}
     * @see org.la.Linear
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Vector)
            return equals((Vector) obj, Linear.EPS);
        return false;
    }

    /**
     * Checks if instance vector {@code v} equals {@code u} by given {@code tolerance}
     * @param u a vector
     * @param tolerance precision used to calculate equality
     * @return v = u
     */
    public boolean equals(Vector u, double tolerance) {
        if (this == u)
            return true;
        
        if (this.length != u.length())
            return false;

        VectorIterator it = iterator();
        boolean res = true;

        while (it.hasNext() && res) {
            double a = it.next();
            double b = u.get(it.index());
            double d = Math.abs(a - b);

            res = (a == b) || (d < tolerance);
        }       
        return res;
    }

    /**
     * Clones the instance vector
     */
    @Override
    public Vector clone() {
        return new Vector(v);
    }

    /**
     * Creates a new {@code VectorIterator}
     */
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

    /**
     * Makes sure instance vector and {@code u} have same length 
     * before performing some operations
     * @param u a vector
     */
    private void checkLengths(Vector u) {
        if (length != u.length())
            fail("Vectors have different sizes");
    }
    
    /**
     * Fails with an {@code IllegalArgumentException}
     * @param message error message
     */
    private void fail(String message) {
        throw new IllegalArgumentException(message);
    }
}