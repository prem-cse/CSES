import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.AbstractCollection;
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
        FlightDiscount solver = new FlightDiscount();
        solver.solve(1, in, out);
        out.close();
    }

    static class FlightDiscount {
        static List<FlightDiscount.Edge>[] edges;
        static List<FlightDiscount.Edge>[] rev;
        static int n;
        static int m;

        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            n = sc.nextInt();
            m = sc.nextInt();
            edges = new ArrayList[n];
            rev = new ArrayList[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new ArrayList<>();
                rev[i] = new ArrayList<>();
            }
            int[] u = new int[m];
            int[] v = new int[m];
            long[] wt = new long[m];

            for (int i = 0; i < m; i++) {
                int a = sc.nextInt() - 1, b = sc.nextInt() - 1;
                long w = sc.nextLong();
                u[i] = a;
                v[i] = b;
                wt[i] = w;
                edges[a].add(new FlightDiscount.Edge(b, w));
                rev[b].add(new FlightDiscount.Edge(a, w));
            }
            long[] dis1 = new long[n];
            long[] disn = new long[n];
            dijkstra(0, dis1, edges);
            dijkstra(n - 1, disn, rev);

            long ans = Long.MAX_VALUE / 10;
            for (int i = 0; i < m; ++i) {
                long temp = dis1[u[i]] + (wt[i] >> 1) + disn[v[i]];
                ans = Math.min(ans, temp);
            }
            out.println(ans);
        }

        static void dijkstra(int start, long[] dis, List<FlightDiscount.Edge>[] edges) {
            PriorityQueue<FlightDiscount.Edge> queue = new PriorityQueue<>();
            boolean[] visited = new boolean[n];
            Arrays.fill(dis, Long.MAX_VALUE / 10);
            dis[start] = 0;
            queue.add(new FlightDiscount.Edge(start, 0));
            while (!queue.isEmpty()) {
                FlightDiscount.Edge curr = queue.poll();
                if (visited[curr.to]) continue;
                visited[curr.to] = true;
                for (FlightDiscount.Edge e : edges[curr.to]) {
                    if (visited[e.to]) continue;
                    if (curr.weight + e.weight < dis[e.to]) {
                        dis[e.to] = curr.weight + e.weight;
                        queue.add(new FlightDiscount.Edge(e.to, dis[e.to]));
                    }
                }
            }
        }

        static class Edge implements Comparable<FlightDiscount.Edge> {
            int to;
            long weight;

            public Edge(int a, long b) {
                to = a;
                weight = b;

            }

            public int compareTo(FlightDiscount.Edge o) {
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

        public long nextLong() {
            return Long.parseLong(next());
        }

    }
}

