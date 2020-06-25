import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
 
 
 
 
public class Main{
	 public static class FastReader {
			BufferedReader br;
			StringTokenizer root;
			
	 
			public FastReader() {
				br = new BufferedReader(new InputStreamReader(System.in));
			}
	 
			String next() {
				while (root == null || !root.hasMoreTokens()) {
					try {
						root = new StringTokenizer(br.readLine());
					} catch (Exception addd) {
						addd.printStackTrace();
					}
				}
				return root.nextToken();
			}
	 
			int nextInt() {
				return Integer.parseInt(next());
			}
	 
			double nextDouble() {
				return Double.parseDouble(next());
			}
	 
			long nextLong() {
				return Long.parseLong(next());
			}
	 
			String nextLine() {
				String str = "";
				try {
					str = br.readLine();
				} catch (Exception addd) {
					addd.printStackTrace();
				}
				return str;
			}
		}
		
	public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
	public static FastReader sc = new FastReader();
 
	static int mod = (int)(1e9+7),MAX=(int)(2e6+10);
	static List<Integer>[] edges;
 
	static int n,m;
 
    static boolean[][] vis;
 
	public static void main(String[] args) throws IOException{
 
		   n = sc.nextInt();
	        m = sc.nextInt();
	        char[][] s = new char[n][];
	        for(int i=0;i<n;++i) s[i] = sc.next().toCharArray();
	        vis = new boolean[n][m];
	        int ans = 0;
	        for(int i=0;i<n;++i){
	            for(int j=0;j<m;++j){
	                if(s[i][j] == '.' && !vis[i][j]){
	                    bfs(i,j,s);
	                    ++ans;
	                }
	            }
	        }
	        out.println(ans);
        out.close();
	}
	 static int[] dx = {0,0,-1,1};
	    static int[] dy = {1,-1,0,0};
 
	    private static void bfs(int i, int j,char[][] s) {
	       
	       Queue<int[]> q = new ArrayDeque<>();
	       q.add(new int[] {i,j});
	       vis[i][j] = true;
	       while(q.size() > 0) {
	    	   int[] v = q.poll();
	    	   for(int k=0;k<4;++k) {
	    		   int x = v[0] + dx[k];
		           int y = v[1] + dy[k];
		           if(x < 0 || y < 0 || x >=n || y >= m || vis[x][y] || s[x][y] == '#') continue;
		           q.add(new int[] {x,y});
		           vis[x][y] = true;
	    	   }
	       }
	      
	    }
	
}
