package com.qwli7.blog.security;

import java.lang.annotation.*;

/**
 * @author qwli7
 */
@Inherited
@Documented
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authenticated {
}
