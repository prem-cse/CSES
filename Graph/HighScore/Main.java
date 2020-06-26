import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author prem_cse
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        FastReader in = new FastReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        HighScore solver = new HighScore();
        solver.solve(1, in, out);
        out.close();
    }

    static class HighScore {
        List<Integer>[] edges;
        int[] u;
        int[] v;
        int[] w;
        boolean[] vis;
        long[] dis;

        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int m = sc.nextInt();
            edges = new ArrayList[n + 1];
            u = new int[m + 1];
            v = new int[m + 1];
            w = new int[m + 1];
            vis = new boolean[n + 1];

            for (int i = 0; i < edges.length; ++i) edges[i] = new ArrayList<>();
            for (int i = 1; i <= m; ++i) {
                u[i] = sc.nextInt();
                v[i] = sc.nextInt();
                w[i] = -1 * sc.nextInt();
                edges[v[i]].add(u[i]);
            }


            dfs(n);
            dis = new long[n + 1];
            Arrays.fill(dis, Long.MAX_VALUE / 10);
            dis[1] = 0;
            for (int V = 1; V < n; ++V) {
                bellmanFord(m);
            }

            if (bellmanFord(m)) {
                out.println(-1);
            } else out.println(-dis[n]);

        }

        private boolean bellmanFord(int m) {
            boolean updated = false;
            for (int e = 1; e <= m; e++) {
                if (vis[u[e]] && vis[v[e]] && dis[u[e]] != Long.MAX_VALUE / 10) {
                    long d = dis[u[e]] + w[e];
                    if (dis[v[e]] > d) {
                        updated = true;
                        dis[v[e]] = d;
                    }
                }
            }
            return updated;
        }

        private void dfs(int v) {
            vis[v] = true;
            for (int node : edges[v]) {
                if (!vis[node]) dfs(node);
            }
        }

    }

    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader(InputStream stream) {
            br = new BufferedReader(new
                    InputStreamReader(stream), 32768);
        }

        public String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

    }
}

