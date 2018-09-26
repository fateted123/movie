package com.gxy.atm.textproxy.staticproxy;

public class RealSubjecj implements Subject {
    //实现类 真正做事的类 ，实现了代理接口

    @Override
    public void dealTask(String taskName) {
        System.out.println("正在打比赛" + taskName);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
