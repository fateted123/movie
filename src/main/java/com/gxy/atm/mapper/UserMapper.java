package com.gxy.atm.mapper;

import com.gxy.atm.entity.User;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    User selectByLoginName(String loginName);

    User selectByEmail(String email);


}