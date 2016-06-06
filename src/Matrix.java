import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class Matrix {
	public static void main(String args[]) {
		// if (args.length != 1) {
		// System.out.println(args.length);
		// System.out.println("usage: java Matrix N");
		// return;
		// }
		// int n = Integer.parseInt(args[4]);

		try {
			FileWriter fw = new FileWriter("f:\\test.csv", false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			for (int n = 1; n < 300; n++) {

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

				// System.out.println("Aは");
				// printMatrix(a);
				//
				// System.out.println("Bは");
				// printMatrix(b);

				long begin = System.currentTimeMillis();

				/**************************************/
				/* Write code to calculate C = A * B. */
				/**************************************/

				for (i = 0; i < n; i++) {
					for (j = 0; j < n; j++) {

						for (int k = 0; k < n; k++) {
							c[i][j] += a[i][k] * b[k][j];
						}
					}
				}

				// System.out.println("Cは");
				// printMatrix(c);

				long end = System.currentTimeMillis();
				double time = (end - begin) / 1000.0;

				System.out.printf("time: %.6f sec\n", time);

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
				pw.print(time);
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