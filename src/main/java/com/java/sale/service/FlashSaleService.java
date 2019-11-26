package com.java.sale.service;

import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.redis.MiaoshaKey;
import com.java.sale.redis.RedisService;
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
    @Autowired
    private RedisService redisService;

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
        boolean success = goodsService.reduceStock(goodsVo);
        if (success) {
            //下订单
            //写入秒杀订单
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }


    /**
     * 查询秒杀的结果
     *
     * @param id
     * @param goodsId
     * @return
     */
    public long getMiaoshaResult(Long userId, long goodsId) {
        FlashSaleOrder flashSaleOrder = orderService.orderByUserIdGoodsId(userId, goodsId);
        if (flashSaleOrder != null) {//秒杀成功
            return flashSaleOrder.getOrderId();
        } else {
            boolean over = getGoodsOver(goodsId);
            if (over) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    private void setGoodsOver(long goodsId) {
        redisService.set(MiaoshaKey.goodsOver, "" + goodsId, true);
    }

    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.goodsOver, "" + goodsId);
    }
}
