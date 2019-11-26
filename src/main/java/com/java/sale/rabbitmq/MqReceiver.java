package com.java.sale.rabbitmq;

import com.java.sale.config.MqConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Service
public class MqReceiver {
    private static Logger logger= LoggerFactory.getLogger(MqConfig.class);
    @RabbitListener(queues = MqConfig.QUEUE)
    public void receive(String message){
        logger.info("receiver");
    }
}
