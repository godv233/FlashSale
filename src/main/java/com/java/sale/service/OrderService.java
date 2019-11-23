package com.java.sale.service;

import com.java.sale.dao.mapper.OrderDao;
import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author 曾伟
 * @date 2019/11/21 22:10
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    /**
     * 通过userid和goodsId得到订单
     * @param userId
     * @param goodsId
     * @return
     */
    public FlashSaleOrder orderByUserIdGoodsId(Long userId, long goodsId) {

        return orderDao.getOrderByUserIdGoodsId(userId,goodsId);
    }

    /**
     * 下订单，分程
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public OrderInfo createOrder(User user, GoodsVo goodsVo) {
        //下订单
        OrderInfo orderInfo=new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
        orderInfo.setOrderChannel(1);//通过程序判断
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId= orderDao.insert(orderInfo);
        //秒杀订单
        FlashSaleOrder flashSaleOrder=new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsVo.getId());
        flashSaleOrder.setOrderId(orderId);
        flashSaleOrder.setUserId(user.getId());
        orderDao.FlashOrder(flashSaleOrder);
        return orderInfo;
    }
}
