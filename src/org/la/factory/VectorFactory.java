package org.la.factory;

import org.la.Linear;
import org.la.Vector;

public class VectorFactory {
    public static Vector fromArray(double ... arr) {
        return new Vector(arr);
    }

    public static Vector fromString(String vec) {
        vec = vec.replaceAll("\\[|\\]", "");
        String[] vals = vec.split(Linear.COL_DELIMITER);
        Vector res = fromLength(vals.length);

        for (int i = 0; i < vals.length; i++) {
            res.set(i, Double.parseDouble(vals[i]));
        }
        return res;
    }
    
    public static Vector fromLength(int length) {
        return new Vector(new double[length], false);
    }

    public static Vector fromRange(double start, double end, double step) {
        int size = (int) Math.ceil((end - start) / step);
        Vector res = fromLength(size);

        for (int i = 0; i < size; i++)
            res.set(i, start + (i * step));
        
        return res;
    }

    public static Vector fromRange(double start, double end) {
        return fromRange(start, end, 1);
    }

    public static Vector fromRange(int end) {
        return fromRange(0, end, 1);
    }

    // public static Vector fromRange(String range) {
    //     Range r = new Range(range);    
    //     return fromRange(r);
    // }

    // public static Vector fromRange(Range r) {
    //     if (r.isInfinite()) 
    //         throw new IllegalArgumentException("Infinte size Vectors are not supported. Use BigVector instead");
        
    //         return fromRange(r.start(), r.end(), r.step());
    // }

    public static Vector fromConstant(int length, double value) {
        Vector res = fromLength(length);
        res.setAll(value);
        return res;
    }
}