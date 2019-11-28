package com.java.sale.domain;

import lombok.Data;

/**
 * 普通商品
 * @author 曾伟
 * @date 2019/11/21 19:43
 */
@Data
public class Goods {
    private long id;
    private String goodsName;
    private String goodsTitle;
    private String goodsImg;
    private String goodsDetail;
    private Double goodsPrice;
    private Integer goodsStock;//库存
}
