/**
 * Created by joshgoldman on 1/30/17.
 */
public class LinkedListDeque<Item> {

    private class Node {
        private Item first;
        private Node rest;
        private Node prev;


        private Node(Item f, Node r, Node p) {
            first = f;
            rest = r;
            prev = p;
        }

    }

    private Node sentinel;
    private int size;


    public LinkedListDeque() {
        sentinel = new Node(null, sentinel, sentinel);
        sentinel.rest = sentinel;
        sentinel.prev = sentinel.rest;
        size = 0;


    }


    public void addFirst(Item item) {


        sentinel.rest = new Node(item, sentinel.rest, sentinel);
        sentinel.rest.rest.prev = sentinel.rest;
        if (isEmpty()) {
            sentinel.prev = sentinel.rest;
        }

        size += 1;

    }

    public void addLast(Item item) {
        sentinel.prev = new Node(item, sentinel, sentinel.prev);
        sentinel.prev.prev.rest = sentinel.prev;
        size += 1;


    }
    public int size() {
        return size;

    }

    public boolean isEmpty() {
        return (sentinel.prev == sentinel);



    }
    public Item removeFirst() {
        if (isEmpty()) {
            return null;
        } else {
            Item first = sentinel.rest.first;
            sentinel.rest = sentinel.rest.rest;
            sentinel.rest.prev = sentinel;
            size -= 1;
            return first;

        }

    }
    public Item removeLast() {
        if (isEmpty()) {
            return null;
        } else {
            Item last = sentinel.prev.first;
            sentinel.prev = sentinel.prev.prev;
            sentinel.prev.rest = sentinel;
            size -= 1;
            return last;
        }

    }

    public void printDeque() {

        Node holder = sentinel;
        while (holder.rest != sentinel) {
            System.out.print(holder.rest.first + " ");
            holder = holder.rest;
        }

    }
    public Item get(int index) {
        Node holder = new Node(sentinel.first, sentinel.rest, sentinel.prev);
        return helper(index, holder);
    }


    private Item helper(int index, Node p) {
        while (index != 0) {
            if (p.rest == sentinel) {
                return null;
            }
            p = p.rest;
            index -= 1;
        }
        return p.rest.first;

    }

    public Item getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        Node holder = new Node(sentinel.first, sentinel.rest, sentinel.prev);
        return helperRecursive(index, holder);
    }

    private Item helperRecursive(int index, Node n) {

        if (index == 0) {
            return n.rest.first;

        } else {
            if (n.rest == sentinel) {
                return null;
            }
            return helper(index - 1, n.rest);
        }
    }

}
