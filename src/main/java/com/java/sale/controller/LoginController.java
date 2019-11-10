package com.java.sale.controller;

import com.java.sale.common.Result;
import com.java.sale.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾伟
 * @date 2019/10/26 15:30
 */
@Controller
public class LoginController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);

    @GetMapping("/to_login")
    public String toLogin(){
        return "login";
    }
    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        logger.info(loginVo.toString());
        //参数校验

        return Result.success(true);
    }
}
