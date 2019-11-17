package com.java.sale.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.java.sale.domain.User;
import com.java.sale.redis.RedisService;
import com.java.sale.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 曾伟
 * @date 2019/11/16 11:00
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private RedisService redisService;
    @RequestMapping("/to_list")
    public String toLogin(Model model,
                          @CookieValue(value = UserService.COOKIE_NAME_TOKEN,required = false) String cookieToken,
                          @RequestParam(value = UserService.COOKIE_NAME_TOKEN,required = false) String paramToken){
        //以此判断两种方式传入token
        if (StringUtils.isEmpty(cookieToken)&&StringUtils.isEmpty(paramToken)){
            return "login";
        }
        String token=StringUtils.isEmpty(paramToken)? cookieToken:paramToken;
        System.out.println(token);
        User user = (User) redisService.get(token);
        model.addAttribute("user",user);
        return "goods_list";
    }
}
