package org.la.iterator;

import java.util.Iterator;

public abstract class VectorIterator implements Iterator<Double> {
    /**
     * Default constructor
     */
    public VectorIterator() {}

    /**
     * Returns the current index
     */
    public abstract int index();

    /**
     * Retuns the value at the current index
     */
    public abstract double get();
    
    /**
     * Sets the value at the current index to {@code value} 
     */
    public abstract void set(double value);
}