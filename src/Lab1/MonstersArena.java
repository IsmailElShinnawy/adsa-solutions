package Lab1;

import java.io.*;
import java.util.*;

public class MonstersArena {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int N = sc.nextInt(), q = sc.nextInt();
    long skills[] = new long[N + 1];
    int parent[] = new int[N + 1];
    for (int i = 1; i <= N; ++i) {
      parent[i] = i;
    }
    int rank[] = new int[N + 1];
    for (int i = 1; i <= N; ++i) {
      skills[i] = sc.nextInt();
    }

    while (q-- > 0) {
      int op = sc.nextInt();
      if (op == 1) {
        union(sc.nextInt(), sc.nextInt(), parent, rank, skills);
      } else {
        pw.println(skills[find(sc.nextInt(), parent)]);
      }
    }

    pw.flush();

  }

  static void union(int i, int j, int parent[], int rank[], long skills[]) {
    int pi = find(i, parent), pj = find(j, parent);
    if (pi == pj)
      return;
    if (rank[pi] > rank[pj]) {
      parent[pj] = pi;
      skills[pi] += skills[pj];
    } else if (rank[pi] < rank[pj]) {
      parent[pi] = pj;
      skills[pj] += skills[pi];
    } else {
      parent[pi] = pj;
      skills[pj] += skills[pi];
      ++rank[pj];
    }
  }

  static int find(int i, int parent[]) {
    if (parent[i] == i)
      return i;
    return parent[i] = find(parent[i], parent);
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