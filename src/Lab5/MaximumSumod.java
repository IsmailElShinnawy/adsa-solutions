package Lab5;
 
import java.io.*;
import java.util.*;
 
public class MaximumSumod {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);
 
    // Implement your solution here
    int n = sc.nextInt(), m = sc.nextInt();
    int a[] = new int[n];
    for(int i = 0; i < n; ++i) {
        a[i] = sc.nextInt();
    }
    TreeSet<Integer> ts1 = new TreeSet<>();
    TreeSet<Integer> ts2 = new TreeSet<>();
    populate(0, n / 2, ts1, a, m);
    populate(n / 2, n, ts2, a, m);
    ts1.add(0);
    ts2.add(0);
    
    int max = 0;
    for(Integer x: ts1) {
        Integer y = ts2.floor(m - 1 - x);
        if(y != null) {
            max = Math.max(max, (x + y) % m);
        }
    }
 
    pw.println(max);
    pw.flush();
  }
 
  static void populate(int l, int r, TreeSet<Integer> ts, int arr[], int m) {
    for(int i = 0; i < (1 << (r - l)); ++i) {
        int sum = 0;
        for(int j = 0; j < r - l; ++j) {
            if((i & (1 << j)) > 0) {
                sum += arr[l + j];
                sum %= m;
            }
        }
        ts.add(sum % m);
    }
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