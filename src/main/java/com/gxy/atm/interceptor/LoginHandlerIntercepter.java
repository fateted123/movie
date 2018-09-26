package com.gxy.atm.interceptor;

import com.alibaba.fastjson.JSON;
import com.gxy.atm.util.AjaxResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginHandlerIntercepter implements HandlerInterceptor {
    private static Logger log = LoggerFactory.getLogger(LoginHandlerIntercepter.class);
    /**
     * preHandler ：在进入Handler(Controller)方法之前执行了，
     * 使用于身份认证，身份授权，登陆校验等，
     * 比如身份认证，用户没有登陆，拦截不再向下执行，
     * 返回值为 false ，即可实现拦截；否则，返回true时，拦截不进行执行；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {

        //用户认证
        System.out.println("LGD...........");


            HttpSession session = request.getSession(false);
            if (session != null && session.getAttribute("loginName") != null) {
                //登录成功
                return true;//请求响应继续向下传递,传递给servlet
            } else {

                try {
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(AjaxResult.fail(600,"无效的请求")));
                } catch (IOException e) {
                    log.error("系统异常",e);
                }

                return false;
            }



    }

    /**
     * postHandler : 进入Handler方法之后，返回ModelAndView之前执行，
     * 使用场景从ModelAndView参数出发，
     * 比如，将公用的模型数据在这里传入到视图，也可以统一指定显示的视图等；
     *
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }


    /**
     * afterHandler : 在执行Handler完成后执行此方法，
     * 使用于统一的异常处理，统一的日志处理等；
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
