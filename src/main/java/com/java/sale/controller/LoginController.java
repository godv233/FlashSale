package com.java.sale.controller;

import com.java.sale.common.CodeMsg;
import com.java.sale.common.Result;
import com.java.sale.service.UserService;
import com.java.sale.utils.ValidatorUtils;
import com.java.sale.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾伟
 * @date 2019/10/26 15:30
 */
@Controller
public class LoginController {

    private static Logger logger= LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private UserService userService;

    @GetMapping("/to_login")
    public String toLogin(){
        return "login";
    }
    @PostMapping("/login/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(LoginVo loginVo){
        logger.info(loginVo.toString());
        //参数校验
        String passInput=loginVo.getPassword();
        String mobile=loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtils.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }
        //登录
        CodeMsg codeMsg = userService.login(loginVo);
        if (codeMsg.getCode()==0){
            return Result.success(true);
        }else {
            return Result.error(codeMsg);
        }

    }
}
