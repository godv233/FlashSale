package com.java.sale.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Configuration
public class MqConfig {
    public static final String MIAOSHA_QUEUE = "sale.queue";

    @Bean
    public Queue saleQueue() {
        return new Queue(MqConfig.MIAOSHA_QUEUE, true);
    }
}
