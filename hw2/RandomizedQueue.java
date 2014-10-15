import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int N;
    
    public RandomizedQueue() {     // construct an empty randomized queue
        q = (Item[]) new Object[2];
    }
    public boolean isEmpty() {     // is the queue empty?
        return N == 0;
    }
    public int size() {            // return the number of items on the queue
        return N;
    }
    public void enqueue(Item item) { // add the item
        if (item == null)
            throw new NullPointerException();
         if (N == q.length)
            resize(2*N);
        q[N] = item;
        N++;
    }
    public Item dequeue() {        // delete and return a random item
        if (isEmpty())
            throw new NoSuchElementException();
        int del = StdRandom.uniform(N);
        swap(del, N-1);
        N--;
        Item result = q[N];
        q[N] = null;
        if (N > 0 && N == q.length/4)
            resize(q.length/2);
        return result;
    }
    private void swap(int i, int j) {
        if (i == j)
            return;
        else {
            Item tmp = q[i];
            q[i] = q[j];
            q[j] = tmp;
        }
    }
    private void resize(int size) {
        Item[] tmp = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) 
            tmp[i] = q[i];
        q = tmp;
    }
    public Item sample() {         // return (but do not delete) a random item
        if (isEmpty())
            throw new NoSuchElementException();
        return q[StdRandom.uniform(N)];
    }
    public Iterator<Item> iterator() { // return an independent iterator over items in random order
        return new RandomQueueIterator();
    }
    private class RandomQueueIterator implements Iterator<Item> {
        private int count = 0;
        private int[] idx = new int[N];
        
        public RandomQueueIterator() {
            for (int i = 0; i < N; i++)
                idx[i] = i;
            StdRandom.shuffle(idx);
        }
        public boolean hasNext() {
            return count < N;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = q[idx[count]];
            count++;
            return item;
        }
    }
    public static void main(String[] args) { // unit testing
    
    }
}