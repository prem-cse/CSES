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
        ForestQueriesII solver = new ForestQueriesII();
        solver.solve(1, in, out);
        out.close();
    }

    static class ForestQueriesII {
        int[][] Bit;

        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int q = sc.nextInt();
            int[][] tab = new int[n + 1][n + 1];
            Bit = new int[n + 1][n + 1];
            for (int i = 1; i <= n; ++i) {
                char[] s = sc.next().toCharArray();
                for (int j = 1; j <= n; ++j) {
                    tab[i][j] = s[j - 1] == '*' ? 1 : 0;
                    update(i, j, 0, tab[i][j]);
                }
            }

            while (q-- > 0) {
                int t = sc.nextInt();
                if (t == 1) {
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    update(x, y, tab[x][y], tab[x][y] ^ 1);
                    tab[x][y] ^= 1;
                } else {
                    int l1 = sc.nextInt();
                    int r1 = sc.nextInt();
                    int l2 = sc.nextInt();
                    int r2 = sc.nextInt();
                    out.println(query(l1, r1, l2, r2));
                }
            }
        }

        long query(int l1, int r1, int l2, int r2) {
            return query(l2, r2) - query(l1 - 1, r2) - query(l2, r1 - 1) + query(l1 - 1, r1 - 1);
        }

        long query(int x, int y) {
            long ret = 0;
            for (int i = x; i > 0; i -= -i & i)
                for (int j = y; j > 0; j -= -j & j)
                    ret += Bit[i][j];
            return ret;
        }

        void update(int x, int y, int from, int to) {
            long val = to - from; // change to previous to new value
            int n = Bit.length;
            for (int i = x; i < n; i += -i & i) {
                for (int j = y; j < n; j += -j & j) {
                    Bit[i][j] += val;
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

    }
}

