public class Percolation {
    
    private WeightedQuickUnionUF grid;
    private int size;
    private boolean[] open;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        int a = N*N;     
        grid = new WeightedQuickUnionUF(a+2);
        size = N;
        open = new boolean[a+2];
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        open[xyTo1D(i, j)] = true;
        if (xyTo1D(i, j) <= size) {
            grid.union(0, xyTo1D(i, j));
        }
        if ((xyTo1D(i, j) <= size*size) && (xyTo1D(i, j) >= size*size-size+1)) {
            grid.union(size*size+1, xyTo1D(i, j));
        }
        if (isValid(i-1, j)) {
            if (isOpen(i-1, j))
                grid.union(xyTo1D(i-1, j), xyTo1D(i, j));
        }
        if (isValid(i, j-1)) {
            if (isOpen(i, j-1))
                grid.union(xyTo1D(i, j-1), xyTo1D(i, j));
        }
        if (isValid(i, j+1)) {
            if (isOpen(i, j+1))
                grid.union(xyTo1D(i, j+1), xyTo1D(i, j));
        }
        if (isValid(i+1, j)) {
            if (isOpen(i+1, j))
                grid.union(xyTo1D(i+1, j), xyTo1D(i, j));
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        return open[xyTo1D(i, j)];
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        return (grid.connected(0, xyTo1D(i, j)));
        //return (grid.connected(0, xyTo1D(i, j)) && open[xyTo1D(i, j)]);
    }
    
    // does the system percolate?
    public boolean percolates() {
        return grid.connected(0, size*size+1);
    }
    
    // transform 2D to 1D
    private int xyTo1D(int x, int y) {
        return (x-1)*size + y;
    }
    
    // validate indices
    private boolean isValid(int x, int y) {
        if ((x > 0) && (x < size+1) && (y > 0) && (y < size+1))
            return true;
        return false;
    }
    
    public static void main(String[] args) {
        //Percolation test = new Percolation(5);
        //System.out.println(test.xyTo1D(3,2));
        //test.open(1, 1);
        //test.open(1, 2);
        //System.out.println(test.grid.connected(test.xyTo1D(1, 1), test.xyTo1D(2, 1)));
    }
    
}