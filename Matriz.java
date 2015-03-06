/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package conexionredtrabajo1;

import java.text.DecimalFormat;

/**
 *
 * @author Ruben
 */
public class Matriz {
    /* ------------------------
    Class variables
     * ------------------------ */

    /** Array for internal storage of elements.
    @serial internal array storage.
     */
    private double[][] A;
    /** Row and column dimensions.
    @serial row dimension.
    @serial column dimension.
     */
    private int m, n;

    public Matriz(double[][] A) {
        m = A.length;
        n = A[0].length;
//      for (int i = 0; i < m; i++) {
//         if (A[i].length != n) {
//            throw new IllegalArgumentException("All rows must have the same length.");
//         }
//      }
        this.A = A;
    }

    public Matriz(double vector[]) {
        m = 1;
        n = vector.length;

        A = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = vector[i + j * m];
            }
        }
    }

    /** Construct an m-by-n Matriz of zeros.
    @param m    Number of rows.
    @param n    Number of colums.
     */
    public Matriz(int m, int n) {
        this.m = m;
        this.n = n;
        A = new double[m][n];
    }

    public Matriz(int n) {
        this.m = 1;
        this.n = n;
        A = new double[m][n];
    }

    /** Get row dimension.
    @return     m, the number of rows.
     */
    public int getDimensionFilas() {
        return m;
    }

    /** Get column dimension.
    @return     n, the number of columns.
     */
    public int getDimensionColumnas() {
        return n;
    }

    public double[][] getArray() {
        return A;
    }

    /** Copy the internal two-dimensional array.
    @return     Two-dimensional array copy of Matriz elements.
     */
    public double[][] getArrayCopia() {
        double[][] C = new double[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j];
            }
        }
        return C;
    }

    /** Get a submatrix.
    @param i0   Initial row index
    @param i1   Final row index
    @param j0   Initial column index
    @param j1   Final column index
    @return     A(i0:i1,j0:j1)
    @exception  ArrayIndexOutOfBoundsException Submatrix indices
     */
    public Matriz getMatriz(int i0, int i1, int j0, int j1) {
        Matriz X = new Matriz(i1 - i0 + 1, j1 - j0 + 1);
        double[][] B = X.getArray();

        for (int i = i0; i <= i1; i++) {
            for (int j = j0; j <= j1; j++) {
                B[i - i0][j - j0] = A[i][j];
            }
        }
        return X;
    }

    public Matriz getDia(int i0) {
        Matriz X = new Matriz(1, n);
        double[][] B = X.getArray();

        for (int i = i0; i <= i0; i++) {
            for (int j = 0; j <= n - 1; j++) {
                B[i - i0][j] = A[i][j];
            }
        }
        return X;
    }

    public double get(int i, int j) {
        return A[i][j];
    }

    public double get(int i) {
        return A[0][i];
    }

    /** Set a single element.
    @param i    Row index.
    @param j    Column index.
    @param s    A(i,j).
    @exception  ArrayIndexOutOfBoundsException
     */
    public void set(int i, int j, double s) {
        A[i][j] = s;
    }

    public void set(int i, double s) {
        A[0][i] = s;
    }

    /** Set a submatrix.
    @param i0   Initial row index
    @param i1   Final row index
    @param j0   Initial column index
    @param j1   Final column index
    @param X    A(i0:i1,j0:j1)
    @exception  ArrayIndexOutOfBoundsException Submatrix indices
     */
    public void setMatriz(int i0, int i1, int j0, int j1, Matriz X) {

        for (int i = i0; i <= i1; i++) {
            for (int j = j0; j <= j1; j++) {
                A[i][j] = X.get(i - i0, j - j0);
            }
        }
    }

    public void setDia(int i0, Matriz X) {

        for (int i = i0; i <= i0; i++) {
            for (int j = 0; j <= n - 1; j++) {
                A[i][j] = X.get(i - i0, j);
            }
        }
    }

    /** C = A + B
    @param B    another Matriz
    @return     A + B
     */
    public Matriz mas(Matriz B) {
//      checkMatrizDimensions(B);
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] + B.A[i][j];
            }
        }
        return X;
    }

    public Matriz mas(double s) {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = s + A[i][j];
            }
        }
        return X;
    }

    /** C = A - B
    @param B    another Matriz
    @return     A - B
     */
    public Matriz menos(Matriz B) {
//      checkMatrizDimensions(B);
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - B.A[i][j];
            }
        }
        return X;
    }

    public Matriz menos(double s) {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] - s;
            }
        }
        return X;
    }

    /** Element-by-element multiplication, C = A.*B
    @param B    another Matriz
    @return     A.*B
     */
    public Matriz por(Matriz B) {
//      checkMatrizDimensions(B);
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] * B.A[i][j];
            }
        }
        return X;
    }

    /** Multiply a Matriz by a scalar, C = s*A
    @param s    scalar
    @return     s*A
     */
    public Matriz por(double s) {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = s * A[i][j];
            }
        }
        return X;
    }

    public Matriz divide(Matriz B) {
//      checkMatrizDimensions(B);
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] / B.A[i][j];
            }
        }
        return X;
    }

    /** Multiply a Matriz by a scalar, C = s*A
    @param s    scalar
    @return     s*A
     */
    public Matriz divide(double s) {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = A[i][j] / s;
            }
        }
        return X;
    }

    public static Matriz seno(Matriz B) {
        Matriz X = new Matriz(B.m, B.n);
        double[][] C = X.getArray();
        for (int i = 0; i < B.m; i++) {
            for (int j = 0; j < B.n; j++) {
                C[i][j] = Math.sin(B.A[i][j]);
            }
        }
        return X;
    }

    public static Matriz aseno(Matriz B) {
        Matriz X = new Matriz(B.m, B.n);
        double[][] C = X.getArray();
        for (int i = 0; i < B.m; i++) {
            for (int j = 0; j < B.n; j++) {
                C[i][j] = Math.asin(B.A[i][j]);
            }
        }
        return X;
    }

    public static Matriz coseno(Matriz B) {
        Matriz X = new Matriz(B.m, B.n);
        double[][] C = X.getArray();
        for (int i = 0; i < B.m; i++) {
            for (int j = 0; j < B.n; j++) {
                C[i][j] = Math.cos(B.A[i][j]);
            }
        }
        return X;
    }

    public static Matriz acoseno(Matriz B) {
        Matriz X = new Matriz(B.m, B.n);
        double[][] C = X.getArray();
        for (int i = 0; i < B.m; i++) {
            for (int j = 0; j < B.n; j++) {
                C[i][j] = Math.acos(B.A[i][j]);
            }
        }
        return X;
    }

    public static Matriz tangente(Matriz B) {
        Matriz X = new Matriz(B.m, B.n);
        double[][] C = X.getArray();
        for (int i = 0; i < B.m; i++) {
            for (int j = 0; j < B.n; j++) {
                C[i][j] = Math.tan(B.A[i][j]);
            }
        }
        return X;
    }

    public Matriz grado_Radian() {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = Math.toRadians(A[i][j]);
            }
        }
        return X;
    }
    public Matriz radian_Grado() {
        Matriz X = new Matriz(m, n);
        double[][] C = X.getArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                C[i][j] = Math.toDegrees(A[i][j]);
            }
        }
        return X;
    }
    public double sumaHoras(int m0) {
        double d = 0;

        for (int i = 0; i < n; i++) {
            d = d + A[m0][i];
        }
        return d;
    }

    @Override
    public String toString() {
        String texto = "Valores: ";
        DecimalFormat formateador = new DecimalFormat("###.###");

        for (int i = 0; i < m; i++) {
            texto += "\n";
            for (int j = 0; j < n; j++) {
                texto += " " + formateador.format(A[i][j]);
            }
        }

        return texto;
    }
}
