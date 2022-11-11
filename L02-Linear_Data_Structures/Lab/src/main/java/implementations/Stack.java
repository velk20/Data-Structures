package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;

public class Stack<E> implements AbstractStack<E> {
    private Node<E> top;//head
    private int size;
    private static class Node<E> {
        private E value;
        private Node<E> next;

        public Node(E element) {
            this.value = element;
        }
    }

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    @Override
    public void push(E element) {
        Node<E> newNode = new Node<>(element);

        newNode.next = this.top;
        this.top = newNode;

        this.size++;
    }


    @Override
    public E pop() {
        ensureNotEmpty();

        Node<E> popedNode = this.top;

        this.top = popedNode.next;

        this.size--;
        return popedNode.value;
    }

    private void ensureNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty!");
        }
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.top.value;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> current = top;
            @Override
            public boolean hasNext() {
                return current.next != null;
            }

            @Override
            public E next() {
                E value = current.value;
                current = current.next;
                return value;
            }

        };
    }
}
