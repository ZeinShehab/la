package org.la;

import java.util.NoSuchElementException;

import org.la.factory.MatrixFactory;
import org.la.iterator.MatrixIterator;
import org.la.iterator.VectorIterator;

/**
 * A matrix is represented by a 2D array of real numbers. It has a fixed size
 */
public class Matrix implements Iterable<Double> {
    private double[][] mat;
    private int rows;
    private int cols;

    /**
     * @see {@link #Matrix(double[][], boolean)}
     */
    public Matrix(double[][] arr) {
        this(arr, true);
    }

    /**
     * Creates a new matrix from given array {@code arr}. If {@code copy} is set to true it copies the array
     * otherwise it points the internal matrix array to the same array. It is true by default
     */
    public Matrix(double[][] arr, boolean copy) {
        if (!copy)
            this.mat = arr;
        else
            mat = ArrayUtils.deepCopyOf(arr);
        this.rows = arr.length;
        this.cols = arr[0].length;
    }
    /**
     * Creates a new matrix from given string {@code matString}
     * It uses strings of the format {@code [1 2 3; 4 5 6; 7 8 9]}
     * where spaces represent the columns and semicolons represent the rows
     */
    public static Matrix fromString(String matString) {
        return MatrixFactory.fromString(matString);
    }

    /**
     * Creates a new matrix from given array {@code arr} while copying the given array
     */
    public static Matrix fromArray(double[][] arr) {
        return MatrixFactory.fromArray(arr);
    } 

    /**
     * Creates a new matrix of {@code value}s of size {@code rows x cols}
     */
    public static Matrix fromConstant(int rows, int cols, double value) {
        return MatrixFactory.fromConstant(rows, cols, value);
    }

    /**
     * Creates a new matrix of {@code value}s of size {@code size x size}
     */
    public static Matrix fromConstant(int size, double value) {
        return MatrixFactory.fromConstant(size, value);
    }

    /**
     * Creates a new matrix of given size {@code rows x cols}
     */
    public static Matrix fromSize(int rows, int cols) {
        return MatrixFactory.fromSize(rows, cols);
    }

    /**
     * Creates a new matrix of given size {@code size x size}
     */
    public static Matrix fromSize(int size) {
        return MatrixFactory.fromSize(size);
    }

    /**
     * Creates a new matrix of given vector {@code v} by stacking them 
     * horizontally on top of each other
     */
    public static Matrix fromVectorsHorizontal(Vector ... v) {
        return MatrixFactory.fromVectorsHorizontal(v);
    }

    /**
     * Creates a new matrix of given vectors {@code v} by laying them out
     * vertically side by side.
     */
    public static Matrix  fromVectorsVertical(Vector ... v) {
        return MatrixFactory.fromVectorsVertical(v);
    }

    /**
     * Returns the number of rows of the matrix
     */
    public int rows() {
        return rows;
    }

    /**
     * Returns the number of columns of the matrix
     */
    public int cols() {
        return cols;
    }

    /**
     * Returns value at row {@code i} and col {@code j}
     */
    public double get(int i, int j) {
        return mat[i][j];
    }

    /**
     * Sets the value at row {@code i} and col {@code j} to {@code value}
     */
    public void set(int i, int j, double value) {
        mat[i][j] = value;
    }

