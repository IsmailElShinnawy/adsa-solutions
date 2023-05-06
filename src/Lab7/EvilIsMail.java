package Lab7;

import java.io.*;
import java.util.*;

public class EvilIsMail {
  static ArrayList<Integer> adjList[];
  static int counter, SCC = 1, dfs_num[], dfs_low[];
  static int[] inSCC;
  static Stack<Integer> stack;

  static void tarjanSCC()        //O(V + E)
  {
	dfs_num = new int[adjList.length];
	dfs_low = new int[adjList.length];
	inSCC = new int[adjList.length];
	stack = new Stack<>();

	for (int i = 0; i < adjList.length; ++i)
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
    int n = sc.nextInt(), m = sc.nextInt();
    int state[] = new int[n];
    ArrayList<Integer> bulbs[] = new ArrayList[n];
    for(int i = 0; i < n; ++i) {
        state[i] = sc.nextInt();
        bulbs[i] = new ArrayList<>();
    }

    for(int i = 0; i < m; ++i) {
        int x = sc.nextInt();
        for(int j = 0; j < x; ++j) {
            bulbs[sc.nextInt() - 1].add(i);
        }
    }

    adjList = new ArrayList[2 * m];
	for(int i = 0; i < 2 * m; ++i)
		adjList[i] = new ArrayList<>();

    for(int i = 0; i < n; ++i) {
      int si = bulbs[i].get(0), sj = bulbs[i].get(1);
      if(state[i] == 0) {
        adjList[si].add(sj + m);
        adjList[sj].add(si + m);
        adjList[si + m].add(sj);
        adjList[sj + m].add(si);
      } else {
        adjList[si].add(sj);
        adjList[sj].add(si);
        adjList[si + m].add(sj + m);
        adjList[sj + m].add(si + m);
      }
    }

	tarjanSCC();

	boolean flag = true;
	for (int i = 0; i < m; ++i) {
		if(inSCC[i] == inSCC[i + m]) {
			flag = false;
			break;
		}
	}

	System.out.println(flag ? "YES" : "NO");
    
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