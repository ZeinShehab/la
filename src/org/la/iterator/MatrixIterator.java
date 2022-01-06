package org.la.iterator;

import java.util.Iterator;

public abstract class MatrixIterator implements Iterator<Double> {
    /**
     * Default constructor
     */
    public MatrixIterator() {};

    /**
     * Returns the index of the current row
     */
    public abstract int rowIndex();

    /**
     * Returns the index of the current column
     */
    public abstract int colIndex();

    /**
     * Returns the value at the current index
     */
    public abstract double get();

    /**
     * Set the value at the current index to {@code value}
     */
    public abstract void set(double value); 
}