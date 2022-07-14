package gg.eilsapgroup.milkshake.collections;

import java.util.*;

public class SyncTreeSet<E> implements NavigableSet<E>,Set<E>{
    private final TreeSet<E> backing = new TreeSet<>();
    private final Object lock = new Object();

    @Override
    public E lower(E e) {
        synchronized (lock){
            return backing.lower(e);
        }
    }

    @Override
    public E floor(E e) {
        synchronized (lock){
            return backing.floor(e);
        }
    }


    @Override
    public E ceiling(E e) {
        synchronized (lock){
            return backing.ceiling(e);
        }
    }

    @Override
    public E higher(E e) {
        synchronized (lock){
            return backing.higher(e);
        }
    }

    @Override
    public E pollFirst() {
        synchronized (lock){
            return backing.pollFirst();
        }
    }

    @Override
    public E pollLast() {
        synchronized (lock){
            return backing.pollLast();
        }
    }

    @Override
    public Iterator<E> iterator() {
        synchronized (lock){
            TreeSet<E> back = new TreeSet<>(this.backing);
            return back.iterator();
        }
    }

    @Override
    public Object[] toArray() {
        synchronized (lock){
            return backing.toArray();
        }
    }

    @Override
    public <T> T[] toArray(T[] a) {
        synchronized (lock){
            return backing.toArray(a);
        }
    }

    @Override
    public boolean add(E e) {
        synchronized (lock){
            return backing.add(e);
        }
    }

    @Override
    public boolean remove(Object o) {
        synchronized (lock){
            return backing.remove(o);
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        synchronized (lock){
            return backing.containsAll(c);
        }
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        synchronized (lock){
            return backing.addAll(c);
        }
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        synchronized (lock){
           return backing.retainAll(c);
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        synchronized (lock){
            return backing.removeAll(c);
        }
    }

    @Override
    public void clear() {
        synchronized (lock){
            backing.clear();
        }
    }

    @Override
    public NavigableSet<E> descendingSet() {
        synchronized (lock){
            return backing.descendingSet();
        }
    }

    @Override
    public Iterator<E> descendingIterator() {
        synchronized (lock){
            return backing.descendingIterator();
        }
    }

    @Override
    public NavigableSet<E> subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) {
        synchronized (lock){
            return backing.subSet(fromElement,fromInclusive,toElement,toInclusive);
        }
    }

    @Override
    public NavigableSet<E> headSet(E toElement, boolean inclusive) {
        synchronized (lock){
            return backing.headSet(toElement,inclusive);
        }
    }

    @Override
    public NavigableSet<E> tailSet(E fromElement, boolean inclusive) {
        synchronized (lock){
            return backing.tailSet(fromElement,inclusive);
        }
    }

    @Override
    public Comparator<? super E> comparator() {
        synchronized (lock){
            return backing.comparator();
        }
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        synchronized (lock){
            return backing.subSet(fromElement,toElement);
        }
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        synchronized (lock){
            return backing.headSet(toElement);
        }
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {
        synchronized (lock){
            return backing.tailSet(fromElement);
        }
    }

    @Override
    public E first() {
        synchronized (lock){
            return backing.first();
        }
    }

    @Override
    public E last() {
        synchronized (lock){
            return backing.last();
        }
    }

    @Override
    public int size() {
        synchronized (lock){
            return backing.size();
        }
    }

    @Override
    public boolean isEmpty() {
        synchronized (lock) {
            return backing.isEmpty();
        }
    }

    @Override
    public boolean contains(Object o) {
        synchronized (lock){
            return backing.contains(o);
        }
    }
}
