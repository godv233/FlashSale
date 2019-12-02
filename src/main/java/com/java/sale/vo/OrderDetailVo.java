package com.java.sale.vo;

import com.java.sale.domain.OrderInfo;
import lombok.Data;

/**
 * 订单详情
 */
@Data
public class OrderDetailVo {
	private GoodsVo goods;
	private OrderInfo order;
}
