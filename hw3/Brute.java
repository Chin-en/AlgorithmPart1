import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {            
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] pointList = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            pointList[i] = new Point(x, y);
            pointList[i].draw();
        }
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    for (int m = k + 1; m < N; m++) {
                        if ((pointList[i].slopeTo(pointList[j]) == pointList[i].slopeTo(pointList[k])) 
                           && (pointList[i].slopeTo(pointList[j]) == pointList[i].slopeTo(pointList[m]))) {
                            
                            Point[] ans = new Point[4];
                            ans[0] = pointList[i];
                            ans[1] = pointList[j];
                            ans[2] = pointList[k];
                            ans[3] = pointList[m];
                            Arrays.sort(ans);
                            /*
                            for (int n = 0; n < 4; n++) {
                                for (int p = n + 1; p < 4; p++) {
                                    if (ans[n].compareTo(ans[p]) > 0) {
                                        Point tmp = ans[n];
                                        ans[n] = ans[p];
                                        ans[p] = tmp;
                                    }
                                }
                            }
                            */
                            StdOut.println(ans[0].toString() + " -> " + ans[1].toString()
                                          + " -> " + ans[2].toString() + " -> " 
                                          + ans[3].toString());
                            ans[0].drawTo(ans[3]);
                        }   
                    }
                }
            }
        }
    }
}