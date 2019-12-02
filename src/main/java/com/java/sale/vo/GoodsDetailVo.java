package com.java.sale.vo;

import lombok.Data;
import com.java.sale.domain.User;

/**
 * 商品详情
 */
@Data
public class GoodsDetailVo {
    private int miaoshaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private User user;
}
