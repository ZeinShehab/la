package org.la;

public class ArrayUtils {
    public static void deepCopy(double[][] src, double[][] dest) {
        for (int i = 0; i < dest.length; i++) {
            copy(src[i], dest[i]);
        }
    }
    
    public static void copy(double[] src, double[] dest) {
        copy(src, 0, dest, 0);
    }
    
    public static void copy(double[] src, int srcPos, double[] dest, int destPos) {
        int length = (int) Math.min(dest.length - destPos, src.length - srcPos);
        System.arraycopy(src, srcPos, dest, destPos, length);
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

    public static void shl(double[] arr) {
        shl(arr, 0);
    }

    public static void shr(double[] arr, double val) {
        for (int i = arr.length - 1; i > 0; i++) {
            arr[i] = arr[i-1];
        }
        arr[0] = val;
    }

    public static void shr(double[] arr) {
        shr(arr, 0);
    }


    public static double[] join(double[] a, double[] b) {
        double[] res = new double[a.length + b.length];
        copy(a, 0, res, 0);
        copy(b, 0, res, a.length);
        return res;
    }

    public static double[] expand(double[] src, int expansion) {
        double[] res = new double[src.length + expansion];
        copy(src, res);
        return res;
    }

    public static double[] shrink(double[] src, int shrink) {
        double[] res = new double[src.length - shrink];
        copy(src, res);
        return res;
    }

    public static double[] remove(double[] src, int index) {
        double[] res = new double[src.length - 1];
        for (int i = 0; i < res.length; i++) 
            res[i] = src[((i < index) ? i : i+1)];
        return res;
    }

    public static double[] insert(double[] src, int index, double ... values) {
        double[] res = new double[src.length + values.length];

        for (int i = 0; i < index; i++)
            res[i] = src[i];

        for (int i = 0; i < values.length; i++)
            res[i + index] = values[i];

        for (int i = index + values.length; i < res.length; i++)
            res[i] = src[i - values.length];

        return res;
    }
}
