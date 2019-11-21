package com.java.sale.controller;

import com.java.sale.common.CodeMsg;
import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.service.FlashSaleService;
import com.java.sale.service.GoodsService;
import com.java.sale.service.OrderService;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 秒杀的控制器
 *
 * @author 曾伟
 * @date 2019/11/21 21:58
 */
@Controller
@RequestMapping("/miaosha")
public class FlashSaleController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlashSaleService flashSaleService;

    @RequestMapping("do_miaosha")
    public String miaosha(Model model, User user, @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return "login";
        }
        //判断商品库存
        GoodsVo goodsVo = goodsService.goodsVoById(goodsId);
        if (goodsVo!=null){
            int stock=goodsVo.getStockCount();
            if (stock<=0){//库存不足，秒杀失败
                model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER.getMsg());
                return "miaosha_fail";
            }
        }
        //判断是否已经秒杀到了
        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getId(), goodsId);
        if (order!=null){//不能重复秒杀
            model.addAttribute("errmsg",CodeMsg.REPEATE_MIAOSHA);
        }
        //下单，需要事务控制，写在一个service里，使用事务控制。
        //减库存
        //下订单
        //写入秒杀订单
        OrderInfo orderInfo= flashSaleService.miaosha(user,goodsVo);
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goodsVo);
        return "order_detail";
    }
}
