package Lab9;

import java.io.*;
import java.util.*;

public class RomeosBlueSkies {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int n = sc.nextInt(), m = sc.nextInt();
    ArrayList<Pair>[] adjList = new ArrayList[n];
    for (int i = 0; i < n; i++)
      adjList[i] = new ArrayList<>();
    while (m-- > 0) {
      int u = sc.nextInt() - 1, v = sc.nextInt() - 1, w = sc.nextInt();
      adjList[u].add(new Pair(v, w));
      adjList[v].add(new Pair(u, w));
    }
    Tree tree = new Tree(n, 0, adjList);
    int q = sc.nextInt();
    while (q-- > 0) {
      int u = sc.nextInt() - 1, v = sc.nextInt() - 1;
      int lca = tree.query(u, v);
      int du = tree.L[u] - tree.L[lca];
      int dv = tree.L[v] - tree.L[lca];
      pw.println(Math.min(tree.findMinWeightToKthParent(u, du), tree.findMinWeightToKthParent(v, dv)));
    }

    pw.flush();

  }

  static class Pair {
    int node, w;

    public Pair(int node, int w) {
      this.node = node;
      this.w = w;
    }
  }

  static class Tree {
    ArrayList<Pair>[] adjL;
    int n, root, logN;
    int[] L;
    int[][] P; // P[i][j] --> the 2^j th ancestor of node i
    int[][] W;

    public Tree(int n, int root, ArrayList<Pair>[] adjL) {
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
      W = new int[n][logN];
      L = new int[n];
      for (int i = 0; i < n; i++) {
        Arrays.fill(P[i], -1);
        Arrays.fill(W[i], Integer.MAX_VALUE);
      }

      fillParents(root, -1, -1);
    }

    void fillParents(int currentNode, int parent, int w) {
      if (parent != -1) {
        L[currentNode] = L[parent] + 1;
      }

      P[currentNode][0] = parent;
      W[currentNode][0] = w;

      for (int j = 1; j <= logN; j++)
        if (P[currentNode][j - 1] != -1) {
          P[currentNode][j] = P[P[currentNode][j - 1]][j - 1];
          W[currentNode][j] = Math.min(W[currentNode][j - 1], W[P[currentNode][j - 1]][j - 1]);
        }

      for (Pair pair : adjL[currentNode]) {
        int child = pair.node;
        if (child == parent)
          continue;

        fillParents(child, currentNode, pair.w);
      }
    }

    int findMinWeightToKthParent(int node, int k) {
      int minWeight = Integer.MAX_VALUE;
      for (int j = logN - 1; j >= 0; j--)
        if (P[node][j] != -1 && k >= (1 << j)) {
          minWeight = Math.min(minWeight, W[node][j]);
          node = P[node][j];
          k -= 1 << j;
        }

      return minWeight;
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