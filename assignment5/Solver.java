import edu.princeton.cs.algs4.MinPQ;

public class Solver {

    private class BoardWithData implements Comparable<BoardWithData> {
        public Board board;
        public ArrayList<int> moves; 

        public int compareTo(Point that) {
            if (this.hamming() < that.hamming()) return -1;
            if (this.hamming() > that.hamming()) return 1;
            return 0;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        if (initial == null) throw new IllegalArgumentException();

        MinPQ<Board> pq = new MinPQ<Board>();

        BoardWithData initialWithData = new BoardWithData();
        initialWithData.board = initial;
        initialWithData.moves = new ArrayList<int>();

        while(!pq.isEmpty()) {
            Board board = pq.delMin();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable()

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()

    // test client (see below) 
    public static void main(String[] args)

}