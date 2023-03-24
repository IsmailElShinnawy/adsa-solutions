package Lab5;

import java.io.*;
import java.util.*;

public class FillTheContainers {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    double lo = 0, hi = 1e9;
    int n = sc.nextInt(), f = sc.nextInt();
    int[] a = new int[n];
    for(int i = 0; i < n; ++i) {
        a[i] = sc.nextInt();
    }

    double res = 0;
    while(lo <= hi) {
        double mid = lo + (hi - lo) / 2;
        if(f(mid, a, f)) {
            if(Math.abs(mid - res) <= 1e-6) {
                res = mid;
                break;
            }
            res = mid;
            hi = mid;
        } else {
            lo = mid;
        }
    }

    pw.println(res);
    pw.flush();

  }

  static boolean f(double time, int a[], int f) {
    double acc = 0;
    for(int i = 0; i < a.length; ++i) {
        double w = f * time + acc;
        if(w < a[i]) {
            return false;
        }
        acc = w - a[i];
    }
    return true;
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