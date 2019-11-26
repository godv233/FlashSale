package com.java.sale.rabbitmq;

import com.alibaba.druid.support.json.JSONUtils;
import com.java.sale.config.MqConfig;
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
    private static Logger logger= LoggerFactory.getLogger(MqConfig.class);
    @Autowired
    private AmqpTemplate amqpTemplate;
    public void send(Object object){
        amqpTemplate.convertAndSend(MqConfig.QUEUE,object);
        logger.info("send");






    }
}
