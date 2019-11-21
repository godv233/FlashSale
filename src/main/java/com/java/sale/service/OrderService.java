package com.java.sale.service;

import com.java.sale.dao.mapper.OrderDao;
import com.java.sale.domain.FlashSaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/11/21 22:10
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;

    public FlashSaleOrder orderByUserIdGoodsId(Long userId, long goodsId) {

        return orderDao.getOrderByUserIdGoodsId(userId,goodsId);
    }
}
