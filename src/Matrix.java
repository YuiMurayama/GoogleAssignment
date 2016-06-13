import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Matrix {
	public static void main(String args[]) {
		try {
			FileWriter fw = new FileWriter("test3.csv", false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
			for (int n = 450; n < 451; n++) {
				int countNum = 10;
				double[][] a = new double[n][n]; // Matrix A
				double[][] b = new double[n][n]; // Matrix B
				double[][] c = new double[n][n]; // Matrix C
				// Initialize the matrices to some values.
				int i, j;
				for (i = 0; i < n; i++) {
					for (j = 0; j < n; j++) {
						a[i][j] = i * n + j;
						b[i][j] = j * n + i;
						c[i][j] = 0;
					}
				}
				for (int count = 0; count < countNum; count++) {
					long begin = System.currentTimeMillis();

					for (i = 0; i < n; i++) {
						for (j = 0; j < n; j++) {
							for (int k = 0; k < n; k++) {
								c[i][j] += a[i][k] * b[k][j];
							}
						}

					}
					long end = System.currentTimeMillis();
					double time = (end - begin) / 1000.0 ;
					System.out.printf("time: %.6f sec\n", time);
				}

				// Print C for debugging. Comment out the print before measuring
				// the
				// execution time.
				double sum = 0;
				for (i = 0; i < n; i++) {
					for (j = 0; j < n; j++) {
						sum += c[i][j];
						// System.out.printf("c[%d][%d]=%f\n", i, j, c[i][j]);
					}
				}
				// Print out the sum of all values in C.
				// This should be 450 for N=3, 3680 for N=4, and 18250 for N=5.
				System.out.printf("sum: %.6f\n", sum);
				System.out.println(n);
				pw.print(n);
				pw.print(",");
//				pw.print(time);
				// pw.print(",");
				// pw.print((n^3)/1000000);
				pw.println();
			}

			pw.close();

		} catch (IOException e) {
			// TODO 自動生成された catch ブロック

			e.printStackTrace();
		} // ※１
	}

	static void printMatrix(double x[][]) {
		for (int i = 0; i < x.length; i++) {
			for (int a = 0; a < x[0].length; a++) {
				System.out.print(x[i][a] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
}