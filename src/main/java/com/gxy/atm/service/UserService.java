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
import com.gxy.atm.util.DecimalCalculate;
import com.gxy.atm.util.MyRandomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserService {

    private static Logger log = LoggerFactory.getLogger(UserService.class);

    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderInfoMapper orderInfoMapper;
    @Resource
    private MovieMapper movieMapper;

    @Resource
    private UserMapper userMapper;

    /**
     * 注册
     * @param loginName
     * @param password
     * @param userName
     * @param userId
     */
   public void addUser(String loginName, String password, String userName, String userId,String salt) throws MyException {

       User user1 = userMapper.selectByLoginName(userId);
       if (user1 !=null) {
           throw new MyException("此用户已注册");
       }

       User user = new User();
       user.setLoginName(userId);
       user.setUserName(userName);
       user.setPassword(password);
       user.setEmail(loginName);
       user.setSalt(salt);
       userMapper.insert(user);
   }

}
