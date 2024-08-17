import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> dq = new RandomizedQueue<String>();

        while(!StdIn.isEmpty()) {
            dq.enqueue(StdIn.readString());
        }

        for (int i = 0; i < k; i++) {
            StdOut.println(dq.dequeue());
        }
    }
}