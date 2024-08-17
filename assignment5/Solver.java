import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.LinkedStack;
import java.util.ArrayList;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {

    private boolean solvable;
    private int moves;
    private LinkedStack<Board> solution;

    private class Node {
        public Board board;
        public Node next;
    }

    private class BoardWithData implements Comparable<BoardWithData> {
        public Board board;
        public BoardWithData parent;
        public int moves;
        public Integer priority;

        public int priority() {
            if (priority == null) {
                priority = this.board.manhattan() + moves;
            }
            return priority;
        }

        public int compareTo(BoardWithData that) {
            int prThis = this.priority(), prThat = that.priority();
            if (prThis < prThat) return -1;
            if (prThis > prThat) return 1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        moves = -1;

        MinPQ<BoardWithData> pq = new MinPQ<BoardWithData>();
        MinPQ<BoardWithData> pqTwin = new MinPQ<BoardWithData>();

        BoardWithData initialWithData = new BoardWithData();
        initialWithData.board = initial;
        initialWithData.parent = null;
        initialWithData.moves = 0;
        pq.insert(initialWithData);

        BoardWithData twinWithData = new BoardWithData();
        twinWithData.board = initial.twin();
        twinWithData.moves = 0;
        pqTwin.insert(twinWithData);


        while(!pq.isEmpty() && !pqTwin.isEmpty()) {
            BoardWithData bwd = pq.delMin();
            Board bd = bwd.board;

            if (bd.isGoal()) {
                solvable = true;
                moves = bwd.moves;
                
                solution = new LinkedStack<Board>();
                solution.push(bwd.board);
                while(bwd.parent != null) {
                    bwd = bwd.parent;
                    solution.push(bwd.board);
                } 
                break;
            }

            Iterable<Board> neighbors = bd.neighbors();
            for (Board nb : neighbors) {
                if (bwd.parent != null)
                if (nb.equals(bwd.parent.board)) continue;

                BoardWithData nbd = new BoardWithData();
                nbd.board = nb;
                nbd.parent = bwd;
                nbd.moves = bwd.moves + 1;
                pq.insert(nbd);
            }

            BoardWithData bwdTwin = pqTwin.delMin();
            Board bd_twin = bwdTwin.board;
            if (bd_twin.isGoal()) { solvable = false; break; }

            Iterable<Board> neighbors_twin = bd_twin.neighbors();
            for (Board nb : neighbors_twin) {
                if (bwdTwin.parent != null)
                if (nb.equals(bwdTwin.parent.board)) continue;

                BoardWithData nbd = new BoardWithData();
                nbd.board = nb;
                nbd.moves = bwdTwin.moves + 1;
                pqTwin.insert(nbd);
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()  { return moves; }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        return solution;
    }

    // test client (see below) 
    public static void main(String[] args) {

    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] tiles = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            tiles[i][j] = in.readInt();
    Board initial = new Board(tiles);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}

}