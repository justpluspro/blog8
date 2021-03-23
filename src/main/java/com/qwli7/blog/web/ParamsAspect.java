//package com.qwli7.blog.web;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class ParamsAspect {
//
//    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
//
//
//    @Pointcut("execution(public * com.qwli7.blog.web.controller.*.*(..))")
//    public void brokerAspect() {
//    }
//
//    @Around("brokerAspect()")
//    public void doAround(ProceedingJoinPoint joinPoint) {
//        final Object[] args = joinPoint.getArgs();
//        try {
//            final Object proceed = joinPoint.proceed(args);
//            logger.info(proceed.toString());
//        } catch (Throwable ex){
//            ex.printStackTrace();
//        }
//    }
//}
