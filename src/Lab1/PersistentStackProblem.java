package Lab1;
import java.io.*;
import java.util.*;

public class PersistentStackProblem {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        //Implement your solution here
        PersistentStack ps = new PersistentStack();
        int q = sc.nextInt();
        while(q-- > 0) {
            String op = sc.next();
            if(op.equals("push")) {
                int value = sc.nextInt(), version = sc.nextInt();
                ps.push(version, value);
            } else if(op.equals("min")) {
                int version = sc.nextInt();
                pw.println(ps.getMin(version));
            } else {
                int version = sc.nextInt();
                pw.println(ps.pop(version));
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

    static class PersistentStack {
        ArrayList<Node> versions;
    
        public PersistentStack() {
            versions = new ArrayList<>();
            versions.add(new Node());
        }
    
        public void push(int version, int value) {
            Node node = new Node(value, versions.get(version));
            versions.add(node);
        }
    
        public Integer pop(int version) {
            Node node = versions.get(version);
            if(node == null) return null;
            versions.add(node.getParent());
            return node.top;
        }
    
        public Integer getMin(int version) {
            Node node = versions.get(version);
            if(node == null) return null;
            return versions.get(version).min;
        }
    
    
        static class Node {
            Integer top, min;
            Node parent;
    
            public Node() {
    
            }
    
            public Node(Integer top, Node parent) {
                this.top = top;
                this.parent = parent;
    
                if (this.parent == null || this.parent.min == null) this.min = this.top;
                else this.min = Math.min(this.parent.min, this.top);
            }
    
            public Node getParent() {
                if (parent == null)
                    return new Node();
                return parent;
            }
        }
    }
}