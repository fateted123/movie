package com.gxy.atm.controller;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gxy.atm.alipay.AlipayConfig;
import com.gxy.atm.entity.Order;
import com.gxy.atm.entity.ShowDay;
import com.gxy.atm.entity.ShowTime;
import com.gxy.atm.entity.User;
import com.gxy.atm.exception.MyException;
import com.gxy.atm.mapper.*;
import com.gxy.atm.service.OrderService;
import com.gxy.atm.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Stream;

@Controller
@RequestMapping(value = "api/order")
public class OrderController {

    private static Logger log = LoggerFactory.getLogger(OrderController.class);
    @Resource
    private MovieMapper movieMapper;

    @Resource
    private ShowDayMapper showDayMapper;

    @Resource
    private ShowTimeMapper showTimeMapper;

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private OrderService orderService;

    @Resource
    private UserMapper userMapper;


    @PostMapping(value = "listSaledSeats.do")
    @ResponseBody
    public AjaxResult listSaledSeats(@RequestParam Integer showTimeId) {

        return AjaxResult.success(orderMapper.listSaledSeats(showTimeId));
    }

    @PostMapping(value = "addOrder.do")
    @ResponseBody
    public AjaxResult addOrder(HttpSession session,
                               @RequestParam Integer showTimeId,
                               @RequestParam Integer movieId,
                               @RequestParam("selectedSeats[]") List<String> selectedSeats) {

        String loginName = (String) session.getAttribute("loginName");

        User user = userMapper.selectByLoginName(loginName);
        Integer userId = user.getId();

        Order order = null;

        try {
            order = orderService.addOrder(showTimeId, movieId, selectedSeats, userId);
        } catch (MyException e) {
           return AjaxResult.fail(e.getMessage());
        }

        try {
            String result = orderService.toPay(order.getOrderNo(), order.getTotalPrice());
            return AjaxResult.success(result);
        } catch (AlipayApiException e) {
            e.printStackTrace();
            AjaxResult.fail("支付失败");
        }

        return null;
    }

    @RequestMapping(value = "notifyUrl.do", method = {RequestMethod.GET, RequestMethod.POST})
    public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("支付宝异步通知");


        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//            valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type); //调用SDK验证签名

        //——请在这里编写您的程序（以下代码仅作参考）——

	/* 实际验证过程建议商户务必添加以下校验：
	1、需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
	2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
	3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email）
	4、验证app_id是否为该商户本身。
	*/
        if (signVerified) {//验证成功
            //商户订单号
            String out_trade_no = request.getParameter("out_trade_no");

            //支付宝交易号
            String trade_no = request.getParameter("trade_no");

            //交易状态
            String trade_status = request.getParameter("trade_status");

            //appid
            String app_id = request.getParameter("app_id");

            //验证appid是否正确
            if (!app_id.equals(AlipayConfig.app_id)) {
                try {
                    response.getWriter().println("fail");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (trade_status.equals("TRADE_FINISHED") || trade_status.equals("TRADE_SUCCESS")) {
                //        1:更改订单状态
                //        2:加余额
                log.info("支付异步通知：out_trade_no：{}，交易号：{},状态：{}", out_trade_no, trade_no, trade_status);

                orderService.updateStatus(out_trade_no, trade_no);

            }

            response.getWriter().println("success");

        } else {//验证失败
            response.getWriter().println("fail");

            //调试用，写文本函数记录程序运行情况是否正常
            //String sWord = AlipaySignature.getSignCheckContentV1(params);
            //AlipayConfig.logResult(sWord);
        }

        //——请在这里编写您的程序（以上代码仅作参考）——


    }

    @RequestMapping(value = "/listOrders.do", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public AjaxResult listOrders(HttpSession session,
                                 String sTime, String eTime,//自动带你获取了这些属性相当于request.getAttribute，，名字必须与前端表单name一致
                                 @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex) {//required表示是否必须,默认为true


        try {
            String loginName = (String) session.getAttribute("loginName");


            Integer pageRows = 3; //默认每页显示条数为3

            User user = userMapper.selectByLoginName(loginName);//查询数据库

            log.info("loginName:" + loginName);

            PageHelper.startPage(pageIndex, pageRows);//分页插件 ， 分页处理 （当前页码，每页行数） 上下两行代码最好放一起
            List<Order> orders = orderMapper.listTimeByUserId(user.getId(), sTime, eTime);

            HashMap<String, Object> params = new HashMap<>();

            PageInfo<Order> pageInfo = new PageInfo<>(orders, 3);//分页插件，脚表处理，（分页的数据，页码导航数）[1][2][3]

            return AjaxResult.success(pageInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return AjaxResult.fail("系统异常");
        }
    }

}
