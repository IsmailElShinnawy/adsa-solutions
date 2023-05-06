package Lab7;

import java.io.*;
import java.util.*;

public class Polizeistation {

    static ArrayList<Integer>[] adjList;
    static int V, counter, SCC = 1, dfs_num[], dfs_low[], price[];
    static int[] inSCC;
    static Stack<Integer> stack;

    static void tarjanSCC()        //O(V + E)
    {
        for (int i = 0; i < V; ++i)
            if (dfs_num[i] == 0)
                tarjanSCC(i);
    }

    static void tarjanSCC(int u) {
        dfs_num[u] = dfs_low[u] = ++counter;
        stack.push(u);

        for (int v : adjList[u]) {
            if (dfs_num[v] == 0)
                tarjanSCC(v);
            if (inSCC[v] == 0)
                dfs_low[u] = Math.min(dfs_low[u], dfs_low[v]);
        }
        if (dfs_num[u] == dfs_low[u]) {
            int v;
            do {
                v = stack.pop();
                inSCC[v] = SCC;
            } while (v != u);
            SCC++;
        }
    }

  public static void main(String[] args) throws IOException {
    Scanner sc = new Scanner(System.in);
    PrintWriter pw = new PrintWriter(System.out);

    // Implement your solution here
    V = sc.nextInt(); // number of vertices
    price = new int[V]; // price of buidling a police station at city i
    adjList = new ArrayList[V];
    dfs_num = new int[V];
    dfs_low = new int[V];
    inSCC = new int[V];
    stack = new Stack<Integer>();

    for(int i = 0; i < V; ++i) {
        price[i] = sc.nextInt();
    }
    
    int M = sc.nextInt(); // number of edges
    
    for(int i = 0; i < V; ++i) {
        adjList[i] = new ArrayList<>();
    }

    while(M-- > 0) {
        int u = sc.nextInt() - 1;
        int v = sc.nextInt() - 1;
        adjList[u].add(v);
    }

    tarjanSCC();

    // System.out.println(Arrays.toString(inSCC));
    // System.out.println(Arrays.deepToString(adjList));

    int ans[][] = new int[SCC][2];

    for(int i = 0; i < ans.length; ++i) {
        ans[i][0] = Integer.MAX_VALUE;
    }

    for(int i = 0; i < V; ++i) {
        int cc = inSCC[i];
        if(price[i] < ans[cc][0]) {
          ans[cc][0] = price[i];
          ans[cc][1] = 1;
        } else if(price[i] == ans[cc][0]) {
          ans[cc][1]++;
        }
    }

    long minPrice = 0;
    long minCount = 1;
    for(int i = 1; i < ans.length; ++i) {
      minPrice += ans[i][0];
      minCount = (minCount * ans[i][1]) % 1000000007;
    }

    System.out.printf("%d %d\n", minPrice, minCount);

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

}