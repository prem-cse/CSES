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
        int[] a = new int[n];
        for(int i=0;i<n;++i) a[i] = sc.nextInt();
        int[] found = new int[n];
        Arrays.fill(found,1);
        SegmentTree seg = new SegmentTree(found,n);
        for(int i=0;i<n;++i){
            int idx = sc.nextInt();
            int realIdx = seg.getIdx(found,0,n-1,idx);
            seg.update(found,realIdx,0);
            out.print(a[realIdx]+" ");
        }

        out.close();

    }

    public static class SegmentTree
    {
        int[] seg;

        SegmentTree(int[] a, int n) {
            seg = new int[4*n];
            buildTree(a,0,n-1,1);
        }

        public void buildTree(int[] a, int start, int end, int i) {
            if(start == end) {
                seg[i] = a[start];
                return;
            }
            int mid = start +(end-start)/2;
            buildTree(a,start,mid,2*i);
            buildTree(a,mid+1,end,2*i+1);

            seg[i] = seg[2*i] + seg[2*i+1];

        }

        public int query(int[] a, int l,int r) {
            return query(0,a.length-1,1,l,r);
        }

        public int query(int m, int mid2, int i, int l, int r) {
            // OUTSIDE
            if(m>r || mid2<l)
                return 0; // depend on q
            //INSIDE
            if(m>=l && mid2<=r)
                return seg[i];
            // PARTIAL
            int mid = m+(mid2-m)/2;
            int ansl = query(m,mid,2*i,l,r);
            int ansr = query(mid+1,mid2,2*i+1,l,r);

            return ansl+ansr;

        }

        public void update(int[] a,int idx,int ans) {
            updateTree(a,0,a.length-1,1,idx,ans);
        }
        public void updateTree(int[] a,int start, int end, int i, int idx, int ans) {
            if(start == end) {
                a[idx] = ans;
                seg[i] = ans;
                return;
            }

            int mid = start+(end-start)/2;
            if(idx>mid)
                updateTree(a,mid+1,end,2*i+1,idx,ans);
            else updateTree(a,start,mid,2*i,idx,ans);

            seg[i] = seg[2*i] + seg[2*i+1];

        }

        public int getIdx(int[] a,int l,int r,int x) {
            while (l <= r){
                int mid = (l+r)>>1;
                long preSum = query(a,0,mid);
                if (preSum >= x)
                    r = mid-1;
                else
                    l = mid+1;
            }
            return l;
        }

    }


}
