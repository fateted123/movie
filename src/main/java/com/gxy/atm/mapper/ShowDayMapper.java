package com.gxy.atm.mapper;

import com.gxy.atm.entity.ShowDay;

import java.util.List;

public interface ShowDayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShowDay record);

    ShowDay selectByPrimaryKey(Integer id);

    List<ShowDay> listByMovieIdShowDay(Integer movieId);


}