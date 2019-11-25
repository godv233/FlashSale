package com.java.sale.vo;

import com.java.sale.domain.OrderInfo;
import lombok.Data;

@Data
public class OrderDetailVo {
	private GoodsVo goods;
	private OrderInfo order;
}
