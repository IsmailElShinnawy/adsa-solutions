package Lab1;

import java.io.*;
import java.util.*;

public class ProjectTeams {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        PrintWriter pw = new PrintWriter(System.out);

        //Implement your solution here
        int n = sc.nextInt(), q = sc.nextInt();
        int parent[] = new int[n + 1];
        for(int i = 0; i <= n; i++) parent[i] = i;
        int rank[] = new int[n + 1];
        int size[] = new int[n + 1];
        Arrays.fill(size, 1);

        while(q-- > 0) {
            int type = sc.nextInt();
            if(type == 1) {
                int si = sc.nextInt(), sj = sc.nextInt();
                union(si, sj, parent, rank, size);
            } else {
                int si = sc.nextInt();
                pw.println(size[find(si, parent)]);
            }
        }

        pw.flush();

    }

    static int find(int i, int parent[]) {
        if(parent[i] == i) return i;
        return parent[i] = find(parent[i], parent);
    }

    static void union(int i, int j, int parent[], int rank[], int size[]) {
        int pi = find(i, parent);
        int pj = find(j, parent);
        if(pi == pj) return;
        if(rank[pi] > rank[pj]) {
            parent[pj] = pi;
            size[pi] += size[pj];
        } else if(rank[pi] < rank[pj]) {
            parent[pi] = pj;
            size[pj] += size[pi];
        } else {
            parent[pi] = pj;
            size[pj] += size[pi];
            rank[pj]++;
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