package com.java.sale.vo;

import com.java.sale.domain.Goods;
import lombok.Data;

import java.util.Date;

/**
 * @author 曾伟
 * @date 2019/11/21 20:04
 */
@Data
public class GoodsVo  extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
