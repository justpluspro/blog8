package com.qwli7.blog.core.security;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author qwli7
 * @date 2023/3/6 13:59
 * 功能：blog8
 **/
public class AttemptLogger {

    private final int attemptCount;
    private final int maxAttemptCount;

    private final AtomicInteger maxAttemptCounter;
    private final Map<String, AtomicInteger> map = new ConcurrentHashMap<>();

    AttemptLogger(int attemptCount, int maxAttemptCount) {
        if(attemptCount < 1) {
            throw new RuntimeException("尝试次数不能小于1");
        }
        this.attemptCount = attemptCount;
        this.maxAttemptCount = maxAttemptCount;
        this.maxAttemptCounter = new AtomicInteger(0);
    }

    /**
     * 尝试，次数 + 1
     * @param ip ip
     * @return 是否达到阈值
     */
    public boolean log(String ip) {
        BooleanHolder holder = new BooleanHolder(true);
        map.compute(ip, (key, value) -> {
            //当前 ip 没有尝试过 并且最大尝试次数 < 尝试次数
            if(value == null && maxAttemptCounter.get() < attemptCount) {
                value = new AtomicInteger();
            }

            if(value != null) {
                holder.value = add(value);
            }

            return value;
        });
        return holder.value;
    }


    private boolean add(AtomicInteger value) {
        int count = value.get();
        if(count == attemptCount) {
            return true;
        }

        for(;;) {
            int maxCount = maxAttemptCounter.get();
            if(maxCount == maxAttemptCount) {
                return true;
            }
            if(maxAttemptCounter.compareAndSet(maxCount, maxCount+1)) {
                value.incrementAndGet();
                return false;
            }
        }
    }

    public Boolean reach(String ip) {
        if(maxAttemptCounter.get() == attemptCount) {
            return true;
        }
        AtomicInteger count = map.get(ip);
        return count != null && count.get() == attemptCount;
    }

    public void remove(String ip) {
        map.computeIfPresent(ip, (key, value) -> {
            // 将当前 ip 重试的次数重置成 0
            maxAttemptCounter.addAndGet(-value.get());
            return null;
        });
    }

    private static final class BooleanHolder {
        private boolean value;

        BooleanHolder(boolean value) {
            super();
            this.value = value;
        }

    }
}
