import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node first, last;
   
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }
    public Deque() {                         // construct an empty deque
        first = null;
        last = null;
        N = 0;
    }
    public boolean isEmpty() {               // is the deque empty?
        return N == 0;
    }
    public int size() {                      // return the number of items on the deque
        return N;
    }
    public void addFirst(Item item) {        // insert the item at the front
        if (item == null)
            throw new NullPointerException();
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.prev = null;
        if (isEmpty()) {
            first.next = null;
            last = first;
        }
        else {
            first.next = oldfirst;
            oldfirst.prev = first;
        }
        N++;
        //assert check();
    }
    public void addLast(Item item) {         // insert the item at the end
        if (item == null)
            throw new NullPointerException();
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) {
            last.prev = null;
            first = last;
        }
        else {
            oldlast.next = last;
            last.prev = oldlast;
        }
        N++;
        //assert check();
    }
    public Item removeFirst() {              // delete and return the item at the front
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty())
            last = null;
        else
            first.prev = null;
        //assert check();
        return item;
    }
    public Item removeLast() {               // delete and return the item at the end
        if (isEmpty())
            throw new NoSuchElementException();
        Item item = last.item;
        last = last.prev;
        N--;
        if (isEmpty())
            first = null;
        else 
            last.next = null;
        //assert check();
        return item;
    }
    public Iterator<Item> iterator() {       // return an iterator over items in order from front to end
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;
        
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }
    /*
    private boolean check() {
        if (N == 0) {
            if (first != null) return false;
            if (last  != null) return false;
        }
        else if (N == 1) {
            if (first == null || last == null) return false;
            if (first != last)                 return false;
            if (first.next != null)            return false;
        }
        else {
            if (first == last)      return false;
            if (first.next == null) return false;
            if (last.next  != null) return false;
            
            // check internal consistency of instance variable N
            int numberOfNodes = 0;
            for (Node x = first; x != null; x = x.next) {
                numberOfNodes++;
            }
            if (numberOfNodes != N) return false;
            
            // check internal consistency of instance variable last
            Node lastNode = first;
            while (lastNode.next != null) {
                lastNode = lastNode.next;
            }
            if (last != lastNode) return false;
        }
        return true;
    }
    */
    public static void main(String[] args) { // unit testing
        Deque<String> q = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("-f")) System.out.println(q.removeFirst() + " ");
            else if (item.equals("-l")) System.out.println(q.removeLast() + " ");
            else if (item.equals("+f")) {
                String item2 = StdIn.readString();
                q.addFirst(item2);
            }
            else if (item.equals("+l")) {
                String item2 = StdIn.readString();
                q.addLast(item2);
            }
            else if (item.equals("test")) {
                for (String s : q)
                    System.out.println(s);
            }
            else 
                System.out.println("Invalid operation");
        }
        StdOut.println("(" + q.size() + " left on queue)");
    }
}