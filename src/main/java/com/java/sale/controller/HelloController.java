package com.java.sale.controller;

import com.java.sale.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾伟
 * @date 2019/10/24 20:29
 */
@Controller
public class HelloController {
    @GetMapping("/hello")
    @ResponseBody
    public User hello(User user){
        return user;
    }

}
