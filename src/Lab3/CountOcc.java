package Lab3;

import java.util.*;
import java.io.*;

public class CountOcc {

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int N = sc.nextInt(), M = sc.nextInt();
    String text = sc.next(), pattern = sc.next();

    pw.println(KMP.kmp(text, pattern));

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

  static class KMP {
    public static int kmp(String text, String pattern) {
      // ArrayList<Integer> res = new ArrayList<>();
      int res = 0;
      int pi[] = prefixFunction(pattern);
      int N = text.length(), M = pattern.length();
      int i = 0, j = 0;
      while (i < N) {
        if (text.charAt(i) == pattern.charAt(j)) {
          i++;
          j++;
          if (j == M) {
            // res.add(i - M);
            res++;
            j = pi[j - 1];
          }
        } else {
          if (j == 0) {
            i++;
          } else {
            j = pi[j - 1];
          }
        }
      }
      return res;
    }

    public static int[] prefixFunction(String input) {
      int N = input.length();
      int pi[] = new int[N];

      pi[0] = 0;
      int i = 1;
      int len = 0;

      while (i < N) {
        if (input.charAt(i) == input.charAt(len)) {
          pi[i++] = ++len;
        } else {
          if (len == 0) {
            pi[i++] = 0;
          } else {
            len = pi[len - 1];
          }
        }
      }

      return pi;
    }
  }
}
