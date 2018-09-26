package com.gxy.atm.controller;

import com.gxy.atm.entity.User;
import com.gxy.atm.exception.MyException;
import com.gxy.atm.mapper.MovieMapper;
import com.gxy.atm.mapper.UserMapper;
import com.gxy.atm.service.UserService;
import com.gxy.atm.util.AjaxResult;
import com.gxy.atm.util.MD5Tools;
import com.gxy.atm.util.MailUtil;
import com.gxy.atm.util.MyRandomUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/api/user")
public class UserController {
    private static Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource
    private UserMapper userMapper;
    @Resource
    private UserService userService;


    /**
     * 验证登录
     * @param session
     * @param loginName
     * @param password
     * @return
     */
    @PostMapping("login.do")
    @ResponseBody //序列化
    public AjaxResult login(HttpSession session,
                        @RequestParam("loginName") String loginName,
                        @RequestParam("password") String password)  {

        if (loginName == null || loginName.equals("") || password == null || password.equals("")) {
            return AjaxResult.fail("账号或密码错误");
        }

        User user = userMapper.selectByLoginName(loginName);

        if (user == null) {
            return AjaxResult.fail("账号或密码错误");
        }


        String userPassword = user.getPassword();

        String salt = user.getSalt();


        //利用MD5加密进行验证

        String s = loginName + password;

        String s1 = MD5Tools.MD5(MD5Tools.MD5(s));

        if (!salt.equals(s1)){
            return AjaxResult.fail("账号或密码错误");
        }


        if (!(userPassword != null && userPassword.equals(password))) {
            return AjaxResult.fail("账号或密码错误");
        }

        session.setAttribute("loginName", loginName);

       return AjaxResult.success();
    }


    @ResponseBody
    @PostMapping(value = "getUser.do")
    public AjaxResult getUser(HttpSession session){
        String loginName = (String) session.getAttribute("loginName");

        User user = userMapper.selectByLoginName(loginName);

        return AjaxResult.success(user);
    }

    /**
     * 发送验证码
     * @param session
     * @param email
     * @return
     */
    @ResponseBody
    @PostMapping(value = "sendCode.do")
    public AjaxResult sendCode(HttpSession session , String email){

        //防刷
        User user = userMapper.selectByEmail(email);

        if (user != null){
           return AjaxResult.fail("此邮箱已被注册，请换邮箱");

        }

        try {
            String validateCode = MyRandomUtil.getRandom(6);

            MailUtil.sendTextMail(email,"邮箱验证","验证码"+validateCode);

            String v1 = String.valueOf(System.currentTimeMillis());
            session.setAttribute("validateCode",validateCode + "_" + v1);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            AjaxResult.fail("发送邮件失败");
        }

        return AjaxResult.success();
    }

    /**
     * 校验注册
     * @param session
     * @param loginName
     * @param password
     * @param validateCode
     * @param userName
     * @param userId
     * @return
     */
    @ResponseBody
    @PostMapping(value = "signUp.do")
    public AjaxResult signUp(HttpSession session ,
                             @RequestParam String loginName,
                             @RequestParam String password,
                             @RequestParam String validateCode,
                             String userName, String userId){

        try {
            String validateCode_session = (String) session.getAttribute("validateCode");

            String validateCodes[] = validateCode_session.split("_");

            log.info(validateCodes[0]);
            log.info(validateCodes[1]);

            long v2 = System.currentTimeMillis();
            long v1 = Long.parseLong(validateCodes[1]);

            long timeOut = v2-v1;

            if (timeOut > (10*60*1000)){
                session.removeAttribute("validateCode");
                return AjaxResult.fail("验证超时，请重新发送");
            }


            if (validateCodes[0] == null || !validateCodes[0].equals(validateCode)){
                return AjaxResult.fail("验证码不正确");
            }

            //如果验证成功销毁
            session.removeAttribute("validateCode");

            //MD5加入盐值（账号密码）存入user表
            String salt = MD5Tools.MD5(MD5Tools.MD5(userId + password));

            userService.addUser(loginName,password,userName,userId,salt);

        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return AjaxResult.fail("注册失败");
        }

        return AjaxResult.success();
    }
}
