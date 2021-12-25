package org.la;

import java.util.Arrays;
import java.util.NoSuchElementException;

import org.la.factory.MatrixFactory;
import org.la.iterator.MatrixIterator;
import org.la.iterator.VectorIterator;

public class Matrix implements Iterable<Double> {
    private final double EPS = Math.pow(10, -6);

    private double[][] mat;
    private int rows;
    private int cols;

    public Matrix(String matrix) {
        mat = MatrixFactory.fromString(matrix);
        rows = mat.length;
        cols = mat[0].length;
    }

    public Matrix(double[][] matrix) {
        mat = MatrixFactory.fromArray(matrix);
    }

    public int rows() {
        return rows;
    }

    public int cols() {
        return cols;
    }

    public double get(int i, int j) {
        return mat[i][j];
    }

    public void set(int i, int j, double value) {
        mat[i][j] = value;
    }

    public void setRow(int index, int value) {
        VectorIterator it = rowIterator(index);

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    public void setRow(int index, Vector row) {
        if (cols != row.length())
            fail("Invalid row size");

        for (int i = 0; i < row.length(); i++)
            set(index, i, row.get(i));
    }

    public void setCol(int index, int value) {
        VectorIterator it = colIterator(index);

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    public void setCol(int index, Vector col) {
        if (rows != col.length())
            fail("Invalid column size");

        for (int i = 0; i < col.length(); i++) 
            set(i, index, col.get(i));
    }

    public double[][] toArray() {
        return MatrixFactory.fromArray(mat);
    }

    public boolean isSquare() {
        return cols() == rows();
    }

    public Vector getRow(int i) {
        return new Vector(mat[i]);
    }

    public Vector getCol(int j) {
        // TODO VectorFactory
        VectorIterator it = colIterator(j);
        Vector v = Vector.blank(cols);

        while (it.hasNext()) {
            double x = it.next();
            int i  = it.index();
            v.set(i, x);
        }
        return v;
    }

    public Matrix negate() {
        return mul(-1);
    }

    public Matrix blank() {
        return blankOfSize(rows, cols);
    }

    public static Matrix blankOfSize(int rows, int cols) {
        return new Matrix(new double[rows][cols]);
    }

    public static Matrix eye(int n) {
        return MatrixFactory.identity(n);
    }

    public Matrix add(double a) {
        MatrixIterator it = iterator();
        Matrix res = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.colIndex();
            res.set(i, j, x + a);
        }
        return res;
    }

    public Matrix add(Matrix m) {
        checkSizes(m);

        MatrixIterator it = iterator();
        Matrix res = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.colIndex();
            res.set(i, j, x + m.get(i, j));
        }
        return res;
    }

    public Matrix sub(double a) {
        return add(-a);
    }

    public Matrix sub(Matrix m) {
        return add(m.negate());
    }

