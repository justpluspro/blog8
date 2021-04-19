package com.qwli7.blog.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于内存的多次操作输入验证码
 * @author liqiwen
 * @since 2.0
 */
public class AttemptLogCounter {

    private final Map<String, AtomicInteger> attemptMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduledExecutorService;

    public AttemptLogCounter(int count, int maxCount, int second) {


        this.scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }


    public void log(String ip) {


    }
}
