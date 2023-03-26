package Lab6;

import java.io.*;
import java.util.*;

public class Fibonacci {

    static class Matrix {
        int[][] arr;

        public Matrix(int n, int m) {
            arr = new int[n][m];
        }

        public void set(int i, int j, int val, int mod) {
            arr[i][j] = val % mod;
        }

        public Matrix multiply(Matrix other, int mod) {
            Matrix result = new Matrix(arr.length, other.arr[0].length);
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < other.arr[0].length; j++) {
                    for (int k = 0; k < arr[0].length; k++) {
                        long temp = 1L * arr[i][k] % mod * other.arr[k][j] % mod;
                        result.arr[i][j] += temp % mod;
                        result.arr[i][j] %= mod;
                    }
                }
            }
            return result;
        }

        @Override
        public String toString() {
            return Arrays.deepToString(arr);
        }
    }
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    long n = sc.nextLong();
    if(n <= 1) {
        pw.println(n);
        pw.flush();
        return;
    }

    int mod = (int) 1e9 + 7;

    Matrix m = new Matrix(2, 2);
    m.set(0, 0, 0, mod);
    m.set(0, 1, 1, mod);
    m.set(1, 0, 1, mod);
    m.set(1, 1, 1, mod);

    /*
     * 0 1 0 1
     * 1 1 1 1
     * 
     * 1 1 1 1
     * 1 2 1 2
     * 
     * 2 3
     * 3 5
     * 
     * 0 1 2 3
     *     3 5
     * 
     * 3 5
     */

    Matrix y = new Matrix(2, 2);
    y.set(0, 0, 1, mod);
    y.set(0, 1, 0, mod);
    y.set(1, 0, 0, mod);
    y.set(1, 1, 1, mod);

    while(n > 0) {
        if((n & 1L) == 1L) {
            y = y.multiply(m, mod);
        }
        m = m.multiply(m, mod);
        n >>= 1;
    }

    Matrix x = new Matrix(1, 2);
    
    x.set(0, 0, 0, mod);
    x.set(0, 1, 1, mod);

    Matrix result = x.multiply(y, mod);

    pw.println(result.arr[0][0] % mod);
    pw.flush();

  }

  static class Scanner {
    StringTokenizer st;
    BufferedReader br;

    public Scanner(InputStream s) {
      br = new BufferedReader(new InputStreamReader(s));
    }

    public String next() throws IOException {
      while (st == null || !st.hasMoreTokens())
        st = new StringTokenizer(br.readLine());
      return st.nextToken();
    }

    public int nextInt() throws IOException {
      return Integer.parseInt(next());
    }

    public long nextLong() throws IOException {
      return Long.parseLong(next());
    }

    public String nextLine() throws IOException {
      return br.readLine();
    }

    public double nextDouble() throws IOException {
      return Double.parseDouble(next());
    }
  }
}