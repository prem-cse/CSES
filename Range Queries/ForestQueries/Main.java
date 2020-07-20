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
        ForestQueries solver = new ForestQueries();
        solver.solve(1, in, out);
        out.close();
    }

    static class ForestQueries {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int q = sc.nextInt();
            long[][] dp = new long[n + 1][n + 1];
            for (int i = 1; i <= n; ++i) {
                char[] s = sc.next().toCharArray();
                for (int j = 1; j <= n; ++j) {
                    dp[i][j] = ((s[j - 1] == '*') ? 1 : 0) + dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - 1];
                }
            }
            while (q-- > 0) {
                int l1 = sc.nextInt();
                int r1 = sc.nextInt();
                int l2 = sc.nextInt();
                int r2 = sc.nextInt();
                long ans = dp[l2][r2] - dp[l1 - 1][r2] - dp[l2][r1 - 1] + dp[l1 - 1][r1 - 1];
                out.println(ans);

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

