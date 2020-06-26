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
import java.util.Collections;
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
        CycleFinding solver = new CycleFinding();
        solver.solve(1, in, out);
        out.close();
    }

    static class CycleFinding {
        static CycleFinding.Edge[] edges;

        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int m = sc.nextInt();
            edges = new CycleFinding.Edge[m];

            for (int i = 0; i < m; ++i) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                int w = sc.nextInt();
                edges[i] = new CycleFinding.Edge(u, v, w);
            }
            long[] d = new long[n + 1];
            int[] par = new int[n + 1];
            Arrays.fill(par, -1);
            int x = 0;
            for (int i = 1; i <= n; ++i) {
                x = -1;
                for (CycleFinding.Edge e : edges) {
                    if (d[e.u] + e.weight < d[e.v]) {
                        d[e.v] = d[e.u] + e.weight;
                        par[e.v] = e.u;
                        x = e.v;
                    }
                }
            }
            if (x == -1) {
                out.println("NO");
            } else {
                for (int i = 1; i <= n; ++i) x = par[x];
                List<Integer> cycle = new ArrayList<>();
                int v = x;
                while (true) {
                    cycle.add(v);
                    if (v == x && cycle.size() > 1) break;
                    v = par[v];
                }

                Collections.reverse(cycle);
                out.println("YES");
                for (int node : cycle) out.print(node + " ");
            }

        }

        static class Edge implements Comparable<CycleFinding.Edge> {
            int u;
            int v;
            long weight;

            public Edge(int u, int v, long w) {
                this.u = u;
                this.v = v;
                this.weight = w;

            }

            public int compareTo(CycleFinding.Edge o) {
                return Long.compare(weight, o.weight);
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

