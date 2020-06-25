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
	static List<Integer>[] edges;
 
	public static void main(String[] args) throws IOException{
 
		  int n = sc.nextInt();
	        int m = sc.nextInt();
 
	        edges = new ArrayList[n + 1];
	        for (int i = 0; i < edges.length; ++i) edges[i] = new ArrayList<>();
 
	        for (int i = 0; i < m; ++i) {
	            int u = sc.nextInt();
	            int v = sc.nextInt();
	            edges[u].add(v);
	            edges[v].add(u);
	        }
 
 
	        int[] col = new int[n + 1];
	        Arrays.fill(col, -1);
 
	        boolean is_bipartite = true;
	        Queue<Integer> q = new ArrayDeque<>();
	        for (int st = 1; st <= n && is_bipartite; ++st) {
	            if (col[st] == -1) {
	                q.add(st);
	                col[st] = 0;
	                while (q.size() > 0) {
	                    int v = q.poll();
	                    for (int node : edges[v]) {
	                        if (col[node] == -1) {
	                            col[node] = col[v] ^ 1;
	                            q.add(node);
	                        } else {
	                            is_bipartite &= col[node] != col[v];
	                        }
	                    }
	                }
	            }
	        }
 
	        if (is_bipartite) {
	            for (int i = 1; i <= n; ++i) out.print((col[i] + 1) + " ");
	        } else out.println("IMPOSSIBLE");
	        out.close();
	    }
	
}
