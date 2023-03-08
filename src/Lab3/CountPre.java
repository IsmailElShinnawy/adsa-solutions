package Lab3;

import java.util.*;
import java.io.*;

public class CountPre {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    Trie t = new Trie();
    int N = sc.nextInt();
    int n = N;
    while (N-- > 0) {
      String s = sc.next();
      t.insert(s);
    }
    int Q = sc.nextInt();
    while (Q-- > 0) {
      String pre = sc.next();
      pw.println(n - t.count(pre));
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

  static class Node {
    Node children[] = new Node[26];
    int count = 0;
  }

  static class Trie {
    Node root = new Node();

    void insert(String s) {
      Node curr = root;
      curr.count++;
      for (int i = 0; i < s.length(); ++i) {
        int idx = s.charAt(i) - 'a';
        if (curr.children[idx] == null) {
          curr.children[idx] = new Node();
        }
        curr = curr.children[idx];
        curr.count++;
      }
    }

    int count(String pre) {
      Node curr = root;
      for (int i = 0; i < pre.length(); ++i) {
        int idx = pre.charAt(i) - 'a';
        if (curr.children[idx] == null) {
          return 0;
        }
        curr = curr.children[idx];
      }
      return curr.count;
    }
  }
}
