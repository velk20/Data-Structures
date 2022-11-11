package implementations;

import interfaces.List;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class ArrayList<E> implements List<E> {
    private Object[] elements;
    private static final int INITIAL_CAPACITY = 4;
    private int size;
    private int currentCapacity;

    public ArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
        this.currentCapacity = INITIAL_CAPACITY;
        this.size = 0;
    }

    @Override
    public boolean add(E element) {
        if (!isEnoughSpace()) {
            grow();
        }

        this.elements[this.size++] = element;
        return true;
    }

    private boolean isEnoughSpace (){
       return !(this.size == this.currentCapacity);
    }
    private void grow() {
        this.currentCapacity *= 2;
        Object[] temp = new Object[this.currentCapacity];
        for (int i = 0; i < this.size; i++) {
            temp[i] = this.elements[i];
        }

        this.elements = temp;
    }

    private void shiftRight(int index) {
        for (int i = this.size; i > index; i--) {
            this.elements[i] = this.elements[i - 1];
        }

    }
    @Override
    public boolean add(int index, E element) {
        if (!isEnoughSpace()) {
            grow();
        }

        shiftRight(index);
        this.elements[index] = element;
        this.size++;
        return true;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index < this.size;
    }
    @Override
    public E get(int index) {
        if (isValidIndex(index)) {
            return (E) this.elements[index];
        }

        throw new IndexOutOfBoundsException("No such element as index: " + index);
    }

    @Override
    public E set(int index, E element) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("No such index in the ArrayList: index=" + index);
        }

        E oldElement = (E) this.elements[index];

        this.elements[index] = element;

        return oldElement;
    }

    private boolean canShrink() {
        return this.size <= this.currentCapacity / 3;
    }

    private void shiftLeft(int index) {
        for (int i = index; i < size; i++) {
            this.elements[i] = this.elements[i + 1];

        }
    }
    @Override
    public E remove(int index) {
        if (!isValidIndex(index)) {
            throw new IndexOutOfBoundsException("No such index in the ArrayList: index=" + index);
        }

        E deletedElement = (E) this.elements[index];
        this.size--;
        shiftLeft(index);
        if (canShrink()) {
            shrink();
        }
        return deletedElement;
    }

    private void shrink() {
        this.currentCapacity /= 3;
        Object[] newArray = Arrays.copyOf(this.elements, this.currentCapacity);
        this.elements = newArray;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {
        return searchForElement(element);
    }
    private int searchForElement(E element) {
        for (int i = 0; i < this.size; i++) {
            if (element .equals((E) this.elements[i])) {
                return i;
            }
        }

        return -1;
    }
    @Override
    public boolean contains(E element) {
        return searchForElement(element) >= 0;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return this.index < size();
            }

            @Override
            public E next() {
                return get(index++);
            }
        };
    }
}
