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
        RangeMinimumQueriesI solver = new RangeMinimumQueriesI();
        solver.solve(1, in, out);
        out.close();
    }

    static class RangeMinimumQueriesI {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int q = sc.nextInt();
            long[] a = new long[n];
            for (int i = 0; i < n; ++i) a[i] = sc.nextLong();
            RangeMinimumQueriesI.SegmentTree seg = new RangeMinimumQueriesI.SegmentTree(a, n);
            while (q-- > 0) {
                int l = sc.nextInt() - 1;
                int r = sc.nextInt() - 1;
                out.println(seg.query(a, l, r));
            }
        }

        public static class SegmentTree {
            long[] seg;

            SegmentTree(long[] a, int n) {
                seg = new long[4 * n];
                buildTree(a, 0, n - 1, 1);
            }

            public void buildTree(long[] a, int start, int end, int i) {
                if (start == end) {
                    seg[i] = a[start];
                    return;
                }
                int mid = start + (end - start) / 2;
                buildTree(a, start, mid, 2 * i);
                buildTree(a, mid + 1, end, 2 * i + 1);

                seg[i] = Math.min(seg[2 * i], seg[2 * i + 1]);

            }

            public long query(long[] a, int l, int r) {
                return query(0, a.length - 1, 1, l, r);
            }

            public long query(long m, long mid2, int i, int l, int r) {
                // OUTSIDE
                if (m > r || mid2 < l)
                    return Integer.MAX_VALUE; // depend on q
                //INSIDE
                if (m >= l && mid2 <= r)
                    return seg[i];
                // PARTIAL
                long mid = m + (mid2 - m) / 2;
                long ansl = query(m, mid, 2 * i, l, r);
                long ansr = query(mid + 1, mid2, 2 * i + 1, l, r);

                return Math.min(ansl, ansr);

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

