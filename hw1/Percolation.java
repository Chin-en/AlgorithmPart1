public class Percolation {
    
    private WeightedQuickUnionUF grid;
    private int size;
    private int[] status; //0 blocked, 1 block/top, 2 block/bot, 
    //3 block/top/bot, 4 open, 5 open/top, 6 open/bot 7 open/top/bot
    private boolean checkPercolate;
    
    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        } 
        grid = new WeightedQuickUnionUF(N*N);
        size = N;
        checkPercolate = false;
        status = new int[N*N];
        if (N == 1) 
            status[0] = 3;
        else {
            for (int i = 0; i < N; i++) {
                status[i] = 1;
                status[N*N-1-i] = 2;
            }
        }
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        // special case
        if (size == 1) {
            checkPercolate = true;
            status[0] = 7;
            return;
        }
        
        int[] tmp = new int[5];
        boolean checkIsolate = true;
        boolean checkConnectTop = false;
        boolean checkConnectBottom = false;

        if (isValid(i-1, j)) {
            tmp[0] = grid.find(xyTo1D(i-1, j));
            if (status[tmp[0]] > 3) {
                grid.union(xyTo1D(i-1, j), xyTo1D(i, j));
                checkIsolate = false;
                if (status[tmp[0]] == 5)
                    checkConnectTop = true;
                if (status[tmp[0]] == 6)
                    checkConnectBottom = true;
                if (status[tmp[0]] == 7) {
                    checkConnectTop = true;
                    checkConnectBottom = true;
                }
            }
        }
        if (isValid(i, j-1)) {
            tmp[1] = grid.find(xyTo1D(i, j-1));
            if (status[tmp[1]] > 3) {
                grid.union(xyTo1D(i, j-1), xyTo1D(i, j));
                checkIsolate = false;
                if (status[tmp[1]] == 5)
                    checkConnectTop = true;
                if (status[tmp[1]] == 6)
                    checkConnectBottom = true;
                if (status[tmp[1]] == 7) {
                    checkConnectTop = true;
                    checkConnectBottom = true;
                }
            }
        }
        if (isValid(i, j+1)) {
            tmp[2] = grid.find(xyTo1D(i, j+1));
            if (status[tmp[2]] > 3) {
                grid.union(xyTo1D(i, j+1), xyTo1D(i, j));
                checkIsolate = false;
                if (status[tmp[2]] == 5)
                    checkConnectTop = true;
                if (status[tmp[2]] == 6)
                    checkConnectBottom = true;
                if (status[tmp[2]] == 7) {
                    checkConnectTop = true;
                    checkConnectBottom = true;
                }
            }
        }
        if (isValid(i+1, j)) {
            tmp[3] = grid.find(xyTo1D(i+1, j));
            if (status[tmp[3]] > 3) {
                grid.union(xyTo1D(i+1, j), xyTo1D(i, j));
                checkIsolate = false;
                if (status[tmp[3]] == 5)
                    checkConnectTop = true;
                if (status[tmp[3]] == 6)
                    checkConnectBottom = true;
                if (status[tmp[3]] == 7) {
                    checkConnectTop = true;
                    checkConnectBottom = true;
                }
            }
        }
        tmp[4] = grid.find(xyTo1D(i, j));
        if (checkIsolate) {
            if (tmp[4] < size)
                status[tmp[4]] = 5;
            else if (tmp[4] >= size*size - size)
                status[tmp[4]] = 6;
            else
                status[tmp[4]] = 4;
        }
        else {
            int position = xyTo1D(i, j); //look up position
            if (position < size) { //top row
                if (checkConnectTop && checkConnectBottom) {
                    checkPercolate = true;
                    status[tmp[4]] = 7;
                }
                else if (checkConnectTop)
                    status[tmp[4]] = 5;
                else if (checkConnectBottom) {
                    checkPercolate = true;
                    status[tmp[4]] = 7;
                }
                else 
                    status[tmp[4]] = 5;
            }
            else if (position >= size*size - size) { //bottom row
                if (checkConnectTop && checkConnectBottom) {
                    checkPercolate = true;
                    status[tmp[4]] = 7;
                }
                else if (checkConnectTop) {
                    status[tmp[4]] = 7;
                    checkPercolate = true;
                }
                else if (checkConnectBottom)                     
                    status[tmp[4]] = 6;
                else 
                    status[tmp[4]] = 6;
            }
            else { //mid 
                if (checkConnectTop && checkConnectBottom) {
                    checkPercolate = true;
                    status[tmp[4]] = 7;
                }
                else if (checkConnectTop)
                    status[tmp[4]] = 5;
                else if (checkConnectBottom)
                    status[tmp[4]] = 6;
                else 
                    status[tmp[4]] = 4;
            }
        }
    }
    
    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        if (status[grid.find(xyTo1D(i, j))] >= 4)
            return true;
        return false;
    }
    
    // is site (row i, column j) full?
    public boolean isFull(int i, int j) {
        if ((i <= 0) || (j <= 0) || (i > size) || (j > size)) {
            throw new IndexOutOfBoundsException();
        }
        if (status[grid.find(xyTo1D(i, j))] == 5 || status[grid.find(xyTo1D(i, j))] == 7)
            return true;
        return false;
    }
    
    // does the system percolate?
    public boolean percolates() {
        return checkPercolate;
    }
    
    // transform 2D to 1D
    private int xyTo1D(int x, int y) {
        return (x-1)*size + (y-1);
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