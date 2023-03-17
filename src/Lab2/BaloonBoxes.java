package Lab2;

import java.io.*;
import java.util.*;

public class BaloonBoxes {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int N = sc.nextInt(), Q = sc.nextInt();
    SegmentTree segTree = new SegmentTree(N);
    // System.out.println("initial");
    // System.out.println(segTree);
    while (Q-- > 0) {
      int op = sc.nextInt();
      if (op == 1) {
        int idx = sc.nextInt(), c = sc.nextInt();
        segTree.update_point(idx, c, true);
      } else if (op == 2) {
        int idx = sc.nextInt(), c = sc.nextInt();
        segTree.update_point(idx, c, false);
      } else {
        int l = sc.nextInt(), r = sc.nextInt();
        pw.println(segTree.query(l, r));
      }
      // System.out.println(segTree);
    }

    pw.flush();

  }

  static class Pair {
    int x;
    String cs;

    Pair(int x, String cs) {
      this.x = x;
      this.cs = cs;
    }
  }

  static class SegmentTree {
    // Range Sum Query (with lazy propagation)
    // 1-based DS, OOP

    int N; // the number of elements in the array as a power of 2 (i.e. after padding)
    long[] sTree;
    int count[][];

    SegmentTree(int size) {
      count = new int[size + 1][51];
      N = 1;
      while (N < size) // padding
        N = 2 * N;

      sTree = new long[2 * N]; // no. of nodes = 2*N - 1, we add one to cross out index zero
      // lazy = new int[2 * N];
    }

    void update_point(int index, int val, boolean set) // adds the value to the given position in O(log n)
    {
      int countIndex = index;
      if (set) {
        count[countIndex][val]++;
      } else {
        count[countIndex][val]--;
      }
      index += N - 1; // the actual index in the segment tree
      if (count[countIndex][val] > 0) {
        sTree[index] |= (1L << val);
      } else {
        sTree[index] &= ~(1L << val);
      }

      while (index > 1) { // update the ancestors
        index = index / 2;
        sTree[index] = sTree[2 * index] | sTree[2 * index + 1];
      }
    }

    String query(int left, int right) { // gets the value of the given range in O(log n)
      return format(query(1, 1, N, left, right));
    }

    long query(int node, int nodeStart, int nodeEnd, int left, int right) {
      if (left > nodeEnd || right < nodeStart) // if the current node's interval doesn't intersect with the required
                                               // interval(they are disjoint), return neutral value
        return 0;

      if (nodeStart >= left && nodeEnd <= right)// if the current node's interval is completely covered by the required
                                                // interval, return the value of the node
        return sTree[node];

      // get the value from the children

      int mid = (nodeStart + nodeEnd) / 2;

      // propagate(node, nodeStart, mid, nodeEnd);// lazy propagation

      long leftChildQuery = query(2 * node, nodeStart, mid, left, right);
      long rightChildQuery = query(2 * node + 1, mid + 1, nodeEnd, left, right);

      return leftChildQuery | rightChildQuery;

    }

    static String format(long x) {
      // System.out.println(Long.toBinaryString(x));
      StringBuilder sb = new StringBuilder();
      int count = 0;
      for (int i = 1; i <= 50; ++i) {
        if ((x & (1L << i)) != 0) {
          count++;
          sb.append(i).append(" ");
        }
      }

      if (count == 0) {
        return "0";
      }

      return count + " " + sb.substring(0, sb.length() - 1);
    }

    @Override
    public String toString() {
      return Arrays.toString(sTree);
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
