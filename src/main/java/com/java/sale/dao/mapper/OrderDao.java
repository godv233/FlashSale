package com.java.sale.dao.mapper;

import com.java.sale.domain.FlashSaleOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author 曾伟
 * @date 2019/11/21 22:12
 */
@Mapper
public interface OrderDao {
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id #{goodsId}")
    FlashSaleOrder getOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);
}
