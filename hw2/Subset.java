public class Subset {
    public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        RandomizedQueue q = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            q.enqueue(item);
        }
        int cnt = 0;
        for (Object s : q) {
            if (cnt < num) {
                StdOut.println(s);
                cnt++;
            }
            else
                break;
        }
    }
}