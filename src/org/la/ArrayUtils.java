package org.la;
public class ArrayUtils {
    public static void deepCopy(double[][] src, double[][] dest) {
        for (int i = 0; i < dest.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, dest.length);
        }
    }

    public static void copy(double[] src, double[] dest) {
        System.arraycopy(src, 0, dest, 0, dest.length);
    }

    public static double[][] deepCopyOf(double[][] src) {
        double[][] res = new double[src.length][src[0].length];
        deepCopy(src, res);
        return res;
    }

    public static double[] copyOf(double[] src) {
        double[] res = new double[src.length];
        copy(src, res);
        return res;
    }
}
