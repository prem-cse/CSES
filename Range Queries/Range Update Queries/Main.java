import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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
        RangeUpdateQueries solver = new RangeUpdateQueries();
        solver.solve(1, in, out);
        out.close();
    }

    static class RangeUpdateQueries {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int q = sc.nextInt();
            RangeUpdateQueries.Fenwick bit = new RangeUpdateQueries.Fenwick(n);
            for (int i = 1; i <= n; ++i) {
                long x = sc.nextLong();
                bit.update(i, i, x);
            }
            while (q-- > 0) {
                int t = sc.nextInt();
                if (t == 1) {
                    int a = sc.nextInt();
                    int b = sc.nextInt();
                    int u = sc.nextInt();
                    bit.update(a, b, u);
                } else {
                    int k = sc.nextInt();
                    out.println(bit.point_query(k));
                }
            }
        }

        static class Fenwick {
            int n;
            long[] ft;

            public Fenwick(int n) {
                this.n = n;
                ft = new long[n + 2];
            }

            void update(int l, int r, long val) {
                point_update(l, val);
                point_update(r + 1, -val);
            }

            long point_query(int b) {
                long sum = 0;
                while (b > 0) {
                    sum += ft[b];
                    b -= b & -b;
                }
                return sum;
            }

            void point_update(int k, long val) {
                while (k <= n) {
                    ft[k] += val;
                    k += k & -k;
                }
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

