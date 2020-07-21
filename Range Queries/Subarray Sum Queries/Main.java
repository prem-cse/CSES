import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

 /*
    Template form : https://www.geeksforgeeks.org/range-query-largest-sum-contiguous-subarray/
 */

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
        int m = sc.nextInt();
        long[] a = new long[n];
        for(int i=0;i<n;++i) a[i] = sc.nextLong();
        tree = new node[4*n];
        for(int i=0;i<4*n;++i) tree[i] = new node();
        build(a, 0, n - 1, 0);
        while(m-->0){
            int k = sc.nextInt()-1;
            int x = sc.nextInt();
            a[k] = x;
            update(a, 0, 0, n - 1, k, x);
            out.println(Math.max(0, query(a, 0, 0, n - 1,0, n - 1).maxsum));

        }
        out.close();

    }


    static class node {
        long sum, prefixsum, suffixsum, maxsum;
    }

    static node[] tree;

    // function to build the tree
    static void build(long[] arr, int low,
                      int high, int index)
    {
        // the leaf node
        if (low == high)
        {
            tree[index].sum = arr[low];
            tree[index].prefixsum = arr[low];
            tree[index].suffixsum = arr[low];
            tree[index].maxsum = arr[low];
        }
        else
        {
            int mid = (low + high) / 2;

            // left subtree
            build(arr, low, mid, 2 * index + 1);

            // right subtree
            build(arr, mid + 1, high, 2 * index + 2);

            // parent node's sum is the summation
            // of left and right child
            tree[index].sum = tree[2 * index + 1].sum +
                    tree[2 * index + 2].sum;

            // parent node's prefix sum will be equivalent
            // to maximum of left child's prefix sum or left
            // child sum + right child prefix sum.
            tree[index].prefixsum = Math.max(tree[2 * index + 1].prefixsum,
                    tree[2 * index + 1].sum +
                            tree[2 * index + 2].prefixsum);

            // parent node's suffix sum will be equal to right
            // child suffix sum or right child sum + suffix
            // sum of left child
            tree[index].suffixsum = Math.max(tree[2 * index + 2].suffixsum,
                    tree[2 * index + 2].sum +
                            tree[2 * index + 1].suffixsum);

            // maxum will be the maximum of prefix, suffix of
            // parent or maximum of left child or right child
            // and summation of left child's suffix and right
            // child's prefix.
            tree[index].maxsum = Math.max(tree[index].prefixsum,
                    Math.max(tree[index].suffixsum,
                            Math.max(tree[2 * index + 1].maxsum,
                                    Math.max(tree[2 * index + 2].maxsum,
                                            tree[2 * index + 1].suffixsum +
                                                    tree[2 * index + 2].prefixsum))));
        }
    }

    // function to update the tree
    static void update(long[] arr, int index, int low,
                       int high, int idx, int value)
    {
        // the node to be updated
        if (low == high)
        {
            tree[index].sum = value;
            tree[index].prefixsum = value;
            tree[index].suffixsum = value;
            tree[index].maxsum = value;
        }
        else
        {
            int mid = (low + high) / 2;

            // if node to be updated is in left subtree
            if (idx <= mid)
            {
                update(arr, 2 * index + 1, low,
                        mid, idx, value);
            }

            // if node to be updated is in right subtree
            else
            {
                update(arr, 2 * index + 2, mid + 1,
                        high, idx, value);
            }

            // parent node's sum is the summation of left
            // and right child
            tree[index].sum = tree[2 * index + 1].sum +
                    tree[2 * index + 2].sum;

            // parent node's prefix sum will be equivalent
            // to maximum of left child's prefix sum or left
            // child sum + right child prefix sum.
            tree[index].prefixsum = Math.max(tree[2 * index + 1].prefixsum,
                    tree[2 * index + 1].sum +
                            tree[2 * index + 2].prefixsum);

            // parent node's suffix sum will be equal to right
            // child suffix sum or right child sum + suffix
            // sum of left child
            tree[index].suffixsum = Math.max(tree[2 * index + 2].suffixsum,
                    tree[2 * index + 2].sum +
                            tree[2 * index + 1].suffixsum);

            // maxum will be the maximum of prefix, suffix of
            // parent or maximum of left child or right child
            // and summation of left child's suffix and
            // right child's prefix.
            tree[index].maxsum = Math.max(tree[index].prefixsum,
                    Math.max(tree[index].suffixsum,
                            Math.max(tree[2 * index + 1].maxsum,
                                    Math.max(tree[2 * index + 2].maxsum,
                                            tree[2 * index + 1].suffixsum +
                                                    tree[2 * index + 2].prefixsum))));
        }
    }

    // fucntion to return answer to every type-1 query
    static node query(long[] arr, int index,
                      int low, int high, int l, int r)
    {
        // initially all the values are Integer.MIN_VALUE
        node result = new node();
        result.sum = result.prefixsum
                = result.suffixsum
                = result.maxsum = Integer.MIN_VALUE;

        // range does not lies in this subtree
        if (r < low || high < l)
        {
            return result;
        }

        // complete overlap of range
        if (l <= low && high <= r)
        {
            return tree[index];
        }

        int mid = (low + high) / 2;

        // right subtree
        if (l > mid)
        {
            return query(arr, 2 * index + 2,
                    mid + 1, high, l, r);
        }

        // left subtree
        if (r <= mid)
        {
            return query(arr, 2 * index + 1,
                    low, mid, l, r);
        }

        node left = query(arr, 2 * index + 1,
                low, mid, l, r);
        node right = query(arr, 2 * index + 2,
                mid + 1, high, l, r);

        result.sum = left.sum + right.sum;
        result.prefixsum = Math.max(left.prefixsum,
                left.sum + right.prefixsum);

        result.suffixsum = Math.max(right.suffixsum,
                right.sum + left.suffixsum);
        result.maxsum = Math.max(result.prefixsum,
                Math.max(result.suffixsum,
                        Math.max(left.maxsum,
                                Math.max(right.maxsum, left.suffixsum +
                                        right.prefixsum))));

        return result;
    }

}
