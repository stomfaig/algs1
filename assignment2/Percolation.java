import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {

    private int N, numOpen;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private boolean percolates;

    private int checkIndex(int row, int col) {
        if (row < 1 || row > N || col < 1 || col > N) throw new IllegalArgumentException();
        else return (row-1) * N + col;
    }

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if(n <= 0) throw new IllegalArgumentException();
        N = n;
        numOpen=0;
        uf = new WeightedQuickUnionUF(2 * N * N + 3);
        grid = new boolean[n][n];
        for (int i = 0; i<N; i++) {
            for (int j = 0; j<N; j++) {
                grid[i][j] = false;
            }
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int idx = checkIndex(row, col);
        if (grid[row-1][col-1]) return;

        grid[row-1][col-1] = true;
        numOpen++;

        if (row == 1) {
            uf.union(0, idx);
            uf.union(0+N*N+1, idx+N*N+1);
        } else if(grid[row-2][col-1]) {
            uf.union(idx, checkIndex(row-1, col));
            uf.union(idx+N*N+1, checkIndex(row-1, col)+N*N+1);
        }

        if (row == N) {
            uf.union(idx+N*N+1, 2*N*N+2);
        } else if (grid[row][col-1]) {
            uf.union(idx, checkIndex(row+1, col));
            uf.union(idx+N*N+1, checkIndex(row+1, col)+N*N+1);
        }

        if (col != 1 && grid[row-1][col-2]) {
            uf.union(idx, checkIndex(row, col-1));
            uf.union(idx+N*N+1, checkIndex(row, col-1)+N*N+1);
        }

        if (col != N && grid[row-1][col]) {
            uf.union(idx, checkIndex(row, col+1));
            uf.union(idx+N*N+1, checkIndex(row, col+1)+N*N+1);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIndex(row, col);
        return grid[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        return uf.find(0) == uf.find(checkIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOpen;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(N*N+1) == uf.find(2 * N*N+2);
    }

    // test client (optional)
    //public static void main(String[] args)
}