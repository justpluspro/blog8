package com.qwli7.blog.core.security;

import org.springframework.stereotype.Component;

/**
 * @author qwli7
 * @date 2023/3/6 14:09
 * 功能：blog8
 **/
@Component
public class AttemptLoggerManager {


    public AttemptLogger createAttemptLogger(int attemptCount, int maxAttemptCount) {
        return new AttemptLogger(attemptCount, maxAttemptCount);
    }
}
