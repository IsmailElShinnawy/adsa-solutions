package Lab5;

import java.io.*;
import java.util.*;

public class SharingIsCaring {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    
    pw.println(bs(sc.nextLong()));
    pw.flush();

  }

  static long bs(long n) {
    long lo = 1, hi = (long) 1e18;
    long res = 0;
    while(lo <= hi) {
        long mid = lo + (hi - lo) / 2;
        if(f(n, mid)) {
            res = mid;
            hi = mid - 1;
        } else {
            lo = mid + 1;
        }
    }
    return res;
  }
  
  static boolean f(long n, long k) {
    long x = 0, tmp = n;
    while(true) {
        if(tmp - k <= 0) {
          x += tmp;
          break;
        }
        x += k;
        tmp -= k;
        tmp -= tmp / 10;
    }
    long target = n % 2 == 0 ? n / 2 : n / 2 + 1;
    return x >= target;
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