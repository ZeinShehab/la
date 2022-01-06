package org.la.factory;

import org.la.Linear;
import org.la.Vector;


public class VectorFactory {
    /**
     * Creates a new vector from given string {@code vecString}
     * It uses strings of the format {@code [1 2 3]} where spaces seperate the elements
     */
    public static Vector fromString(String vecString) {
        vecString = vecString.replaceAll("\\[|\\]", "");
        String[] vals = vecString.split(Linear.COL_DELIMITER);
        Vector res = fromLength(vals.length);

        for (int i = 0; i < vals.length; i++) {
            res.set(i, Double.parseDouble(vals[i]));
        }
        return res;
    }
    
    /**
     * Creates a new vector of given length {@code length}
     */
    public static Vector fromLength(int length) {
        return new Vector(new double[length], false);
    }

    /**
     * Creates a new vector of given range
     */
    public static Vector fromRange(double start, double end, double step) {
        int size = (int) Math.ceil((end - start) / step);
        Vector res = fromLength(size);

        for (int i = 0; i < size; i++)
            res.set(i, start + (i * step));
        
        return res;
    }

    /**
     * Creates a new vector of given range with step set to 1 by default
     */
    public static Vector fromRange(double start, double end) {
        return fromRange(start, end, 1);
    }

    /**
     * Creates a new vector from range with the {@code start=0} and {@code step=1}  
     */
    public static Vector fromRange(int end) {
        return fromRange(0, end, 1);
    }

    /**
     * Creates a new vector of {@code value}s of length {@code length}
     */
    public static Vector fromConstant(int length, double value) {
        Vector res = fromLength(length);
        res.setAll(value);
        return res;
    }
}