/**
 * Created by joshgoldman on 1/30/17.
 */
public class ArrayDeque<Item> {
    private int size;
    private int nextFirst;
    private int nextLast;
    private static double usageFactor = .25;
    private Item[] items;

    public ArrayDeque() {
        items = (Item[]) new Object[8];
        size = 0;
        nextFirst = 0;
        nextLast = 1;

    }

    public void addFirst(Item x) {
        if (size == items.length) {
            resize(size * 2);
        }


        items[((nextFirst % items.length) + items.length) % items.length] = x;
        nextFirst = (((nextFirst - 1) % items.length) + items.length) % items.length;


        size += 1;
    }



    public void addLast(Item x) {
        if (size == items.length) {
            resize(size * 2);
        }


        items[((nextLast % items.length) + items.length) % items.length] = x;
        nextLast = (((nextLast + 1) % items.length) + items.length) % items.length;

        size += 1;



    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;

    }

    public int size() {
        return size;
    }

    public Item removeFirst() {

        Item first = items[(((nextFirst + 1) % items.length) + items.length) % items.length];
        items[(((nextFirst + 1) % items.length) + items.length) % items.length] = null;


        if (items.length >= 16) {
            if ((double) size / (double) items.length < usageFactor) {
                this.resize(items.length / 2);
            }

        }
        size -= 1;


        nextFirst = (((nextFirst + 1) % items.length) + items.length) % items.length;


        return first;

    }

    public Item removeLast() {




        Item last = items[(((nextLast - 1) % items.length) + items.length) % items.length];
        items[(((nextLast - 1) % items.length) + items.length) % items.length] = null;

        if (items.length >= 16) {
            if ((double) size / (double) items.length < usageFactor) {
                this.resize(items.length / 2);
            }

        }




        size -= 1;

        nextLast = (((nextLast - 1) % items.length) + items.length) % items.length;


        return last;

    }

    public Item get(int index) {
        int subtracted = ((((nextFirst + 1) % items.length) + items.length) % items.length + index);
        return items[((subtracted % items.length) + items.length) % items.length];


    }

    public void printDeque() {
        int counter = 0;
        while (counter < size) {
            System.out.print(this.get(counter) + " ");
            counter += 1;

        }

    }

    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];

        Item[] copy = (Item[]) new Object[size];

        int counter = 0;
        while (counter < size) {
            copy[counter] = get(counter);
            counter += 1;

        }

        System.arraycopy(copy, 0, a, 0, size);
        nextFirst = -1;
        nextLast = size;
        items = a;

    }

}
