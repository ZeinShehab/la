package org.la.iterator;

import java.util.Iterator;

public abstract class VectorIterator implements Iterator<Double> {
    public VectorIterator() {}

    public abstract int index();

    public abstract double get();
    
    public abstract void set(double value);
}
