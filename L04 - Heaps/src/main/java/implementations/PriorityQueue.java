package implementations;

import interfaces.AbstractQueue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PriorityQueue<E extends Comparable<E>> implements AbstractQueue<E> {

    private List<E> elements;

    public PriorityQueue() {
        this.elements = new ArrayList<>();
    }

    @Override
    public int size() {
        return this.elements.size();
    }

    @Override
    public void add(E element) {
        this.elements.add(element);
        this.heapifyUp(this.size() - 1);
    }

    private void heapifyUp(int index) {
        while (index > 0 && isLess(index,getParentIndex(index))) {
            Collections.swap(this.elements, index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    private boolean isLess(int firstIndex, int secondIndex) {
        return getAt(firstIndex).compareTo(getAt(secondIndex)) > 0;
    }

    private E getAt(int index) {
        return this.elements.get(index);
    }

    private int getParentIndex(int index) {
        return (index - 1) / 2;
    }

    @Override
    public E peek() {
        ensureNonEmpty();
        return this.elements.get(0);
    }

    private void ensureNonEmpty() {
        if (this.size() == 0) {
            throw new IllegalStateException("Heap is empty upon peek attempt");
        }
    }

    @Override
    public E poll() {
        ensureNonEmpty();
        E returnedValue = getAt(0);
        Collections.swap(this.elements, 0, this.size() - 1);
        this.elements.remove(this.size() - 1);
        this.heapifyDown(0);
        return returnedValue;
    }

    private E getLeftChild(int index) {
        return this.elements.get(this.getLeftChildIndex(index));
    }

    private E getRightChild(int index) {
        return this.elements.get(this.getRightChildIndex(index));
    }
    private int getLeftChildIndex(int index) {
        return 2 * index + 1;
    }

    private int getRightChildIndex(int index) {
        return 2 * index + 2;
    }
    private void heapifyDown(int index) {
        while (getLeftChildIndex(index) < this.size() && isLess(index, getLeftChildIndex(index))) {
            int child = getLeftChildIndex(index);
            int rightChildIndex = getRightChildIndex(index);
            if (rightChildIndex < this.size() && isLess(child, rightChildIndex)) {
                child = rightChildIndex;
            }

            Collections.swap(this.elements, child, index);
            index = child;
        }
    }

}
