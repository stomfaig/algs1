import java.lang.Math;
import java.util.ArrayList;

public class Board {

    private final int n;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }
                                           
    // string representation of this board
    public String toString() {
        String repr = "";
        repr += this.n;
        for (int i = 0; i < n; i++) {
            repr += "\n";
            for (int j = 0; j < n; j++) {
                repr += " " + this.tiles[i][j];
            }
        }
        return repr;
    }

    // board dimension n
    public int dimension() {
        return this.n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != (i * this.n + j + 1) % (n * n)) {
                    hamming++;
                }
            }
        }
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                int correct_j_coord = (tiles[i][j]-1) % n;
                int correct_i_coord = (tiles[i][j] - 1 - correct_j_coord) / n;
                manhattan += Math.abs(correct_i_coord - i) + Math.abs(correct_j_coord - j);
            }
        }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y? this is currently not working...
    public boolean equals(Object y) {
        // self check
        if (this == y)
            return true;
        // null check
        if (y == null)
            return false;
        // type check and cast
        if (getClass() != y.getClass())
            return false;
        Board other = (Board) y;
        // field comparison
        if (this.n != other.n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != other.tiles[i][j]) return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int[][] copy = new int[this.n][this.n];
        int zero_i = 0, zero_j = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) { zero_i = i; zero_j = j; }
                copy[i][j] = tiles[i][j];
            }
        }
        
        int i = zero_i, j = zero_j;

        if (i > 0) {
            copy[i][j] = copy[i-1][j];
            copy[i-1][j] = 0;
            neighbors.add(new Board(copy));
            copy[i-1][j] = copy[i][j];
            copy[i][j] = 0;
        }
        if (i < this.n-1) {
            copy[i][j] = copy[i+1][j];
            copy[i+1][j] = 0;
            neighbors.add(new Board(copy));
            copy[i+1][j] = copy[i][j];
            copy[i][j] = 0;
        }
        if (j > 0) {
            copy[i][j] = copy[i][j-1];
            copy[i][j-1] = 0;
            neighbors.add(new Board(copy));
            copy[i][j-1] = copy[i][j];
            copy[i][j] = 0;
        }
        if (j < this.n-1) {
            copy[i][j] = copy[i][j+1];
            copy[i][j+1] = 0;
            neighbors.add(new Board(copy));
            copy[i][j+1] = copy[i][j];
            copy[i][j] = 0;
        }
        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] copy = new int[this.n][this.n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }

        // find a pair of tiles in a triangle
        if(tiles[0][0] == 0) {
            int temp = copy[1][0];
            copy[1][0] = copy[0][1];
            copy[0][1] = temp;
        } else if (tiles[0][1] == 0) {
            int temp = copy[1][0];
            copy[1][0] = copy[0][0];
            copy[0][0] = temp;
        } else {
            int temp = copy[0][1];
            copy[0][1] = copy[0][0];
            copy[0][0] = temp;
        }
        
        return new Board(copy);
    }

    // unit testing (not graded)
    public static void main(String[] args) { ; }

}