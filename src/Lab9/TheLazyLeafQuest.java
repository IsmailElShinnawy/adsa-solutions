package Lab9;

import java.io.*;
import java.util.*;

public class TheLazyLeafQuest {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int n = sc.nextInt();
    ArrayList<Integer>[] adjList = new ArrayList[n];
    for (int i = 0; i < n; i++)
      adjList[i] = new ArrayList<>();

    for (int i = 0; i < n - 1; ++i) {
      int u = sc.nextInt() - 1;
      int v = sc.nextInt() - 1;
      adjList[u].add(v);
      adjList[v].add(u);
    }

    Tree tree = new Tree(n, 0, adjList);
    int q = sc.nextInt();
    while (q-- > 0) {
      int a = sc.nextInt() - 1, b = sc.nextInt() - 1, c = sc.nextInt();
      if (a == b) {
        pw.println(b + 1);
      } else {
        int lca = tree.query(a, b);
        int aToLca = tree.L[a] - tree.L[lca];
        int ans = tree.findKthAncestor(a, Math.min(aToLca, c));
        c -= aToLca;
        if (c <= 0) {
          pw.println(ans + 1);
        } else {
          int bToLca = tree.L[b] - tree.L[lca];
          if (c >= bToLca) {
            pw.println(b + 1);
          } else {
            ans = tree.findKthAncestor(b, bToLca - c);
            pw.println(ans + 1);
          }
        }
      }
    }

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

  static class Tree {
    ArrayList<Integer>[] adjL;
    int n, root, logN;
    int[] L;
    int[][] P; // P[i][j] --> the 2^j th ancestor of node i

    public Tree(int n, int root, ArrayList<Integer>[] adjL) {
      this.n = n;
      this.root = root;
      this.adjL = adjL;
      this.logN = (int) Math.ceil(Math.log(n) / Math.log(2)) + 1;
      preprocessParents();
    }

    /*
     * Algorithm 1: Parent Sparse Table
     */
    void preprocessParents() // O(n log n)
    {

      P = new int[n][logN];
      L = new int[n];
      for (int i = 0; i < n; i++)
        Arrays.fill(P[i], -1);

      fillParents(root, -1);
    }

    void fillParents(int currentNode, int parent) {
      if (parent != -1)
        L[currentNode] = L[parent] + 1;

      P[currentNode][0] = parent;

      for (int j = 1; j <= logN; j++)
        if (P[currentNode][j - 1] != -1)
          P[currentNode][j] = P[P[currentNode][j - 1]][j - 1];

      for (int child : adjL[currentNode]) {
        if (child == parent)
          continue;

        fillParents(child, currentNode);
      }
    }

    int findKthAncestor(int node, int k) {
      for (int i = 0; i < logN; i++)
        if ((k & (1 << i)) != 0)
          node = P[node][i];

      return node;
    }

    int query(int p, int q) // O(log n)
    {
      // if p is situated on a higher level than q, swap them
      if (L[p] < L[q]) {
        p ^= q;
        q ^= p;
        p ^= q;
      }

      // find largest k such that 2^k is a parent of p
      int k = 0;
      while (1 << k + 1 <= L[p])
        ++k;

      // find the ancestor of p situated on the same level with q
      for (int i = k; i >= 0; --i)
        if (L[p] - (1 << i) >= L[q])
          p = P[p][i];

      if (p == q)
        return p;

      // go up to lowest (non-common) ancestors for p and q
      for (int i = k; i >= 0; --i)
        if (P[p][i] != -1 && P[p][i] != P[q][i]) {
          p = P[p][i];
          q = P[q][i];
        }

      return P[p][0];
    }
  }
}
