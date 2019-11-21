package com.java.sale.domain;

import lombok.Data;

import java.util.Date;

/**
 * 秒杀商品。
 * @author 曾伟
 * @date 2019/11/21 19:46
 */
@Data
public class FlashSaleGoods {
    private Long id;
    private Long goodsId;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
