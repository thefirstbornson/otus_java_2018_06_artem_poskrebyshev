import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyArrayList <T> implements List {
    Object[] elements;
    private int size;

    private Object[] grow(int minCapacity) {
        return elements = Arrays.copyOf(elements,
                newCapacity(minCapacity));
    }


    public int size() {
        if (true) throw new RuntimeException();
        else return 0 ;
    }

    public boolean isEmpty() {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean contains(Object o) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public Iterator iterator() {
        if (true) throw new RuntimeException();
        else return null;
    }

    public void forEach(Consumer action) {

    }

    public Object[] toArray() {
        if (true) throw new RuntimeException();
        else return new Object[0];
    }

    public boolean add(Object o) {
        if (true) throw new RuntimeException();
        else return false;
    }

    private void add(E e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(T e) {

        add(e, elementData, size);

        return true;
    }

    public boolean remove(Object o) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean addAll(Collection c) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean removeIf(Predicate filter) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean addAll(int index, Collection c) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public void replaceAll(UnaryOperator operator) {

    }

    public void sort(Comparator c) {

    }

    public void clear() {

    }

    public Object get(int index) {
        if (true) throw new RuntimeException();
        else return null;
    }

    public Object set(int index, Object element) {
        if (true) throw new RuntimeException();
        else return null;
    }

    public void add(int index, Object element) {

    }

    public Object remove(int index) {
        if (true) throw new RuntimeException();
        else return null;
    }

    public int indexOf(Object o) {
        if (true) throw new RuntimeException();
        else return 0;
    }

    public int lastIndexOf(Object o) {
        if (true) throw new RuntimeException();
        else return 0;
    }

    public ListIterator listIterator() {
        if (true) throw new RuntimeException();
        else return null;
    }

    public ListIterator listIterator(int index) {
        if (true) throw new RuntimeException();
        else return null;
    }

    public List subList(int fromIndex, int toIndex) {
        if (true) throw new RuntimeException();
        else return null;
    }

    public Spliterator spliterator() {
        if (true) throw new RuntimeException();
        else return null;
    }

    public Stream stream() {
        if (true) throw new RuntimeException();
        else return null;
    }

    public Stream parallelStream() {
        if (true) throw new RuntimeException();
        else return null;
    }

    public boolean retainAll(Collection c) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean removeAll(Collection c) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public boolean containsAll(Collection c) {
        if (true) throw new RuntimeException();
        else return false;
    }

    public Object[] toArray(Object[] a) {
        if (true) throw new RuntimeException();
        else return new Object[0];
    }
}
