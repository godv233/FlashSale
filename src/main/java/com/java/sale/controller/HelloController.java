package com.java.sale.controller;

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
    public String hello(){
        return "hello";
    }

}
