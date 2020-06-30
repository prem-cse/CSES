import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

public class Main {
	static class Reader 
    { 
        final private int BUFFER_SIZE = 1 << 16; 
        private DataInputStream din; 
        private byte[] buffer; 
        private int bufferPointer, bytesRead; 
  
        public Reader() 
        { 
            din = new DataInputStream(System.in); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public Reader(String file_name) throws IOException 
        { 
            din = new DataInputStream(new FileInputStream(file_name)); 
            buffer = new byte[BUFFER_SIZE]; 
            bufferPointer = bytesRead = 0; 
        } 
  
        public String readLine() throws IOException 
        { 
            byte[] buf = new byte[64]; // line length 
            int cnt = 0, c; 
            while ((c = read()) != -1) 
            { 
                if (c == '\n') 
                    break; 
                buf[cnt++] = (byte) c; 
            } 
            return new String(buf, 0, cnt); 
        } 
  
        public int nextInt() throws IOException 
        { 
            int ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do
            { 
                ret = ret * 10 + c - '0'; 
            }  while ((c = read()) >= '0' && c <= '9'); 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public long nextLong() throws IOException 
        { 
            long ret = 0; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        public double nextDouble() throws IOException 
        { 
            double ret = 0, div = 1; 
            byte c = read(); 
            while (c <= ' ') 
                c = read(); 
            boolean neg = (c == '-'); 
            if (neg) 
                c = read(); 
  
            do { 
                ret = ret * 10 + c - '0'; 
            } 
            while ((c = read()) >= '0' && c <= '9'); 
  
            if (c == '.') 
            { 
                while ((c = read()) >= '0' && c <= '9') 
                { 
                    ret += (c - '0') / (div *= 10); 
                } 
            } 
  
            if (neg) 
                return -ret; 
            return ret; 
        } 
  
        private void fillBuffer() throws IOException 
        { 
            bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE); 
            if (bytesRead == -1) 
                buffer[0] = -1; 
        } 
  
        private byte read() throws IOException 
        { 
            if (bufferPointer == bytesRead) 
                fillBuffer(); 
            return buffer[bufferPointer++]; 
        } 
  
        public void close() throws IOException 
        { 
            if (din == null) 
                return; 
            din.close(); 
        } 
    }

	public static PrintWriter out = new PrintWriter (new BufferedOutputStream(System.out));
	public static Reader sc = new Reader();
 
	static int mod = (int)(1e9+7),MAX=(int)(2e6+10);
	
	static List<Edge>[] edges,rev;
	static List<Integer>[] g;

	static int[] in;
	static List<Integer> order; 
	
	public static void main(String[] args) throws IOException{

		int n = sc.nextInt();
		int m = sc.nextInt();
		int[] u = new int[m];
		int[] v = new int[m];
		int[] w = new int[m];
		in = new int[n+1];
		edges = new ArrayList[n+1];
		rev = new ArrayList[n+1];
		g = new ArrayList[n+1];
		for(int i=0;i<edges.length;++i) {
			edges[i] = new ArrayList<>();
			rev[i] = new ArrayList<>();
			g[i] = new ArrayList<>();
		}
		long[] dis1 = new long[n+1];
		long[] disn = new long[n+1];
		
		for(int i=0;i<m;++i) {
			u[i] = sc.nextInt();
			v[i] = sc.nextInt();
			w[i] = sc.nextInt();
			edges[u[i]].add(new Edge(v[i],w[i]));
			rev[v[i]].add(new Edge(u[i],w[i]));
		}
		
		shortestPath(1,edges,dis1);
		shortestPath(n,rev,disn);
		
		for(int i=0;i<m;++i) {
			long wt = dis1[u[i]] + w[i] + disn[v[i]];
			if(wt == dis1[n]) { 
				g[u[i]].add(v[i]);
				in[v[i]]++;
			}
		}
		
		tsort();
		
		long[] dp = new long[n+1];
		int[] max = new int[n+1];
		int[] min = new int[n+1];
		
		Arrays.fill(min,n+1);
		min[n] = 0;
		dp[n] = 1;
		
		for(int i=order.size()-1;i>=0;--i) {
			int vx = order.get(i);
			for(int node : g[vx]) {
				dp[vx] = (dp[vx] + dp[node])%mod;
				max[vx] = Math.max(max[vx], 1 + max[node]);
				min[vx] = Math.min(min[vx], 1 + min[node]);
			}
		}
		
		out.print(dis1[n]+" "+dp[1]+" "+min[1]+" "+max[1]);
		
	        out.close();
	}
	
	 private static boolean tsort() {
			int n = in.length-1;
			// at any point it will contain only source vertex
			Queue<Integer> q = new LinkedList<>(); 
			for(int i=1;i<=n;++i)
				if(in[i] == 0) q.add(i); // adding all the source node with indegree 0.
			
			order = new ArrayList<>();
			while(q.size() > 0) {
				int x = q.poll();
				order.add(x);
				for(int node : g[x]) {
					if(--in[node] == 0)
						q.add(node); // adding only when it becomes source
				}
			}
			if(order.size() == n) return true;
			else return false;
			
		}

	static void shortestPath(int start,List<Edge>[] edges,long[] dis) {
        PriorityQueue<Edge> queue = new PriorityQueue<>();
        boolean[] visited = new boolean[dis.length];
        Arrays.fill(dis, Long.MAX_VALUE/10);
        dis[start] = 0;
 
        queue.add(new Edge(start, 0));
        while(!queue.isEmpty()) {
            Edge curr = queue.poll();
            if(visited[curr.to]) continue;
            visited[curr.to] = true;
            for(Edge e : edges[curr.to]) {
                if(visited[e.to]) continue;
                if(curr.weight + e.weight < dis[e.to]) {
                    dis[e.to] = curr.weight + e.weight;
                    queue.add(new Edge(e.to, dis[e.to]));
                }
            }
        }
    }
	
	  static class Edge implements Comparable<Edge> {
	        int to;
	        long weight;

	        public Edge(int a, long b) {
	            to = a;
	            weight = b;

	        }
	        @Override
	        public int compareTo(Edge o) {
	            return Long.compare(weight, o.weight);
	        }
	    }

}


