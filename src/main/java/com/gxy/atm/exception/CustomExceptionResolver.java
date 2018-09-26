package com.gxy.atm.exception;

import com.alibaba.fastjson.JSON;
import com.gxy.atm.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 统一异常处理类
 */
@Component
public class CustomExceptionResolver implements HandlerExceptionResolver {

    private static Logger log = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object o, Exception ex) {

        log.error(">>>>>>>:",ex);

        //如果是自定义异常，则显示ex.getMessage ，否则显示  系统系统异常，请稍后再试
        String mistake = null;
        if(ex instanceof MyException){
            mistake = ex.getMessage();
        }else{
            mistake = "系统异常，请稍后再试";
        }



        try {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(AjaxResult.fail(mistake)));
        } catch (IOException e) {
            log.error("系统异常",ex);
        }

        //向前台返回错误信息
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("mistake", mistake);
//        modelAndView.setViewName("fail");


        return null;
    }
}