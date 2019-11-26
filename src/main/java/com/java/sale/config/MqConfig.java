package com.java.sale.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Configuration
public class MqConfig {
    public static final String QUEUE = "queue";
    public static final String TOPIC_QUEUE1 = "topic.q1";
    public static final String TOPIC_QUEUE2 = "topic.q2";

    /**
     * direct模式。直接使用队列相等
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("queue", true);
    }


    /**
     * topic模式,模糊匹配
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(MqConfig.TOPIC_QUEUE1, true);

    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(MqConfig.TOPIC_QUEUE2, true);
    }
}
