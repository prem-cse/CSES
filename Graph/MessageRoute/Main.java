import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Queue;
import java.io.BufferedReader;
import java.util.ArrayDeque;
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
        MessageRoute solver = new MessageRoute();
        solver.solve(1, in, out);
        out.close();
    }
 
    static class MessageRoute {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {
 
            int n = sc.nextInt();
            int m = sc.nextInt();
            List<Integer>[] edges = new ArrayList[n + 1];
            for (int i = 0; i < edges.length; ++i) edges[i] = new ArrayList<>();
            for (int i = 1; i <= m; ++i) {
                int u = sc.nextInt();
                int v = sc.nextInt();
                edges[u].add(v);
                edges[v].add(u);
            }
            int[] par = new int[n + 1];
            Arrays.fill(par, -1);
            par[1] = 1;
            Queue<Integer> q = new ArrayDeque<>();
            q.add(1);
            while (q.size() > 0) {
                int v = q.poll();
                for (int node : edges[v]) {
                    if (par[node] == -1) {
                        q.add(node);
                        par[node] = v;
                    }
                }
            }
 
            if (par[n] == -1) out.println("IMPOSSIBLE");
            else {
                List<Integer> path = new ArrayList<>();
                int v = n;
                while (par[v] != v) {
                    path.add(v);
                    v = par[v];
                }
                path.add(v);
                Collections.reverse(path);
                out.println(path.size());
                for (int x : path) out.print(x + " ");
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
 
