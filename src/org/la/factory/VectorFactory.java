package org.la.factory;

import org.la.Linear;

// TODO return Vector??
public class VectorFactory {
    public static double[] fromArray(double[] arr) {
        double[] res = new double[arr.length];
        System.arraycopy(arr, 0, res, 0, arr.length);
        return res;
    }

    public static double[] fromString(String vec) {
        vec = vec.replaceAll("\\[|\\]", "");
        String[] vals = vec.split(Linear.COL_DELIMITER);
        double[] res = new double[vals.length];

        for (int i = 0; i < vals.length; i++) {
            res[i] = Double.parseDouble(vals[i]);
        }
        return res;
    }
    
    public static double[] fromLength(int length) {
        return new double[length];
    }

    // TODO: potentially introduce automatic step detection like haskell
    // accept input of the form [1,2..20] or [1,3..20]
    public static double[] fromRange(double start, double end, double step) {
        int size = (int) Math.ceil((end - start) / step);
        double[] res = new double[size];

        for (int i = 0; i < size; i++)
            res[i] = start + (i * step);
        
        return res;
    }

    public static double[] fromRange(double start, double end) {
        return fromRange(start, end, 1);
    }

    public static double[] fromRange(int end) {
        return fromRange(0, end, 1);
    }
}