    /**
     * Sets all values at {@code row} to {@code value}
     */
    public void setRow(int row, int value) {
        VectorIterator it = rowIterator(row);

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    /**
     * Sets the values at {@code row} to to the values of {@code v}
     */
    public void setRow(int row, Vector v) {
        if (cols != v.length())
            fail("Invalid row length");

        for (int i = 0; i < v.length(); i++)
            set(row, i, v.get(i));
    }

    /**
     * Sets all values at {@code col} to {@code value}
     */
    public void setCol(int col, int value) {
        VectorIterator it = colIterator(col);

        while (it.hasNext()) {
            it.next();
            it.set(value);
        }
    }

    /**
     * Sets the values at {@code col} to to the values of {@code v}
     */
    public void setCol(int col, Vector v) {
        if (rows != v.length())
            fail("Invalid column length");

        for (int i = 0; i < v.length(); i++) 
            set(i, col, v.get(i));
    }

    /**
     * Converts instance matrix to an array
     */
    public double[][] toArray() {
        return ArrayUtils.deepCopyOf(mat);
    }

    /**
     * Checks if instance matrix is a square matrix
     */
    public boolean isSquare() {
        return rows == cols;
    }

    /**
     * Checks if the instance matrix is empty
     */
    public boolean isEmpty() {
        return (rows == 0) || (cols == 0);
    }

    /**
     * Returns a {@code Vector} of elements of at row {@code i}
     */
    public Vector getRow(int i) {
        return new Vector(mat[i]);
    }

    /**
     * Returns a {@code Vector} of elements of at col {@code j}
     */
    public Vector getCol(int j) {
        VectorIterator it = colIterator(j);
        Vector v = Vector.blankOfLength(rows);

        while (it.hasNext()) {
            double x = it.next();
            int i  = it.index();
            v.set(i, x);
        }
        return v;
    }

    /**
     * Returns negation of instance matrix {@code m}
     * @return -m
     * @see {@link #mul(double)}
     */
    public Matrix negate() {
        return mul(-1);
    }

    /**
     * Creates a new matrix of instance size initalized to all zeros
     * @return blank matrix
     * @see {@link #blankOfSize(int, int)}
     */
    public Matrix blank() {
        return blankOfSize(rows, cols);
    }

    /**
     * Creates a new matrix of size {@code rows x cols} initalized to all zeros
     * @return blank matrix
     * @see {@link #fromSize(int)}
     */
    public static Matrix blankOfSize(int rows, int cols) {
        return fromSize(rows, cols);
    }

    /**
     * Creates an identitiy matrix of size {@code n x n}
     */
    public static Matrix eye(int n) {
        return MatrixFactory.identity(n);
    }

    /**
     * Returns a new matrix with {@code a} added to all elements of instance {@code m}
     * @param a a value
     * @return m + a
     */
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

    /**
     * Returns the sum of instance matrix {@code M} and {@code A}
     * @param A a matrix
     * @return M + A
     */
    public Matrix add(Matrix A) {
        checkSizes(A);

        MatrixIterator it = iterator();
        Matrix res = blank();

        while (it.hasNext()) {
            double x = it.next();
            int i = it.rowIndex();
            int j = it.colIndex();
            res.set(i, j, x + A.get(i, j));
        }
        return res;
    }

    /**
     * Returns new matrix with {@code a} subtracted from all elements of instance {@code M}
     * @param a a value
     * @return M - a
     * @see {@link #add(double)}
     */
    public Matrix sub(double a) {
        return add(-a);
    }

    /**
     * Returns difference of instance matrix {@code M} and {@code A}
     * @param A a matrix
     * @return M - A
     * @see {@link #add(Matrix)}
     * @see {@link #negate()}
     */
    public Matrix sub(Matrix A) {
        return add(A.negate());
    }

    /**
     * Returns a new matrix of instance {@code M} multiplied by {@code a}
     * @param a a value
     * @return M x a
     */
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

    /**
     * Returns a new vector of {@code v} multiplied/transformed by instance matrix {@code M} 
     * @param v a vector
     * @return M * v
     */
    public Vector mul(Vector v) {
        Vector res = Vector.blankOfLength(rows);
        VectorIterator it = res.iterator();

        if (!isSquare())
            // TODO non square matrices?
            throw new IllegalArgumentException("Linear transformation matrix must be square for now");

        while (it.hasNext()) {
            it.next();
            it.set(v.dot(getRow(it.index())));
        }
        return res;
    }

    /**
     * Returns a new matrix of the product of the instance {@code M} and {@code A}
     * @param A a matrix
     * @return M x A
     */
    public Matrix mul(Matrix A) {
        int rows = A.rows();
        int cols = A.cols();

        if (this.cols != rows) {
            fail("Matrices have different sizes");
        }

        double[][] mult = new double[this.rows][cols];

        for (int k = 0; k < this.cols; k++) {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < cols; j++) {
                    mult[i][j] += get(i, k) * A.get(k, j);
                }
            }
        }
        return fromArray(mult);
    } 

    /**
     * Returns a new matrix of instance {@code M} divided by {@code a}
     * @param a a non-zero value
     * @return M / a
     * @see {@link #mul(double)}
     */
    public Matrix div(double a) {
        return mul(1.0/a);
    }

    /**
     * Returns the transpose of the matrix
     */
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

    /**
     * Returns a sub matrix by removing row {@code i} and col {@code j}
     * @param i row index
     * @param j col index
     */
    public Matrix subMatrix(int i, int j) {
        return removeRow(i).removeCol(j);
    }

    /**
     * Calculates the determinant of the matrix
     */
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

    /**
     * Returns the cofactor of the matrix
     */
    public Matrix cofactor() {
        if (!isSquare())
            fail("Cannot compute cofactor of non-square matrix");

        Matrix res = fromSize(rows);
        MatrixIterator it = res.iterator();

        while (it.hasNext()) {
            it.next();
            int i = it.rowIndex();
            int j = it.colIndex();

            it.set(Math.pow(-1, i+j) * subMatrix(i, j).det());
        }
        return res;
    }

    /**
     * Returns the inverse of the matrix
     * @return
     */
    public Matrix inverse() {
        return cofactor().transpose().mul(1.0/det());
    }

    /**
     * Returns a new matrix without row {@code i} 
     * @param i row index
     */
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

    /**
     * Returns a new matrix without column {@code j}
     * @param j column index
     */
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

    /**
     * Creates a new matrix iterator 
     * @see org.la.iterator.MatrixIterator
     */
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

    /**
     * Creates a new vector iterator at {@code row}
     * @see org.la.iterator.VectorIterator
     */
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

    /**
     * Creates a new vector iterator at {@code col}
     * @see org.la.iterator.VectorIterator
     */
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

    /**
     * Verifies that the instance and {@code A} have the same sizes
     * before performing other operations
     */
    private void checkSizes(Matrix A) {
        if (rows != A.rows() || cols != A.cols())
            fail("Matrices have different sizes");
    }

    /**
     * Fails with an {@code IllegalArgumentException}
     * @param message error message
     */
    private void fail(String message) {
        throw new IllegalArgumentException(message);
    }

    /**
     * Returns conventional string representation of matrix
     */
    @Override
    public String toString() {
        String out = "[";
        String padding = !isEmpty() && get(0, 0) < 0 ? " " : "";

        String rowDelimeter = "\n";
        String colDelimeter = ",  ";
            
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = get(i, j);
                String fmt = String.format("%.3f", x);

                if (i != 0 && j == 0) out += padding;
                if (x >= 0 && i != 0 && j == 0) out += " ";
                
                if (j == 0) out += "[";

                out += fmt;
                if (j < cols - 1) {
                    if (get(i, j+1) >= 0)
                        out += colDelimeter;
                    else
                        out += colDelimeter.substring(0, colDelimeter.length()-1);
                }
            }
            out += "]";
            if (i < rows - 1)
                out += rowDelimeter;
        }
        return out + "]";
    }

    /**
     * Clones the matrix
     */
    @Override
    public Matrix clone() {
        return fromArray(mat);
    }

    /**
     * Checks if matrix is equal to {@code obj} with tolerance of {@code 1.0E-6}
     * @see org.la.Linear
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof Matrix)
            return equals((Matrix) obj, Linear.EPS);
        return false;
    }

    /**
     * Checks if matrix {@code M} equals {@code A} by given {@code tolerance}
     * @param A a matrix
     * @param tolerance precision used to calculate equality
     * @return M = A
     */
    public boolean equals(Matrix A, double tolerance) {
        if (this == A)
            return true;

        if (rows != A.rows() || cols != A.cols())
            return false;
        
        boolean res = true;    
        
        for (int i = 0; i < rows && res; i++) {
            Vector v = getRow(i);
            Vector u = A.getRow(i);
            res = v.equals(u, tolerance);
        }
        return res;
    }
}