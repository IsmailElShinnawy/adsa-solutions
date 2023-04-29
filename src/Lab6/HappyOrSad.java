package Lab6;

import java.io.*;
import java.util.*;

public class HappyOrSad {

  static void multiply(double[][] x, double[][] y) {
    double temp[][] = new double[2][2];
    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < 2; ++j) {
        for (int k = 0; k < 2; ++k) {
          temp[i][j] += x[i][k] * y[k][j];
        }
      }
    }

    for (int i = 0; i < 2; ++i) {
      for (int j = 0; j < 2; ++j) {
        x[i][j] = temp[i][j];
      }
    }
  }

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int n = sc.nextInt();
    double p = sc.nextDouble();

    if (n == 0) {
      System.out.println(1);
      return;
    }

    double base[][] = { { 1 - 2 * p, 1 }, { 0, 1 } };

    double result[][] = { { 1, 0 }, { 0, 1 } };
    while (n > 0) {
      if ((n & 1) == 1) {
        multiply(result, base);
      }

      multiply(base, base);
      n >>= 1;
    }

    System.out.println(result[0][0] + result[0][1] * p);

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
