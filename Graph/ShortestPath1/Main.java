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
	  
	static ArrayList<Edge>[] adj;
	static long[] dist;
	static int n, m;

	public static void main(String[] args) throws IOException{

	
		 n = sc.nextInt();
	        m = sc.nextInt();
	        adj = new ArrayList[n];
	        dist = new long[n];
	        for(int i = 0; i < n; i++) adj[i] = new ArrayList<>();
	        for(int i = 0; i < m; i++) {
	            int a = sc.nextInt()-1, b = sc.nextInt()-1;
	            long w = sc.nextLong();
	            adj[a].add(new Edge(b, w));
	        }
	        shortestPath(0);
	        for(long x : dist) out.print(x+" ");
	        
		out.close();
		

	}
	
	 static void shortestPath(int start) {
	        PriorityQueue<Edge> queue = new PriorityQueue<>();
	        boolean[] visited = new boolean[n];
	        Arrays.fill(dist, Long.MAX_VALUE);
	        dist[start] = 0;
	        queue.add(new Edge(start, 0));
	        while(!queue.isEmpty()) {
	            Edge curr = queue.poll();
	            if(visited[curr.to]) continue;
	            visited[curr.to] = true;
	            for(Edge e : adj[curr.to]) {
	                if(visited[e.to]) continue;
	                if(curr.weight + e.weight < dist[e.to]) {
	                    dist[e.to] = curr.weight + e.weight;
	                    queue.add(new Edge(e.to, dist[e.to]));
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
