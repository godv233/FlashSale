package com.java.sale.controller;

import com.java.sale.domain.User;
import com.java.sale.rabbitmq.MqSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 曾伟
 * @date 2019/11/26 15:31
 */
@RestController
public class MqController {
    @Autowired
    private MqSender sender;
    @GetMapping("/mq")
    public String mq(){
        sender.send("123");
        return "哈哈";
    }
}
