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
 
	static int mod = (int)1e9+7;
	
	public static void main(String[] args) throws IOException {
	    
		 int n = sc.nextInt();
	        int q = sc.nextInt();
	        long[] a = new long[n];
	        for (int i = 0; i < n; ++i) a[i] = sc.nextLong();
	        SegmentTree seg = new SegmentTree(a, n);
	        while (q-- > 0) {
	            int t = sc.nextInt();
	            if (t == 1) {
	                int k = sc.nextInt() - 1;
	                int u = sc.nextInt();
	                seg.update(a, k, u);
	                a[k] = u;
	            } else {
	                int l = sc.nextInt() - 1;
	                int r = sc.nextInt() - 1;
	                out.println(seg.query(a, l, r));
	            }
	        }
	        out.close();
	    }
 
 
	    public static class SegmentTree
	    {
	        long[] seg;
 
	        SegmentTree(long[] a, int n) {
	            seg = new long[4*n];
	            buildTree(a,0,n-1,1);
	        }
 
	        public void buildTree(long[] a, int start, int end, int i) {
	            if(start == end) {
	                seg[i] = a[start];
	                return;
	            }
	            int mid = start +(end-start)/2;
	            buildTree(a,start,mid,2*i);
	            buildTree(a,mid+1,end,2*i+1);
 
	            seg[i] = Math.min(seg[2*i] , seg[2*i+1]);
 
	        }
 
	        public long query(long[] a, int l,int r) {
	            return query(0,a.length-1,1,l,r);
	        }
 
	        public long query(long m, long mid2, int i, int l, int r) {
	            // OUTSIDE
	            if(m>r || mid2<l)
	                return Integer.MAX_VALUE; // depend on q
	            //INSIDE
	            if(m>=l && mid2<=r)
	                return seg[i];
	            // PARTIAL
	            long mid = m+(mid2-m)/2;
	            long ansl = query(m,mid,2*i,l,r);
	            long ansr = query(mid+1,mid2,2*i+1,l,r);
 
	            return Math.min(ansl,ansr);
 
	        }
 
	        public void update(long[] a,int idx,long ans) {
	            updateTree(a,0,a.length-1,1,idx,ans);
	        }
	        public void updateTree(long[] a,int start, int end, int i, int idx, long ans) {
	            if(start == end) {
	                a[idx] = ans;
	                seg[i] = ans;
	                return;
	            }
 
	            int mid = start+(end-start)/2;
	            if(idx>mid)
	                updateTree(a,mid+1,end,2*i+1,idx,ans);
	            else updateTree(a,start,mid,2*i,idx,ans);
 
	            seg[i] = Math.min(seg[2*i], seg[2*i+1]);
 
	        }
 
	    }
 
 
}
