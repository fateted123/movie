package com.gxy.atm.mapper;

import com.gxy.atm.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);


    Order selectByPrimaryKey(Integer id);

    List<String> listSaledSeats(Integer showTimeId);

    void insertOrder(Order order);

    Order selectOrderForLock(String orderNo);

    void updateOrderStatus(@Param("orderNo") String orderNo,
                           @Param("tradeNo") String tradeNo);

    List<Integer> listCancelOrders ();

    Order selectByPrimaryKeyForLock(Integer id);

    void updateStatusById(Integer id);

    int countBySeat(@Param("seat") String seat,
                    @Param("showTimeId") Integer showTimeId);

    List<Order> listOrders();

    List<Order> listTimeByUserId(@Param("userId") Integer userId,
                                 @Param("sTime") String sTime,
                                 @Param("eTime") String eTime);
}