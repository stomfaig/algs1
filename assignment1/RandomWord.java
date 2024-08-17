import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {

    public static void main(String[] args) {
        String champion = null;
        float i = 1;
        while(!StdIn.isEmpty()) {
            String candidate = StdIn.readString();
            if (StdRandom.bernoulli(1.0/i)) {
                champion = candidate;
            }
            i = i + 1;
        }
        StdOut.println(champion);
    
    }
}