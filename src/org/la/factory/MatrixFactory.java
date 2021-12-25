package org.la.factory;

import org.la.Matrix;

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
		matString = " " + matString.replace("[", "").replace("]", ""); // add space at [0] for consistency between rows

		String[] cols;
		String[] rows = matString.split(";");

		double[][] numM = new double[rows.length][];

		for (int i = 0; i < rows.length; i++) {
			cols = rows[i].substring(1).split(" "); // remove space and split each row into columns
			numM[i] = new double[cols.length]; // set size of columns according to each column

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

	public static Matrix identity(int size) {
		double[][] m = new double[size][size];
		for (int i = 0; i < size; i++) {
			m[i][i] = 1;
		}
		return new Matrix(m);
	}

	// TODO: from constant
}
