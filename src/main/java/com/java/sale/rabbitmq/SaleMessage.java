package com.java.sale.rabbitmq;

import com.java.sale.domain.User;
import lombok.Data;

/**
 * 消息队列中存放的对象
 * @author 曾伟
 * @date 2019/11/26 17:16
 */
@Data
public class SaleMessage {
    private User user;
    private long goodsId;
}
