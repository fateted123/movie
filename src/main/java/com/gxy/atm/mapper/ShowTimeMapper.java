package com.gxy.atm.mapper;

import com.gxy.atm.entity.ShowTime;

import java.util.HashMap;
import java.util.List;

public interface ShowTimeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowTime record);

    ShowTime selectByPrimaryKey(Integer id);

    List<ShowTime> listShowTimeByDayId(Integer showDayId);

    HashMap<String,String> getShowMsgById(Integer id);
}