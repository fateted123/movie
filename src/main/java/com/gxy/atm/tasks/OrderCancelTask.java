package com.gxy.atm.tasks;

import com.gxy.atm.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class OrderCancelTask {

    private static Logger log = LoggerFactory.getLogger(OrderCancelTask.class);

    @Resource
    OrderService orderService;

    /**
     * 取消订单的定时任务
     */
    @Scheduled(fixedRate = 1000* 60)
    public void cancelOrder () {
        log.info(">>>>>LGD取消订单开始");
        orderService.cancelOrder();
        log.info(">>>>>>>>>结束");
    }
}
