package Lab1;

import java.util.*;
import java.io.*;

public class PersistentQueueProblem {
  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    PersistentQueue pq = new PersistentQueue();
    int q = sc.nextInt();
    while (q-- > 0) {
      String op = sc.next();
      if (op.equals("enqueue")) {
        pq.enqueue(sc.nextInt());
      } else if (op.equals("dequeue")) {
        pq.dequeue();
      } else if (op.equals("print")) {
        pw.println(pq.toString(sc.nextInt()));
      }
    }

    pw.flush();

  }

  static class Node {
    int val;
    Node next, prev;

    public Node() {
    }

    public Node(int val, Node next, Node prev) {
      this.val = val;
      this.next = next;
      this.prev = prev;
    }
  }

  static class Pair {
    Node head, tail;

    public Pair(Node head, Node tail) {
      this.head = head;
      this.tail = tail;
    }
  }

  static class PersistentQueue {
    ArrayList<Pair> versions = new ArrayList<>();

    public PersistentQueue() {
      versions.add(new Pair(null, null));
    }

    public void enqueue(int val) {
      Pair latest = versions.get(versions.size() - 1);
      if (latest.tail != null) {
        Node newNode = new Node(val, null, latest.tail);
        latest.tail.next = newNode;
        versions.add(new Pair(latest.head, newNode));
      } else {
        Node newNode = new Node(val, null, null);
        versions.add(new Pair(newNode, newNode));
      }
    }

    public void dequeue() {
      Pair latest = versions.get(versions.size() - 1);
      if (latest.head == null || latest.head == latest.tail) {
        versions.add(new Pair(null, null));
      } else {
        versions.add(new Pair(latest.head.next, latest.tail));
      }
    }

    public String toString(int version) {
      Pair q = versions.get(version);
      if (q.head == null) {
        return "empty";
      }

      StringBuilder sb = new StringBuilder();
      Node curr = q.head;
      while (curr != q.tail) {
        sb.append(curr.val + " ");
        curr = curr.next;
      }
      sb.append(curr.val);
      return sb.toString();
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