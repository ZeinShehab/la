package org.la.factory;

import org.la.Linear;

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
}