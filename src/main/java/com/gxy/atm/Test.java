package com.gxy.atm;

import com.gxy.atm.util.MD5Tools;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        //编码
        byte[] encode = Base64.getEncoder().encode("LGD咚咚咚咚".getBytes());
        String str = new String(encode);
        System.out.println("编码后："+str);
        //解码
        byte[] decode = Base64.getDecoder().decode(str.getBytes());
        str = new String(decode);
        System.out.println("解码后："+str);

        //用途：签名、密码存储
        String s = MD5Tools.MD5(MD5Tools.MD5("1234123123"));//嵌套加密
        System.out.println(s);
        System.out.println(s.length());

    }
}
