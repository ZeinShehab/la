package org.la.iterator;

import java.util.Iterator;

public abstract class MatrixIterator implements Iterator<Double> {
    public MatrixIterator() {};

    public abstract double get();

    public abstract int rowIndex();

    public abstract int colIndex();

    public abstract void set(double value); 
}
