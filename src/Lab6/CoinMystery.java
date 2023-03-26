package Lab6;

import java.io.*;
import java.util.*;

public class CoinMystery {

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
    long d = sc.nextLong();
    int m = sc.nextInt(), n = sc.nextInt();

    // if(d==1) {
    //     System.out.println(1);
    //     return;
    // }
    // if(d==1) {
    //     System.out.println(m);
    //     return;
    // }

    int mod = (int) 1e9 + 7;

    Matrix base = new Matrix(2, 2);
    // 0 -n
    // 1  m
    /*
     * 1 m 0 -n
     *     1  m
     * 
     * m 
     */
    base.set(0, 0, 0, mod);
    base.set(0, 1, -n, mod);
    base.set(1, 0, 1, mod);
    base.set(1, 1, m, mod);

    Matrix y = new Matrix(2, 2);
    y.set(0, 0, 1, mod);
    y.set(0, 1, 0, mod);
    y.set(1, 0, 0, mod);
    y.set(1, 1, 1, mod);

    d--;

    while(d > 0) {
        if((d & 1L) == 1L) {
            y = y.multiply(base, mod);
        }
        base = base.multiply(base, mod);
        d >>= 1;
    }

    Matrix x = new Matrix(1, 2);
    
    x.set(0, 0, 1, mod);
    x.set(0, 1, m, mod);

    Matrix result = x.multiply(y, mod);

    pw.println((result.arr[0][0] + mod) % mod);
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