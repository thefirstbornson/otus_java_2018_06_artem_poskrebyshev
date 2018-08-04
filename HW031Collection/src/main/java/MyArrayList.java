import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class MyArrayList <T> implements List<T> {

    private Object[] elements={};
    private int size;

    public MyArrayList(){

    }

    private T elements(int index) {
        return (T) elements[index];
    }

    private Object[] grow() {
        return elements = Arrays.copyOf(elements,size + 1);
    }

    public int size() {
        //if (true) throw new RuntimeException();
        return this.size ;
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
        if (true) throw new RuntimeException();
    }

    public Object[] toArray() {
        return Arrays.copyOf(elements, size);
    }

    @Override
    public boolean add(T e) {
        add(e, elements, size);
        return true;
    }


    private void add(T e, Object[] elementData, int s) {
        if (s == elementData.length)
            elementData = grow();
        elementData[s] = e;
        size = s + 1;
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
        throw new RuntimeException();
    }

    public void sort(Comparator c) {
        Arrays.sort((T[]) elements, 0, size, c);
    }

    public void clear() {
        throw new RuntimeException();
    }

    public T get(int index) {
        return elements(index);
    }

    public T set(int index, Object element) {

        T oldValue = elements(index);
        elements [index] = element;
        return oldValue;
    }

    public void add(int index, Object element) {

    }

    public T remove(int index) {
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
