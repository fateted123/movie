package com.gxy.atm.textproxy.dongtaiproxy.cglibproxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

// CGLIB动态代理
// 1. 首先实现一个MethodInterceptor，方法调用会被转发到该类的intercept()方法。
class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {


        System.out.println("代理执行的方法"+method.getName());
        //方法头上是否有注解声明
        boolean annotationPresent = method.isAnnotationPresent(Fuck.class);

        if (annotationPresent){
            Fuck annotation = method.getAnnotation(Fuck.class);
            System.out.println(annotation.value());
        }
        System.out.println("vgj虐泉啦");
        long stime = System.currentTimeMillis();

        Object object = proxy.invokeSuper(obj, args);//目标代码

        long ftime = System.currentTimeMillis();
        System.out.println("圣剑插在lgd基地了！");
        System.out.println("执行任务耗时" + (ftime - stime) + "毫秒");


        return object;


    }
}