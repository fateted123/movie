package com.gxy.atm.textproxy.dongtaiproxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理类对应的调用处理程序类
 */
public class SubjectInvocat implements InvocationHandler {

    //代理类持有一个委托类的对象引用
    private Object zhandui;

    public SubjectInvocat(Object zhandui) {
        this.zhandui = zhandui;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        System.out.println("比赛开始");
        long s = System.currentTimeMillis();
        //利用反射机制将请求分派给委托类处理。Method的invoke返回Object对象作为方法执行结果。
        //因为示例程序没有返回值，所以这里忽略了返回值处理
        method.invoke(zhandui,args);
        long e = System.currentTimeMillis();
        System.out.println("比赛时间"+(e-s));
        System.out.println("比赛结束");

        return null;
    }
}
