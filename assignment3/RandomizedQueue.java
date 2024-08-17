import java.util.NoSuchElementException;
import java.util.Iterator;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] array;
    private int N;

    // construct an empty randomized queue
    public RandomizedQueue() {
        array = (Item[]) new Object[8];
        N = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    private void resize(int capacity)
    {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            copy[i] = array[i];
        array = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();

        if (N == array.length) {
            resize(2 * array.length);
        }
        array[N++] = item;

    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();

        int idx = StdRandom.uniformInt(N);

        Item item = array[idx];
        array[idx] = array[--N];
        array[N] = null;

        if (N > 0 && N == array.length/4) resize(array.length/2);

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();

        return array[StdRandom.uniformInt(N)];
    }

    private class RandQueueIterator implements Iterator<Item> {
        private int M;
        private Item[] elements;

        public RandQueueIterator() {
            M = 0;
            elements = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                elements[i] = array[i];
            }
            StdRandom.shuffle(elements);
        }

        public boolean hasNext() { return M < N; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (M>=N) throw new NoSuchElementException();

            return elements[M++];
        }

    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {

    }
}