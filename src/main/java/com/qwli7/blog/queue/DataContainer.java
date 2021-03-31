package com.qwli7.blog.queue;

import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * 容器
 * @param <T> T
 */
public interface DataContainer<T> {

    /**
     * push 数据
     * @param data data
     */
    void push(@NotNull T data);

    /**
     * pushAll 数据
     * @param data data
     */
    void pushAll(Collection<T> data);

    /**
     * 取出数据
     * @return T
     */
    T pop();

    /**
     * 移除数据
     * @param data data
     */
    void remove(T data);

    /**
     * 容器是否为空
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 容器内部大小
     * @return int
     */
    int size();
}
