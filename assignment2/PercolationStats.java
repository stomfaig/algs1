import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

import java.lang.Math;

public class PercolationStats {

    private int N, T;
    private double[] p;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        N = n; T = trials;

        if (N <= 0 || T <= 0) throw new IllegalArgumentException();

        p = new double[T];

        for (int t = 0; t < T; t++) {
            Percolation percolation = new Percolation(n);
            
            while (!percolation.percolates()) {
                int row = StdRandom.uniformInt(n) + 1;
                int col = StdRandom.uniformInt(n) + 1;

                percolation.open(row, col);
            }

            p[t] = (double)(percolation.numberOfOpenSites()) / (double)(n*n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(p);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(p);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double s = stddev();
        return mean - 1.96 * s / Math.sqrt(T);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double s = stddev();
        return mean + 1.96 * s / Math.sqrt(T);
    }

   // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, t);

        StdOut.println("mean                    = "+ ps.mean());
        StdOut.println("stddev                  = "+ ps.stddev());
        StdOut.println("95% confidence interval = ["+ ps.confidenceLo() + ", "+ ps.confidenceHi() +"]");
   }

}