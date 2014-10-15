import java.util.Comparator;

public class Point implements Comparable<Point> {
    
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlopeOrder();
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate
    
    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }
    
    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    // slope between this point and that point
    public double slopeTo(Point that) {
        if (x == that.x && y == that.y)
            return Double.NEGATIVE_INFINITY;
        else if (x == that.x)
            return Double.POSITIVE_INFINITY;
        else if (y == that.y)
            return 0;
        else {
            double x0 = x;
            double y0 = y;
            double x1 = that.x;
            double y1 = that.y;
            return (y1 - y0)/(x1 - x0);
        }
    }
    
    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    
    public int compareTo(Point that) {
        if (y < that.y)
            return -1;
        else if (y > that.y)
            return 1;
        else {
            if (x < that.x)
                return -1;
            else if (x > that.x)
                return 1;
            else
                return 0;
        }
    }
    
    private class BySlopeOrder implements Comparator<Point> {
        public int compare(Point v, Point w) {
            double slopeV = slopeTo(v);
            double slopeW = slopeTo(w);
            if (slopeV < slopeW)
                return -1;
            else if (slopeV > slopeW)
                return 1;
            else
                return 0;
        }
    }
    
    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
    
    // unit test
    public static void main(String[] args) {
    }
}