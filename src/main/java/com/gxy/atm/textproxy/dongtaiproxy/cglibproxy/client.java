package com.gxy.atm.textproxy.dongtaiproxy.cglibproxy;

import org.springframework.cglib.proxy.Enhancer;

public class client {
    public static void main(String[] args) {
        // 2. 然后在需要使用HelloConcrete的时候，通过CGLIB动态代理获取代理对象。
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloConcrete.class);
        enhancer.setCallback(new MyMethodInterceptor());

        HelloConcrete o = (HelloConcrete) enhancer.create();
        System.out.println(o.sayHello("FGNB"));
    }
}
