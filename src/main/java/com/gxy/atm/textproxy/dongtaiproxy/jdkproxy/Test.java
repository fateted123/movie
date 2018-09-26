package com.gxy.atm.textproxy.dongtaiproxy.jdkproxy;

import com.gxy.atm.textproxy.staticproxy.RealSubjecj;
import com.gxy.atm.textproxy.staticproxy.Subject;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class Test {

    public static void main(String[] args) {
        //没使用动态代理
        RealSubjecj realSubjecj = new RealSubjecj();
//        realSubjecj.dealTask("newbee");
        //使用动态代理

        InvocationHandler subjectInvocat = new SubjectInvocat(realSubjecj); //执行器

        //生成动态代理对象
        Subject proxy = (Subject) Proxy.newProxyInstance(realSubjecj.getClass().getClassLoader(),
                realSubjecj.getClass().getInterfaces(), subjectInvocat);

        proxy.dealTask("tea star");

        System.out.println("<>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<");

        SubjectImpl2 impl2 = new SubjectImpl2();

        InvocationHandler subjectInvocat1 = new SubjectInvocat(impl2);

        Subject2 o = (Subject2) Proxy.newProxyInstance(impl2.getClass().getClassLoader(), impl2.getClass().getInterfaces(), subjectInvocat1);

        o.sayHello();

        System.out.println("代理对象名字："+o.getClass().getName());

    }
}
