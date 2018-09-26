package com.gxy.atm.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.gxy.atm.alipay.AlipayConfig;
import com.gxy.atm.entity.Movie;
import com.gxy.atm.entity.Order;
import com.gxy.atm.entity.OrderInfo;
import com.gxy.atm.entity.User;
import com.gxy.atm.exception.MyException;
import com.gxy.atm.mapper.MovieMapper;
import com.gxy.atm.mapper.OrderInfoMapper;
import com.gxy.atm.mapper.OrderMapper;
import com.gxy.atm.mapper.UserMapper;
import com.gxy.atm.tasks.OrderCancelTask;
import com.gxy.atm.util.DecimalCalculate;
import com.gxy.atm.util.MyRandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private static Logger log = LoggerFactory.getLogger(OrderService.class);

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private MovieMapper movieMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 添加订单业务
     *
     * @param showTimeId
     * @param movieId
     * @param selectedSeats
     * @param userId
     */
    @Transactional(rollbackFor = Exception.class)
    public Order addOrder(Integer showTimeId, Integer movieId, List<String> selectedSeats, Integer userId) throws MyException {

        for (String seat: selectedSeats) {
            int i = orderMapper.countBySeat(seat, showTimeId);
            if (i >0){
                throw new MyException("票已经售出请重新选择");
            }
        }

        Movie movie = movieMapper.selectByPrimaryKey(movieId);

        Double totalPrice = DecimalCalculate.mul(Double.parseDouble(movie.getPrice()), selectedSeats.size());

        //往主表添加数据
        Order order = new Order();
        order.setUserId(userId);
        order.setOrderNo(System.currentTimeMillis() + MyRandomUtil.getRandom(6));
        order.setSeatCode(MyRandomUtil.getRandom(6));
        order.setShowTimeId(showTimeId);
        order.setTotalPrice(totalPrice.toString());
        orderMapper.insertOrder(order);

        //往从表添加数据
        for (String seat:selectedSeats) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setSeat(seat);
            orderInfo.setOrderId(order.getId());
            orderInfo.setPrice(movie.getPrice());
            orderInfoMapper.insertOderInfo(orderInfo);
        }
        return order;
    }

    public String toPay(String orderNo,String money) throws AlipayApiException {
        //调用支付宝支付接口
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = orderNo;
        //付款金额，必填
        String total_amount = money;
        //订单名称，必填
        String subject = "XX国际影城";
        //商品描述，可空
        String body = "电影票";

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + out_trade_no + "\","
                + "\"total_amount\":\"" + total_amount + "\","
                + "\"subject\":\"" + subject + "\","
                + "\"body\":\"" + body + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        String result = alipayClient.pageExecute(alipayRequest).getBody();

        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(String out_trade_no, String trade_no) throws Exception {



        //因为有幂等，订单有误的情况 所以要锁订单
        Order order = orderMapper.selectOrderForLock(out_trade_no);

        Integer userId = order.getUserId();

        // 锁账号
        User user = userMapper.selectByPrimaryKey(order.getUserId());

        if (user == null) {

            throw new MyException("id有误");
        }


        if (order == null) {

            throw new MyException("订单有问题");

        }

        //幂等
        if (order.getStatus() == 2) {
            return;
        }

        orderMapper.updateOrderStatus(out_trade_no, trade_no);


    }


    /**
     * 取消订单业务 要加事务 防止同时有人一起取消
     */
    @Transactional(rollbackFor = Exception.class)
    public void cancelOrder () {
        //查询超过十分钟未支付的订单
        List<Integer> ids = orderMapper.listCancelOrders();
        log.info("一共查询了{}条数据要修改",ids.size());

        for (Integer id:ids) {
            //锁住表
            orderMapper.selectByPrimaryKeyForLock(id);
            //修改状态
            orderMapper.updateStatusById(id);
        }

    }

}
