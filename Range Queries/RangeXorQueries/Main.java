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
        RangeXorQueries solver = new RangeXorQueries();
        solver.solve(1, in, out);
        out.close();
    }

    static class RangeXorQueries {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int q = sc.nextInt();
            int[] a = new int[n + 1];
            for (int i = 0; i < n; ++i) a[i + 1] = sc.nextInt();
            long[] pre = new long[n + 1];
            for (int i = 1; i <= n; ++i) pre[i] = pre[i - 1] ^ a[i];
            while (q-- > 0) {
                int l = sc.nextInt();
                int r = sc.nextInt();
                out.println(pre[r] ^ pre[l - 1]);
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

