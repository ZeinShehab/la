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

    public static void shl(double[] arr, double val) {
        for (int i = 1; i < arr.length; i++) {
            arr[i-1] = arr[i];
        }
        arr[arr.length-1] = val;
    }

    public static void shr(double[] arr, double val) {
        for (int i = arr.length - 1; i > 0; i++) {
            arr[i] = arr[i-1];
        }
        arr[0] = val;
    }

    public static void copyElements(double[] src, double[] dest) {
        for (int i = 0; i < dest.length && i < src.length; i++) {
            dest[i] = src[i];
        }
    }

    public static double[] expand(double[] src, int expansion) {
        double[] res = new double[src.length + expansion];
        copyElements(src, res);
        return res;
    }

    public static double[] shrink(double[] src, int shrink) {
        double[] res = new double[src.length - shrink];
        copyElements(src, res);
        return res;
    }

    public static double[] remove(double[] src, int index) {
        double[] res = new double[src.length - 1];
        for (int i = 0; i < index; i++) 
            res[i] = src[i];

        for (int i = index; i < res.length; i++)
            res[i] = src[i+1];

        return res;
    }

    public static double[] insert(double[] src, double value, int index) {
        double[] res = new double[src.length + 1];

        for (int i = 0; i < index; i++)
            res[i] = src[i];

        res[index] = value;

        for (int i = index + 1; i < res.length; i++)
            res[i] = src[i-1];

        return res;
    }
}
