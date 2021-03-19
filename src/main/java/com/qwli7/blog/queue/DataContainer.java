package com.qwli7.blog.queue;

import javax.validation.constraints.NotNull;
import java.util.Collection;

public interface DataContainer<T> {

    void push(@NotNull T data);

    void pushAll(Collection<T> data);

    T pop();

    void remove(T data);

    boolean isEmpty();

    int size();
}
