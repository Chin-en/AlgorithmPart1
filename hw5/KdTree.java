public class KdTree {
    private int N;
    private Node root;
    private Point2D ans_nearest;
    private double min_nearest;
    // construct an empty set of points
    /*
    private class RectHV {
        private final double xmin, ymin;   // minimum x- and y-coordinates
        private final double xmax, ymax;   // maximum x- and y-coordinates
        
        // construct the axis-aligned rectangle [xmin, xmax] x [ymin, ymax]
        public RectHV(double xmin, double ymin, double xmax, double ymax) {
            if (xmax < xmin || ymax < ymin) {
                throw new IllegalArgumentException("Invalid rectangle");
            }
            this.xmin = xmin;
            this.ymin = ymin;
            this.xmax = xmax;
            this.ymax = ymax;
        }
        
        // accessor methods for 4 coordinates
        public double xmin() { return xmin; }
        public double ymin() { return ymin; }
        public double xmax() { return xmax; }
        public double ymax() { return ymax; }
        
        // width and height of rectangle
        public double width()  { return xmax - xmin; }
        public double height() { return ymax - ymin; }
        
        // does this axis-aligned rectangle intersect that one?
        public boolean intersects(RectHV that) {
            return this.xmax >= that.xmin && this.ymax >= that.ymin
                && that.xmax >= this.xmin && that.ymax >= this.ymin;
        }
        
        // draw this axis-aligned rectangle
        public void draw() {
            StdDraw.line(xmin, ymin, xmax, ymin);
            StdDraw.line(xmax, ymin, xmax, ymax);
            StdDraw.line(xmax, ymax, xmin, ymax);
            StdDraw.line(xmin, ymax, xmin, ymin);
        }
        
        // distance from p to closest point on this axis-aligned rectangle
        public double distanceTo(Point2D p) {
            return Math.sqrt(this.distanceSquaredTo(p));
        }
        
        // distance squared from p to closest point on this axis-aligned rectangle
        public double distanceSquaredTo(Point2D p) {
            double dx = 0.0, dy = 0.0;
            if      (p.x() < xmin) dx = p.x() - xmin;
            else if (p.x() > xmax) dx = p.x() - xmax;
            if      (p.y() < ymin) dy = p.y() - ymin;
            else if (p.y() > ymax) dy = p.y() - ymax;
            return dx*dx + dy*dy;
        }
        
        // does this axis-aligned rectangle contain p?
        public boolean contains(Point2D p) {
            return (p.x() >= xmin) && (p.x() <= xmax)
                && (p.y() >= ymin) && (p.y() <= ymax);
        }
        
        // are the two axis-aligned rectangles equal?
        public boolean equals(Object y) {
            if (y == this) return true;
            if (y == null) return false;
            if (y.getClass() != this.getClass()) return false;
            RectHV that = (RectHV) y;
            if (this.xmin != that.xmin) return false;
            if (this.ymin != that.ymin) return false;
            if (this.xmax != that.xmax) return false;
            if (this.ymax != that.ymax) return false;
            return true;
        }
        
        // return a string representation of this axis-aligned rectangle
        public String toString() {
            return "[" + xmin + ", " + xmax + "] x [" + ymin + ", " + ymax + "]";
        }  
    }
    */
    public KdTree() {
        N = 0;
        root = null;
    }
    
    private static class Node {
        public Point2D p; // the point
        public RectHV rect; // the axis-aligned rectangle corresponding to this node
        public Node lb; // the left/bottom subtree;
        public Node rt; // the right/top subtree;
        public boolean xCoordinate;
        
        public Node(Point2D p, boolean h, RectHV rect) {
            this.p = p;
            this.lb = null;
            this.rt = null;
            this.xCoordinate = h;
            this.rect = rect;
        }
    }
    
    // is the set empty? 
    public boolean isEmpty() {
        return N == 0;
    }
    
    // number of points in the set
    public int size() {
        return N;
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        Node node = root;
        Node parent = null;
        if (node == null) {
            RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
            root = new Node(p, true, rect);
            N++;
        }
        else if (!contains(p)) {
            N++; //add one element
            // find proper position
            while (node != null) {
                parent = node;
                if (parent.xCoordinate) {
                    double cmp = p.x() - parent.p.x();
                    if (cmp < 0) {
                        node = parent.lb;
                    }
                    else {
                        node = parent.rt;
                    }
                }
                else {
                    double cmp = p.y() - parent.p.y();
                    if (cmp < 0) {
                        node = parent.lb;
                    }
                    else {
                        node = parent.rt;
                    }
                }
            }
            // put new node
            if (parent.xCoordinate) {
                double cmp = p.x() - parent.p.x();
                if (cmp < 0) {
                    RectHV rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.p.x(), parent.rect.ymax()); 
                    Node n = new Node(p, !parent.xCoordinate, rect);
                    parent.lb = n;
                }
                else {
                    RectHV rect = new RectHV(parent.p.x(), parent.rect.ymin(), parent.rect.xmax(), parent.rect.ymax()); 
                    Node n = new Node(p, !parent.xCoordinate, rect);
                    parent.rt = n;
                }
            }
            else {
                double cmp = p.y() - parent.p.y();
                if (cmp < 0) {
                    RectHV rect = new RectHV(parent.rect.xmin(), parent.rect.ymin(), parent.rect.xmax(), parent.p.y());
                    Node n = new Node(p, !parent.xCoordinate, rect);
                    parent.lb = n;
                }
                else {
                    RectHV rect = new RectHV(parent.rect.xmin(), parent.p.y(), parent.rect.xmax(), parent.rect.ymax());
                    Node n = new Node(p, !parent.xCoordinate, rect);
                    parent.rt = n;
                }
            }
        }
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (isEmpty()) return false;
        return find(root, p) != null;
    }
    
    private Node find(Node node, Point2D p) {
        if (node == null) return null;
        if (node.p.equals(p)) return node;
        
        Node n = null;
        if (node.xCoordinate) {
            double cmp = p.x() - node.p.x();
            if (cmp < 0)
                n = find(node.lb, p);
            else
                n = find(node.rt, p);
        }
        else {
            double cmp = p.y() - node.p.y();
            if (cmp < 0)
                n = find(node.lb, p);
            else
                n = find(node.rt, p);
        }
        return n;
    }
    
    // draw all points to standard draw
    public void draw() {
    }
    
    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> ans = new Stack<Point2D>();
        Stack<Node> q = new Stack<Node>();
        if (N == 0)
            return ans;
        q.push(root);
        while (!q.isEmpty()) {
            Node n = q.pop();
            if (rect.intersects(n.rect)) {
                if (rect.contains(n.p))
                    ans.push(n.p);
                if (n.lb != null) 
                    q.push(n.lb);
                if (n.rt != null)
                    q.push(n.rt);
            }
        }
        return ans;
    }
    private void find_nearest(Point2D p, Node n) {
        if (n == null)
            return;
        if (n.rect.distanceTo(p) < min_nearest) {
            double d = n.p.distanceTo(p);
            if (d < min_nearest) {
                ans_nearest = n.p;
                min_nearest = d;
            }
            if (n.xCoordinate) {
                double cmp = p.x() - n.p.x();
                if (cmp < 0) {
                    find_nearest(p, n.lb);
                    find_nearest(p, n.rt);
                }
                else {
                    find_nearest(p, n.rt);
                    find_nearest(p, n.lb);
                }
            }
            else {
                double cmp = p.y() - n.p.y();
                if (cmp < 0) {
                    find_nearest(p, n.lb);
                    find_nearest(p, n.rt);
                }
                else {
                    find_nearest(p, n.rt);
                    find_nearest(p, n.lb);
                }
            }
        }
        return;
    }
    public Point2D nearest(Point2D p) {
        if (N == 0) return null;
        min_nearest = 100;
        ans_nearest = root.p;
        find_nearest(p, root);
        return ans_nearest;
    }
    // a nearest neighbor in the set to point p; null if the set is empty 
    /*
    public Point2D nearest(Point2D p) {
        if (N == 0) return null;
        Stack<Node> q = new Stack<Node>();
        q.push(root);
        double min = p.distanceTo(root.p);
        Point2D ans = root.p;
        while (!q.isEmpty()) {
            Node n = q.pop();
            if (n.rect.distanceTo(p) < min) {
                if (p.distanceTo(n.p) < min) {
                    min = p.distanceTo(n.p);
                    ans = n.p;
                }
                if (n.xCoordinate) {
                    double cmp = p.x() - n.p.x();
                    if (cmp < 0) {
                        if (n.lb != null)
                            q.push(n.lb);
                        if (n.rt != null)
                            q.push(n.rt);
                    }
                    else {
                        if (n.rt != null)
                            q.push(n.rt);
                        if (n.lb != null)
                            q.push(n.lb);
                    }
                }
                else {
                    double cmp = p.y() - n.p.y();
                    if (cmp < 0) {
                        if (n.lb != null)
                            q.push(n.lb);
                        if (n.rt != null)
                            q.push(n.rt);
                    }
                    else {
                        if (n.rt != null)
                            q.push(n.rt);
                        if (n.lb != null)
                            q.push(n.lb);
                    }
                }
            }
        }
        return ans;
    }
    */
    
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
    }

}