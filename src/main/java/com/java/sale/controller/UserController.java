package com.java.sale.controller;

import com.java.sale.common.Result;
import com.java.sale.domain.User;
import com.java.sale.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 曾伟
 * @date 2019/10/24 21:03
 */
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @GetMapping("/db")
    @ResponseBody
    private Result<User> getById(){
        User user = userService.getById(1);
        return Result.success(user);
    }

}
