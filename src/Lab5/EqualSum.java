package Lab5;

import java.io.*;
import java.util.*;

public class EqualSum {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int n = sc.nextInt();
    int[] arr = new int[n];
    while(n-- > 0) {
      arr[n] = sc.nextInt();
    }

    if(sol(arr)) {
      pw.println("SIU");
    } else {
      pw.println(":(");
    }

    pw.flush();

  }

  static boolean sol(int[] arr) {
    int n = arr.length;
    int mid = n / 2;
    int x = 0;
    for(int i = 0; i < mid; i++) {
      x += arr[i];
    }
    for(int i = mid; i < n; ++i) {
      x -= arr[i];
    }
    Set<Integer> firstHalfSet = new HashSet<>();
    for (int i = 0; i < (1 << mid); i++) {
        int sum = 0;
        for (int j = 0; j < mid; j++) {
            if ((i & (1 << j)) > 0) {
                sum += arr[j];
            }
        }
        firstHalfSet.add(sum);
    }
    for (int i = 0; i < (1 << (n - mid)); i++) {
        int sum = 0;
        for (int j = 0; j < (n - mid); j++) {
            if ((i & (1 << j)) > 0) {
                sum += arr[mid + j];
            }
        }
        // a + second half sum - b = b + first half sum - a
        int a = sum + x / 2;
        if(firstHalfSet.contains(a) && x % 2 == 0) {
          return true;
        }
    }
    return false;
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