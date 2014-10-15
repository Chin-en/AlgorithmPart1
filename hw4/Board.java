public class Board {
    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    private int[][] blocks;
    private int size;
    
    public Board(int[][] tiles) {
        this.size = tiles.length;
        this.blocks = new int[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.blocks[i][j] = tiles[i][j];
    }
    
    // board dimension N
    public int dimension() {
        return size;
    }
    
    // number of blocks out of place
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0)
                    continue;
                if (blocks[i][j] != transform(i, j))
                    cnt++;
            }
        }
        return cnt;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int cnt = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0)
                    continue;
                int actualI = (blocks[i][j]-1) / size;
                int actualJ = (blocks[i][j]-1) % size;
                if (i - actualI < 0)
                    cnt += (i - actualI)*(-1);
                else
                    cnt += (i - actualI);
                if (j - actualJ < 0)
                    cnt += (j - actualJ)*(-1);
                else
                    cnt += (j - actualJ);
            }
        }
        return cnt;
    }
    
    private int transform(int i, int j) {
        return i*size + (j+1);
    }
    
    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0)
                    continue;
                if (blocks[i][j] != transform(i, j))
                    return false;
            }
        }
        return true;
    }
    
    // a board obtained by exchanging two adjacent blocks in the same row
    public Board twin() {
        int[][] tmp = new int[size][size];
        boolean change = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tmp[i][j] = blocks[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if ((tmp[i][j] != 0) && (tmp[i][j+1] != 0)) {
                    swap(tmp, i, j, i, j+1);
                    Board twin = new Board(tmp);
                    return twin;
                }   
            }
        }
        return null;
    }
    
    // does this board equal y? 
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        if (this.size != that.size)
            return false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (this.blocks[i][j] != that.blocks[i][j]) 
                    return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
        Stack<Board> next = new Stack<Board>();
        int posI = 0, posJ = 0;
        int[][] tmp = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (blocks[i][j] == 0) {
                    posI = i;
                    posJ = j;
                }
                tmp[i][j] = blocks[i][j];
            }
        }
        if (isValid(posI-1, posJ)) {
            swap(tmp, posI-1, posJ, posI, posJ);
            Board nextBoard = new Board(tmp);
            next.push(nextBoard);
            swap(tmp, posI, posJ, posI-1, posJ);
        }
        if (isValid(posI, posJ-1)) {
            swap(tmp, posI, posJ-1, posI, posJ);
            Board nextBoard = new Board(tmp);
            next.push(nextBoard);
            swap(tmp, posI, posJ, posI, posJ-1);
        }
        if (isValid(posI, posJ+1)) {
            swap(tmp, posI, posJ+1, posI, posJ);
            Board nextBoard = new Board(tmp);
            next.push(nextBoard);
            swap(tmp, posI, posJ, posI, posJ+1);
        }
        if (isValid(posI+1, posJ)) {
            swap(tmp, posI+1, posJ, posI, posJ);
            Board nextBoard = new Board(tmp);
            next.push(nextBoard);
            swap(tmp, posI, posJ, posI+1, posJ);
        }
        return next;
    }
    private void swap(int[][] mat, int a, int b, int c, int d) {
        int tmp = mat[a][b];
        mat[a][b] = mat[c][d];
        mat[c][d] = tmp;
    }
    
    private boolean isValid(int i, int j) {
        if ((i < 0) || (j < 0) || (i >= size) || (j >= size))
            return false;
        return true;
    }
    
    // string representation of the board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
 
}