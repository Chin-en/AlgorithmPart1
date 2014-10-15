public class PercolationStats {
   
   private int size;
   private int times;
   private double[] result;
   
   // perform T independent computational experiments on an N-by-N grid
   public PercolationStats(int N, int T) {
       if ((N <= 0) || (T <= 0)) {
           throw new IllegalArgumentException();
       }
       size = N;
       times = T;
       result = new double[T];
       for (int i = 0; i < T; i++) {
           Percolation exp = new Percolation(N);
           boolean[] record = new boolean[N*N];
           while ((!exp.percolates())) {
               int x = StdRandom.uniform(1, N+1);
               int y = StdRandom.uniform(1, N+1);
               if (record[xyTo1D(x, y)])
                   continue;
               exp.open(x, y);
               record[xyTo1D(x, y)] = true;
           }
           double cnt = 0;
           for (int j = 0; j < record.length; j++)
               if (record[j]) cnt++;
           result[i] = cnt / record.length;
       }
   }
   
    private int xyTo1D(int x, int y) {
        return (x-1)*size + (y-1);
    }
       
   // sample mean of percolation threshold    
   public double mean() {
       return StdStats.mean(result, 0, result.length-1);
   }
   
   // sample standard deviation of percolation threshold    
   public double stddev() {
       return StdStats.stddev(result);
   }
       
   // returns lower bound of the 95% confidence interval
   public double confidenceLo() {
       return StdStats.mean(result, 0, result.length-1) - 1.96*StdStats.stddev(result)/Math.sqrt(times);
   }
   
   // returns upper bound of the 95% confidence interval
   public double confidenceHi() {
       return StdStats.mean(result, 0, result.length-1) + 1.96*StdStats.stddev(result)/Math.sqrt(times);
   }
       
   // test client, described below
   public static void main(String[] args) {
       int n = Integer.parseInt(args[0]);
       int t = Integer.parseInt(args[1]);
       PercolationStats exp = new PercolationStats(n, t);
       System.out.println("mean                    = " + String.valueOf(exp.mean()));
       System.out.println("stddev                  = " + String.valueOf(exp.stddev()));
       System.out.println("95% confidence interval = " + String.valueOf(exp.confidenceLo()) + ", " + String.valueOf(exp.confidenceHi()));
   }
}