package Lab2;

import java.io.*;
import java.util.*;

public class HxH {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    int N = sc.nextInt();
    SegmentTree segTree = new SegmentTree(N);
    for (int i = 0; i < N; ++i) {
      segTree.update_point(i + 1, sc.nextInt());
    }
    int M = sc.nextInt();
    StringTokenizer st;
    while (M-- > 0) {
      st = new StringTokenizer(sc.nextLine());
      if (st.countTokens() == 3) {
        segTree.update_range(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1,
            Integer.parseInt(st.nextToken()));
      } else {
        pw.println(segTree.query(Integer.parseInt(st.nextToken()) + 1, Integer.parseInt(st.nextToken()) + 1));
      }
      // System.out.println(segTree);
    }

    pw.flush();

  }

  static class SegmentTree {
    // Range Sum Query (with lazy propagation)
    // 1-based DS, OOP

    int N; // the number of elements in the array as a power of 2 (i.e. after padding)
    int size;
    long[] sTree, lazy;

    SegmentTree(int size) {
      this.size = size;
      N = 1;
      while (N < size) // padding
        N = 2 * N;

      sTree = new long[2 * N]; // no. of nodes = 2*N - 1, we add one to cross out index zero
      Arrays.fill(sTree, Long.MAX_VALUE);
      lazy = new long[2 * N];
      // Arrays.fill(lazy, Integer.MAX_VALUE);
    }

    void update_point(int index, int val) { // adds the value to the given position in O(log n)
      index += N - 1; // the actual index in the segment tree
      sTree[index] = val; // update the value

      while (index > 1) { // update the ancestors
        index = index / 2;
        sTree[index] = Math.min(sTree[2 * index], sTree[2 * index + 1]);
      }
    }

    void update_range(int left, int right, int val) // adds the value to the given interval in O(log n)
    {
      if (left > right) {
        update_range(1, 1, N, 1, right, val);
        update_range(1, 1, N, left, size, val);
      } else {
        update_range(1, 1, N, left, right, val);
      }
    }

    void update_range(int node, int nodeStart, int nodeEnd, int left, int right, int val) {
      if (left > nodeEnd || right < nodeStart) // if the current node's interval doesn't intersect with the required
                                               // interval(they are disjoint), return
        return;

      if (nodeStart >= left && nodeEnd <= right) { // if the current node's interval is completely covered by the
                                                   // required interval, update the whole range
        sTree[node] += val;
        lazy[node] += val;
      } else {
        // update the left and right children

        int mid = (nodeStart + nodeEnd) / 2;

        propagate(node, nodeStart, mid, nodeEnd); // lazy propagation

        update_range(2 * node, nodeStart, mid, left, right, val);
        update_range(2 * node + 1, mid + 1, nodeEnd, left, right, val);

        sTree[node] = Math.min(sTree[2 * node], sTree[2 * node + 1]);
      }

    }

    void propagate(int node, int b, int mid, int e) {
      // update the children of the lazy node
      lazy[2 * node] += lazy[node];
      lazy[2 * node + 1] += lazy[node];

      // update the children of the node
      sTree[2 * node] += lazy[node];
      sTree[2 * node + 1] += lazy[node];

      // reset the lazy node
      lazy[node] = 0;
    }

    long query(int left, int right) { // gets the value of the given range in O(log n)
      if (left > right) {
        return Math.min(query(1, 1, N, 1, right), query(1, 1, N, left, size));
      }
      return query(1, 1, N, left, right);
    }

    long query(int node, int nodeStart, int nodeEnd, int left, int right) {
      if (left > nodeEnd || right < nodeStart) // if the current node's interval doesn't intersect with the required
                                               // interval(they are disjoint), return neutral value
        return Long.MAX_VALUE;

      if (nodeStart >= left && nodeEnd <= right)// if the current node's interval is completely covered by the required
                                                // interval, return the value of the node
        return sTree[node];

      // get the value from the children

      int mid = (nodeStart + nodeEnd) / 2;

      propagate(node, nodeStart, mid, nodeEnd);// lazy propagation

      long leftChildQuery = query(2 * node, nodeStart, mid, left, right);
      long rightChildQuery = query(2 * node + 1, mid + 1, nodeEnd, left, right);

      return Math.min(leftChildQuery, rightChildQuery);

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
