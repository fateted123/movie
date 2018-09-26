package com.gxy.atm.util;

import java.util.ResourceBundle;

/**
 * 封装
 * jdbc 配置文件工具类
 */
public class JDBCProUtil {

    //jdbc为属性文件名，此文件放在src下，直接用jdbc即可
    private static ResourceBundle resource = ResourceBundle.getBundle("jdbc");

    public static String getUrl() {
        return resource.getString("url");
    }

    public static String getUser() {
        return resource.getString("user");
    }

    public static String getPwd() {
        return resource.getString("password");
    }

    public static String getString(String key) {
        return resource.getString(key);
    }

}
