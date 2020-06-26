import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collection;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Queue;
import java.io.BufferedReader;
import java.util.ArrayDeque;
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
        Monsters solver = new Monsters();
        solver.solve(1, in, out);
        out.close();
    }

    static class Monsters {
        public void solve(int testNumber, FastReader sc, PrintWriter out) {

            int n = sc.nextInt();
            int m = sc.nextInt();
            char[][] s = new char[n][];
            int st = 0;
            int[] mon = new int[n * m];
            int[] dis = new int[n * m];
            int MAX = (int) 1e9;
            Arrays.fill(mon, MAX);
            Arrays.fill(dis, -1);
            Queue<Integer> q = new ArrayDeque<>();

            for (int i = 0; i < n; ++i) {
                s[i] = sc.next().toCharArray();
                for (int j = 0; j < s[i].length; ++j) {
                    if (s[i][j] == 'M') {
                        q.add(i * m + j);
                        mon[i * m + j] = 0;
                    } else if (s[i][j] == 'A') {
                        st = i * m + j;
                    }
                }
            }

            int[] dx = {0, 0, -1, 1};
            int[] dy = {1, -1, 0, 0};
            char[] c = {'R', 'L', 'U', 'D'};

            // min distance from a cell to some monster
            while (q.size() > 0) {
                int num = q.poll();
                int i = num / m;
                int j = num % m;
                for (int k = 0; k < 4; ++k) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x < 0 || y < 0 || x >= n || y >= m || s[x][y] == '#' || mon[x * m + y] != MAX) continue;
                    q.add(x * m + y);
                    mon[x * m + y] = mon[num] + 1;
                }
            }

            q.add(st);
            int[] par = new int[n * m];
            char[] dir = new char[n * m];
            par[st] = st;
            dis[st] = 0;
            boolean found = false;
            int last = -1;

            // min distance from A to any cell

            while (q.size() > 0) {
                int num = q.poll();
                int i = num / m;
                int j = num % m;
                if (i == 0 || j == 0 || i == n - 1 || j == m - 1) {
                    last = num;
                    found = true;
                    break;
                }
                for (int k = 0; k < 4; ++k) {
                    int x = i + dx[k];
                    int y = j + dy[k];
                    if (x < 0 || y < 0 || x >= n || y >= m || s[x][y] == '#' || dis[x * m + y] != -1) continue;
                    else if (dis[num] + 1 >= mon[x * m + y]) continue; // if monster can reach before us then skip
                    dis[x * m + y] = dis[num] + 1;
                    q.add(x * m + y);
                    par[x * m + y] = num;
                    dir[x * m + y] = c[k];
                }
            }

            if (found) {
                out.println("YES");
                StringBuilder path = new StringBuilder();
                while (par[last] != last) {
                    path.append(dir[last]);
                    last = par[last];
                }
                path.reverse();
                out.println(path.length());
                out.println(path);
            } else out.println("NO");


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

