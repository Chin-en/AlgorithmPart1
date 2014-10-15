import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] pointList = new Point[N];
        //Point[][] ansList = new Point[2*N][N];
        //int cntAns = 0;
        
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            pointList[i] = new Point(x, y);
            pointList[i].draw();
        }
        
        for (int i = 0; i < N; i++) {
            /*
            boolean already = false;
            for (int j = 0; j < cntAns; j++) {
                if (pointList[i].compareTo(ansList[j]) == 0)
                    already = true;
            }
            if (already)
                continue;
            */
            Point[] tmp = new Point[N];
            for (int j = 0; j < N; j++) 
                tmp[j] = pointList[j];
            
            Arrays.sort(tmp, 0, tmp.length, pointList[i].SLOPE_ORDER);
            int idx = 1;
            int acc = 1;
            //for (int j = 0; j < N; j++)
            //    StdOut.print(pointList[i].slopeTo(tmp[j]) + " ");
            while (idx < tmp.length) {
                if (pointList[i].slopeTo(tmp[idx-1]) == pointList[i].slopeTo(tmp[idx])) {
                    acc++;
                    idx++;
                }
                else {
                    if (acc >= 3) {
                        Point[] ans = new Point[acc+1];
                        for (int k = 0; k < acc; k++) {
                            //ansList[cntAns] = tmp[idx-k-1];
                            //cntAns++;
                            ans[k] = tmp[idx-k-1];
                        }
                        ans[acc] = pointList[i];
                        boolean order = true;
                        for (int k = 0; k < acc; k++) {
                            if (ans[acc].compareTo(ans[k]) > 0) {
                                order = false;
                                break;
                            }
                        }
                        //ansList[cntAns] = pointList[i];
                        //Arrays.sort(ans);
                        //for (int k = 0; k < cntAns; k++)
                        //    if ((ansList[k][0].compareTo(ans[0]) == 0) && (ansList[k][1].compareTo(ans[1]) == 0))
                        //            already =true;
                        //StdOut.println(pointList[i].toString());
                        if (order) {
                            Arrays.sort(ans);
                            for (int k = 0; k < ans.length; k++) {
                                //ansList[cntAns][k] = ans[k];
                                if (k != ans.length - 1)
                                    StdOut.print(ans[k].toString() + " -> ");
                                else
                                    StdOut.println(ans[k].toString());
                            }
                            ans[0].drawTo(ans[ans.length-1]);
                            //cntAns++;
                        }
                    }
                    acc = 1;
                    idx++;
                }
            }
            if (acc >= 3) {
                Point[] ans = new Point[acc+1];
                for (int k = 0; k < acc; k++) {
                    //ansList[cntAns] = tmp[idx-k-1];
                    //cntAns++;
                    ans[k] = tmp[idx-k-1];
                }
                ans[acc] = pointList[i];
                boolean order = true;
                for (int k = 0; k < acc; k++) {
                    if (ans[acc].compareTo(ans[k]) > 0) {
                        order = false;
                        break;
                    }
                }
                //ansList[cntAns] = pointList[i];
                //Arrays.sort(ans);
                //for (int k = 0; k < cntAns; k++)
                //    if ((ansList[k][0].compareTo(ans[0]) == 0) && (ansList[k][1].compareTo(ans[1]) == 0))
                //    already =true;
                //StdOut.println(pointList[i].toString());
                if (order) {
                    Arrays.sort(ans);
                    for (int k = 0; k < ans.length; k++) {
                        //ansList[cntAns][k] = ans[k];
                        if (k != ans.length - 1)
                            StdOut.print(ans[k].toString() + " -> ");
                        else
                            StdOut.println(ans[k].toString());
                    }
                    ans[0].drawTo(ans[ans.length-1]);
                    //cntAns++;
                }
            }
        }
    }
}