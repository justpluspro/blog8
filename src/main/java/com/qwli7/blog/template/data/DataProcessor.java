package com.qwli7.blog.template.data;


public abstract class DataProcessor<T> {

    private String name;

    public DataProcessor(String name) {
        this.name = name;
    }
}
