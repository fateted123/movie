package com.gxy.atm.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


/**
 * 所有Controller方法都加上统计执行时间日志
 */
@Aspect
@Component
public class LogAop {

    @Pointcut("execution(public * com.gxy.atm.controller..*.*(..))")
    public void myMethod(){}

    @Before("myMethod()")
    public void before() {
        System.out.println(">>>>>>>>>method start");
    }

    @After("myMethod()")
    public void after() {
        System.out.println(">>>>>>>>>method after");
    }

    //环绕通知（特别适合做权限系统）
    @Around("myMethod()")
    public Object round(ProceedingJoinPoint pjp) throws Throwable{


        long s = System.currentTimeMillis();
        Object object=pjp.proceed();
        long e = System.currentTimeMillis();
        System.out.println("执行方法"+pjp.getSignature().getName()+"耗时"+(e-s)+"ms");
        return object;
    }

}
