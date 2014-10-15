public class Solver {
    // find a solution to the initial board (using the A* algorithm)
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> pqTwin;
    private int move = 0;
    private int isSolve = 0; //0 not solve, 1 solve byself, 2 solve by twin 
    private Stack solution;
    
    public Solver(Board initial) {
        pq = new MinPQ<SearchNode>();
        pqTwin = new MinPQ<SearchNode>();
        solution = new Stack<Board>();
        Board initialTwin = initial.twin();
        SearchNode init = new SearchNode(initial, null, 0);
        SearchNode initTwin = new SearchNode(initialTwin, null, 0);
        pq.insert(init);
        pqTwin.insert(initTwin);
        solve();
    }
    
    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private SearchNode prev;
        private int move;
        private int manhattan;
        private int priority;
        
        public SearchNode(Board now, SearchNode prev, int move) {
            this.board = now;
            this.prev = prev;
            this.move = move;
            this.manhattan = now.manhattan();
            this.priority = manhattan + move;
        }
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority)
                return -1;
            else if (this.priority == that.priority)
                return 0;
            else 
                return 1;
        }
    }
    
    private void solve() {
        //int cnt = 0;
        while (isSolve == 0) {
            //cnt += 1;
            //StdOut.println(cnt);
            SearchNode now = pq.delMin();
            if (now.board.isGoal()) {
                this.isSolve = 1;
                this.move = now.move;
                this.solution.push(now.board);
                while (now.prev != null) {
                    now = now.prev;
                    this.solution.push(now.board);
                }
                break;
            }
            else {
                for (Board neighbor: now.board.neighbors()) {
                    SearchNode neighborNode = new SearchNode(neighbor, now, now.move+1);
                    if (now.prev == null) {
                        pq.insert(neighborNode);
                    }
                    else if (!(neighbor.equals(now.prev.board))) {
                        pq.insert(neighborNode);
                    }
                }
            }
            SearchNode nowTwin = pqTwin.delMin();
            if (nowTwin.board.isGoal()) {
                this.isSolve = 2;
                break;
            }
            else {
                for (Board neighbor: nowTwin.board.neighbors()) {
                    SearchNode neighborNode = new SearchNode(neighbor, nowTwin, now.move+1);
                    if (nowTwin.prev == null) {
                        pqTwin.insert(neighborNode);
                    }
                    else if (!(neighbor.equals(nowTwin.prev.board))) {
                        pqTwin.insert(neighborNode);
                    }
                }
            }
        }
    }
    
    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolve == 1;
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves() {
        if (isSolve <= 1)
            return this.move;
        else
            return -1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if (isSolve <= 1)
            return this.solution;
        else
            return null;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                blocks[i][j] = in.readInt();
        
        Board initial = new Board(blocks);
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