package org.la.factory;

import org.la.Matrix;
import org.la.Vector;
import org.la.Linear;


public class MatrixFactory {
	/**
	 * Creates a matrix from strings of the format {@code [1 2 3; 4 5 6; 7 8 9]}
	 * Similar to the MatLab syntax spaces represent columns and semicolons
	 * represent rows
	 * The square brackets {@code []} are optional
	 * 
	 * @param matString String following the above format
	 */
	public static Matrix fromString(String matString) {
		matString = " " + matString.replace("[", "").replace("]", "");

		String[] cols;
		String[] rows = matString.split(Linear.ROW_DELIMITER);

		int colLen = rows[0].substring(1).split(Linear.COL_DELIMITER).length;
		Matrix res = fromSize(rows.length, colLen);

		for (int i = 0; i < rows.length; i++) {
			cols = rows[i].substring(1).split(Linear.COL_DELIMITER);

			for (int j = 0; j < cols.length; j++)
				res.set(i, j, Double.parseDouble(cols[j]));
		}
		return res;
	}

	/**
     * Creates a new matrix of {@code value}s of size {@code rows x cols}
	 */
	public static Matrix fromConstant(int rows, int cols, double value) {
		Matrix res = fromSize(rows, cols);

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				res.set(i, j, value);
		return res;
	}

	/**
     * Creates a new matrix of {@code value}s of size {@code size x size}
	 */
	public static Matrix fromConstant(int size, double value) {
		return fromConstant(size, size, value);
	}

	/**
     * Creates a new matrix of given size {@code rows x cols}
	 */
	public static Matrix fromSize(int rows, int cols) {
		return new Matrix(new double[rows][cols], false);
	}

	/**
     * Creates a new matrix of given size {@code size x size}
	 */
	public static Matrix fromSize(int size) {
		return fromSize(size, size);
	}

	/**
     * Creates a new matrix of given vector {@code v} by stacking them 
     * horizontally on top of each other
     */
	public static Matrix fromVectorsHorizontal(Vector ... v) {
		if (v.length == 0 || v[0] == null)
			throw new IllegalArgumentException("Invalid input size");

		Matrix res = fromSize(v.length, v[0].length());

		for (int i = 0; i < v.length; i++)
			res.setRow(i, v[i]);
		return res;
	}

	/**
     * Creates a new matrix of given vectors {@code v} by laying them out
     * vertically side by side
     */
	public static Matrix fromVectorsVertical(Vector ... v) {
		if (v.length == 0 || v[0] == null)
			throw new IllegalArgumentException("Invalid input size");
		
		Matrix res = fromSize(v[0].length(), v.length);

		for (int i = 0; i < v[0].length(); i++)
			res.setCol(i, v[i]);
		return res;
	}

	/**
     * Creates an identitiy matrix of size {@code n x n}
     */
	public static Matrix identity(int size) {
		double[][] m = new double[size][size];
		for (int i = 0; i < size; i++) {
			m[i][i] = 1;
		}
		return new Matrix(m);
	}
}
