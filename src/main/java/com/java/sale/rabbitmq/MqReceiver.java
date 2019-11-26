package com.java.sale.rabbitmq;

import com.java.sale.common.Result;
import com.java.sale.config.MqConfig;
import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.service.FlashSaleService;
import com.java.sale.service.GoodsService;
import com.java.sale.service.OrderService;
import com.java.sale.utils.JsonUtils;
import com.java.sale.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Service
public class MqReceiver {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlashSaleService flashSaleService;


    private static Logger logger = LoggerFactory.getLogger(MqConfig.class);

    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        logger.info(message);
        SaleMessage saleMessage = JsonUtils.stringToBean(message, SaleMessage.class);
        User user = saleMessage.getUser();
        long goodsId = saleMessage.getGoodsId();
        //真正的下单操作

        //判断商品库存
        GoodsVo goodsVo = goodsService.goodsVoById(goodsId);
        if (goodsVo != null) {
            int stock = goodsVo.getStockCount();
            if (stock <= 0) {//库存不足，秒杀失败
                return;
            }
        }
        //判断是否已经秒杀到了
        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getId(), goodsId);
        if (order != null) {//不能重复秒杀
            return;
        }
        //下单，需要事务控制，写在一个service里，使用事务控制。
        //减库存
        //下订单
        //写入秒杀订单
        OrderInfo orderInfo = flashSaleService.miaosha(user, goodsVo);
    }
}
