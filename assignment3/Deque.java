import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node left;
        Node right;
    }

    private Node first, last;
    private int size;

    // construct an empty deque
    public Deque() {
        size = 0;
        first = null;
        last = null;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return (first == null);
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        size++;
        Node new_first = new Node();
        new_first.item = item;
        new_first.left = null;
        new_first.right = first;

        if (first != null) first.left = new_first;

        first = new_first;
        if (last == null) {
            last = new_first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        size++;
        Node new_last = new Node();
        new_last.item = item;
        new_last.left = last;
        new_last.right = null;

        if (last != null) last.right = new_last;

        last = new_last;
        if(first == null) {
            first = new_last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        size--;
        Item item = first.item;
        first = first.right;
        if (first == null) last = null;

        if (first != null) first.left = null;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        size--;
        Item item = last.item;
        last = last.left;
        if(last == null) first = null;

        if (last != null) last.right = null;

        return item;
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == null) throw new NoSuchElementException();

            Node node = current;
            current = current.right;
            return node.item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
         
    }

}