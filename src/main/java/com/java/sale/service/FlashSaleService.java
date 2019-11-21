package com.java.sale.service;

import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 秒杀的service
 *
 * @author 曾伟
 * @date 2019/11/21 22:19
 */
@Service
public class FlashSaleService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;

    /**
     * 秒杀操作（原子）。
     *
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public OrderInfo miaosha(User user, GoodsVo goodsVo) {
        //减库存
        //下订单
        //写入秒杀订单
        return null;
    }
}
