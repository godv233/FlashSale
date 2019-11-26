package com.java.sale.rabbitmq;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.java.sale.config.MqConfig;
import com.java.sale.redis.RedisService;
import com.java.sale.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/11/26 15:06
 */
@Service
public class MqSender {
    private static Logger logger = LoggerFactory.getLogger(MqConfig.class);
    @Autowired
    private AmqpTemplate amqpTemplate;
//    public void send(Object object){
//        amqpTemplate.convertAndSend(MqConfig.QUEUE,object);
//        logger.info("send");
//    }

    /**
     * 生产者
     * @param message
     */
    public void miaoshaMessage(SaleMessage message) {
        String msg= JsonUtils.beanToString(message);
        logger.info(msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE,msg);
    }
}
