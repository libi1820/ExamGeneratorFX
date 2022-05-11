package model;

import java.io.Serializable;
import java.util.*;

public class GenericSet<T> implements Serializable {

    private T[] set;
    private final int INITIAL_CAPACITY = 10;
    private int logicalSize = 0;

    public GenericSet() {
        set = (T[]) new Object[INITIAL_CAPACITY];
    }

    public boolean add(T o) {

        for (int i = 0; i < set.length; i++) {
            if (set[i]!=null &&set[i].equals(o)) {
                return false;
            }
        }
        if (logicalSize == set.length) {
            resize();
        }

        set[logicalSize++] = o;
        return true;


    }



    private void resize() {
        T[] newSet = (T[]) new Object[this.logicalSize * 2];
        for (int i = 0; i < this.set.length; i++) {
            newSet[i] = this.set[i];
        }
        this.set = newSet;
    }

    public int size() {
        return logicalSize;
    }


    public T get(int index) {
        return set[index];
    }

    public boolean isEmpty() {
        return set[0] == null;
    }

    public void removeByIndex(int index) {

    T[] newSet = (T[]) new Object[logicalSize - 1];
    for (int j = 0; j < index; j++) {
        newSet[j] = this.set[j];
    }
    for (int j = index; j < logicalSize - 1; j++) {
        newSet[j] = this.set[j + 1];
    }
    this.set = newSet;
    logicalSize--;

    }
    public void remove(T o) {

        for (int i=0; i < logicalSize; i ++) {
            if (o.equals(set[i])) {

                T[] newSet = (T[]) new Object[logicalSize - 1];
                for (int j = 0; j < i; j++) {
                    newSet[j] = this.set[j];
                }
                for (int j = i; j < logicalSize - 1; j++) {
                    newSet[j] = this.set[j + 1];
                }
                this.set = newSet;
                logicalSize--;
                break;

            }
        }
    }




    @Override
    public String toString() {
        return Arrays.toString(set);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenericSet<?> genericSet = (GenericSet<?>) o;
        return INITIAL_CAPACITY == genericSet.INITIAL_CAPACITY && logicalSize == genericSet.logicalSize && Arrays.equals(set, genericSet.set);
    }


}
