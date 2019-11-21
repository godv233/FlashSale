package com.java.sale.controller;

import com.java.sale.domain.User;
import com.java.sale.service.GoodsService;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author 曾伟
 * @date 2019/11/16 11:00
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    /**
     * 跳转商品列表，并携带商品数据
     *
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/to_list")
    public String toLogin(Model model, User user) {
        model.addAttribute("user", user);
        //查询商品列表
        List<GoodsVo> goodsVoList = goodsService.goodsVoList();
        model.addAttribute("goodslist", goodsVoList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);
        GoodsVo goodsVo = goodsService.GoodsVoById(goodsId);
        model.addAttribute("goods", goodsVo);
        //
        long startTime = goodsVo.getStartDate().getTime();
        long endTime = goodsVo.getEndDate().getTime();
        long currentTime = System.currentTimeMillis();
        int miaoshaStatus = 0;
        int remainSeconds=0;
        if (startTime > currentTime) {//秒杀还未开始
            miaoshaStatus = 0;
            remainSeconds= (int) (startTime-currentTime)/1000;
        } else if (endTime < currentTime) {//秒杀已结束
            miaoshaStatus=2;
            remainSeconds=-1;
        } else {//秒杀正在进行中
            miaoshaStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }


}
