package com.qwli7.blog.queue;

import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MemoryDataContainer<T> implements DataContainer<T> {

    private Queue<T> container = new ConcurrentLinkedQueue<>();


    @Override
    public void push(T data) {
        this.container.offer(data);
    }

    @Override
    public void pushAll(Collection<T> data) {
        this.container.addAll(data);
    }

    @Override
    public void remove(T data) {
        container.remove(data);
    }

    @Override
    public T pop() {
        return container.poll();
    }

    @Override
    public boolean isEmpty() {
        return container.isEmpty();
    }

    @Override
    public int size() {
        return container.size();
    }
}
