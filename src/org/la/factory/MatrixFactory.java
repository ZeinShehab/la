package org.la.factory;

import org.la.Matrix;
import org.la.Vector;

// TODO return Matrix ??
public class MatrixFactory {
	/**
	 * Parses matrix from string of the format {@code [1 2 3; 4 5 6; 7 8 9]}
	 * Similar to the MatLab syntax spaces represent columns and semicolons
	 * represent rows
	 * The square brackets {@code []} are optional
	 * 
	 * @param matString String following the above format
	 * @return 2D array representation of string as matrix
	 */
	public static double[][] fromString(String matString) {
		matString = " " + matString.replace("[", "").replace("]", "");

		String[] cols;
		String[] rows = matString.split(";");

		double[][] numM = new double[rows.length][];

		for (int i = 0; i < rows.length; i++) {
			cols = rows[i].substring(1).split(" ");
			numM[i] = new double[cols.length]; 

			for (int j = 0; j < cols.length; j++)
				numM[i][j] = Double.parseDouble(cols[j]);
		}
		return numM;
	}

	public static double[][] fromArray(double[][] arr) {
		double[][] mat = new double[arr.length][];

		for (int i = 0; i < mat.length; i++) {
			int size = arr[i].length;
			mat[i] = new double[size];
			System.arraycopy(arr[i], 0, mat[i], 0, size);
		}
		return mat;
	}

	public static double[][] fromConstant(int rows, int cols, double value) {
		double[][] m = new double[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; i++)
				m[i][j] = value;	

		return m;
	}

	public static double[][] fromConstant(int size, double value) {
		return fromConstant(size, size, value);
	}

	public static double[][] fromSize(int rows, int cols) {
		return new double[rows][cols];
	}

	public static double[][] fromSize(int size) {
		return new double[size][size];
	}

	public static double[][] fromVectorsHorizontal(Vector ... v) {
		double[][] m = new double[v.length][];

		for (int i = 0; i < v.length; i++) {
			m[i] = v[i].toArray();
		}
		return m;
	}

	public static double[][] fromVectorsVertical(Vector ... v) {
		if (v.length == 0 || v[0] == null)
			throw new IllegalArgumentException("Invalid input size");
		
		double[][] m = new double[v[0].length()][v.length];

		for (int i = 0; i < v[0].length(); i++) {
			for (int j = 0; j < v.length; j++) {
				m[i][j] = v[j].get(i);
			}
		}
		return m;
	}

	public static Matrix identity(int size) {
		double[][] m = new double[size][size];
		for (int i = 0; i < size; i++) {
			m[i][i] = 1;
		}
		return new Matrix(m);
	}
}
