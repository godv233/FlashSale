package com.java.sale.service;

import com.java.sale.dao.mapper.OrderDao;
import com.java.sale.domain.FlashSaleOrder;
import com.java.sale.domain.OrderInfo;
import com.java.sale.domain.User;
import com.java.sale.redis.OrderKey;
import com.java.sale.redis.RedisService;
import com.java.sale.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 订单service
 * @author 曾伟
 * @date 2019/11/21 22:10
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;
    /**
     * 通过userid和goodsId得到订单
     * @param userId
     * @param goodsId
     * @return
     */
    public FlashSaleOrder orderByUserIdGoodsId(Long userId, long goodsId) {
        //判断数据库中时候已经有了订单。
//        return orderDao.getOrderByUserIdGoodsId(userId,goodsId);
        //将订单的信息存入redis。判断的时候直接访问redis而不是数据库。
        FlashSaleOrder order = (FlashSaleOrder) redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + goodsId);
        return order;


    }

    /**
     * 下订单
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
        //一般通过程序判断:这里直接写死
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderDao.insert(orderInfo);
        //秒杀订单
        FlashSaleOrder flashSaleOrder=new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsVo.getId());
        flashSaleOrder.setOrderId(orderInfo.getId());
        flashSaleOrder.setUserId(user.getId());
        orderDao.flashOrder(flashSaleOrder);
        boolean set = redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getId() + goodsVo.getId(), flashSaleOrder);
        System.out.println(set);
        return orderInfo;
    }

    /**
     * 通过id得到订单详情
     * @param orderId
     * @return
     */
    public OrderInfo getOrderById(long orderId) {
        return orderDao.getOrderById(orderId);
    }
}