    public Matrix mul(double a) {
        MatrixIterator it = iterator();
        Matrix res = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.colIndex();
            res.set(i, j, x * a);
        }
        return res;
    }

    public Matrix mul(Matrix m) {
        int rows = m.rows();
        int cols = m.cols();

        if (this.cols != rows) {
            fail("Matrices have different sizes");
        }

        // TODO iterator?
        double[][] mult = new double[this.rows][cols];

        for (int k = 0; k < this.cols; k++) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < cols; j++) {
                    mult[i][j] += get(i, k) * m.get(k, j);
                }
            }
        }
        return new Matrix(mult);
    } 

    public Matrix div(double a) {
        return mul(1.0/a);
    }

    public Matrix transpose() {
        MatrixIterator it = iterator();
        Matrix res = blankOfSize(cols, rows);

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.colIndex();
            res.set(j, i, x);
        }
        return res;
    }

    public Matrix subMatrix(int i, int j) {
        return removeRow(i).removeCol(j);
    }

    public double det() {
        if (rows == 1) return get(0, 0);
        if (rows == 2) 
            return get(0,0) * get(1,1) - get(0,1)*get(1,0);

        double det = 0;

        for (int i = 0; i < cols; i++) {
            det += (Math.pow(-1, i) * get(0, i) * subMatrix(0, i).det());
        }
        return det;
    }

    public Matrix cofactor() {
        if (!isSquare())
            fail("Cannot compute cofactor of non-square matrix");

        double[][] cof = new double[rows][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                cof[i][j] = Math.pow(-1, i + j) * subMatrix(i, j).det();
            }
        }
        return new Matrix(cof);
    }

    public Matrix inverse() {
        return cofactor().transpose().mul(1.0/det());
    }

    public Matrix removeRow(int i) {
        if (i < 0 || i >= rows)
            throw new IndexOutOfBoundsException("Invalid row index");

        Matrix res = blankOfSize(rows - 1, cols);

        for (int j = 0; j < i; j++) {
            res.setRow(j, getRow(j));
        }

        for (int j = i + 1; j < rows; j++) {
            res.setRow(j - 1, getRow(j));
        }
        return res;
    }

    public Matrix removeCol(int j) {
        if (j < 0 || j >= cols)
            throw new IndexOutOfBoundsException("Invalid column index");

        Matrix res = blankOfSize(rows, cols - 1);

        for (int i = 0; i < j; i++) {
            res.setCol(i, getCol(i));
        }
        
        for (int i = j + 1; i < cols; i++) {
            res.setCol(i - 1, getCol(i));
        }
        return res;
    }

    public MatrixIterator iterator() {
        return new MatrixIterator() {
            private long limit = (long) rows * cols;
            private int  index = -1;

            @Override
            public int rowIndex() {
                return index / cols;
            }

            @Override 
            public int colIndex() {
                return index - rowIndex() * cols;
            }

            @Override
            public double get() {
                return Matrix.this.get(rowIndex(), colIndex());
            }

            @Override
            public void set(double value) {
                Matrix.this.set(rowIndex(), colIndex(), value);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < limit;
            }

            @Override
            public Double next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                index++;
                return get();
            }
        };
    }

    public VectorIterator rowIterator(int row) {
        return new VectorIterator() {
            private int index = -1;

            @Override
            public int index() {
                return index;
            }

            @Override
            public double get() {
                return Matrix.this.get(row, index);
            }

            @Override
            public void set(double value) {
                Matrix.this.set(row, index, value);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < rows;
            }

            @Override
            public Double next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                index++;
                return get();
            }
        };
    }

    public VectorIterator colIterator(int col) {
        return new VectorIterator() {
            private int index = -1;

            @Override
            public int index() {
                return index;
            }

            @Override
            public double get() {
                return Matrix.this.get(index, col);
            }

            @Override
            public void set(double value) {
                Matrix.this.set(index, col, value);
            }

            @Override
            public boolean hasNext() {
                return index + 1 < cols;
            }

            @Override
            public Double next() {
                if (!hasNext())
                    throw new NoSuchElementException();

                index++;
                return get();
            }
        };
    } 

    private void checkSizes(Matrix m) {
        if (rows != m.rows() || cols != m.cols())
            fail("Matrices have different sizes");
    }

    private void fail(String message) {
        throw new IllegalArgumentException(message);
    }

    @Override
    public String toString() {
        // TODO:
        return Arrays.deepToString(mat);
    }

    @Override
    public Matrix clone() {
        return new Matrix(mat);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Matrix)
            return equals((Matrix) obj, EPS);
        return false;
    }

    public boolean equals(Matrix m, double tolerance) {
        if (this == m)
            return true;

        if (rows != m.rows() || cols != m.cols())
            return false;
        
        boolean res = true;    
        
        for (int i = 0; i < rows && res; i++) {
            Vector v = getRow(i);
            Vector u = m.getRow(i);
            res = v.equals(u);
        }
        return res;
    }
}