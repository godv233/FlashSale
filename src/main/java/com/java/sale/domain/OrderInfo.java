package com.java.sale.domain;

import lombok.Data;

import java.util.Date;

/**
 * 订单详情
 * @author 曾伟
 * @date 2019/11/21 19:49
 */
@Data
public class OrderInfo {
    private Long id;
    private Long userId;
    private Long goodsId;
    private Long  deliveryAddrId;
    private String goodsName;
    private Integer goodsCount;
    private Double goodsPrice;
    private Integer orderChannel;
    private Integer status;
    private Date createDate;
    private Date payDate;
}
