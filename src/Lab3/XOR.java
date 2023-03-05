package Lab3;

import java.io.*;
import java.util.*;

public class XOR {
  public static void main(String[] args) throws IOException{
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    BinaryTrie trie = new BinaryTrie();
    int ans = 0;
    int n = sc.nextInt();
    while(n-- > 0) {
        int val = sc.nextInt();
        trie.insert(val);
        ans = Math.max(ans, trie.getMax(val));
    }
    pw.println(ans);

    // Implement your solution here
 
    pw.flush();

  }

  static class BinaryTrie {
    Node root = new Node();

    void insert(int value) {
        Node cur = root;
        for (int i = 31; i >= 0; i--) {
            int val = (value >> i) & 1;
            if(val == 1) {
                if(cur.one == null) {
                    cur.one = new Node();
                }
                cur = cur.one;
            } else {
                if(cur.zero == null) {
                    cur.zero = new Node();
                }
                cur = cur.zero;
            }
        }
    }

    int getMax(int value) {
        int answer = 0;
        Node cur = root;
        for (int i = 31; i >= 0; i--) {
            int val = (value >> i) & 1;
            if(val == 1) {
                if(cur.zero != null) {
                    answer += (1 << i);
                    cur = cur.zero;
                } else {
                    cur = cur.one;
                }
            } else {
                if(cur.one != null) {
                    answer += (1 << i);
                    cur = cur.one;
                } else {
                    cur = cur.zero;
                }
            }
        }
        return answer;
    }


    static class Node {
        Node one, zero;

        public Node() {
        }
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