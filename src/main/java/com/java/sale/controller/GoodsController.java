package com.java.sale.controller;

import com.java.sale.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 曾伟
 * @date 2019/11/16 11:00
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @RequestMapping("/to_list")
    public String toLogin(Model model, User user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
