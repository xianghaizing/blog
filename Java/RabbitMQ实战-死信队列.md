---
title: RabbitMQ实战-死信队列
tags: RabbitMQ,死信队列
grammar_cjkRuby: true
---

>[toc]

# 场景说明

场景: 当队列的消息未正常被消费时,如何解决?
1. 消息被拒绝并且不再重新投递
2. 消息超过有效期
3. 队列超载


方案: **未被消费的消息,可通过"死信队列"重新被消费**

死信队列含义,发生以上情况时,该队列上的消息,可通过配置转发到死信队列,被重新消费

模拟实现:

1. 1个生产者,2个交换机和队列(普通和死信),1个消费者(死信消费者)
2. 通过消息超时,模拟未正常消费场景
3. 启动死信队列消费者,等待消息...
4. 启动生产者,绑定死信队列并发送消息
5. 消息超时后,由死信队列消费者消费

# 代码实现
## 简单的Util

``` java
package com.lyf.springboot.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MqUtil {

    private static Connection connection = null;
    private static Channel channel = null;

    /**
     * 获取channel
     * @return
     */
    public static Channel getChannel(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.37.200");
        factory.setUsername("lyf");
        factory.setPassword("123456");
        factory.setVirtualHost("/lyf");
        try {
            connection = factory.newConnection();
            channel = connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return channel;
    }


    /**
     * 关闭channel和connection
     */
    public static void close(){
        try {
            if(channel != null){
                channel.close();
            }
            if(connection != null){
                connection.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}

```

## 生产者

``` java
package com.lyf.springboot.mq;

import com.lyf.springboot.utils.MqUtil;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Sender {
    private static String QUEUE_NAME="hello";
    private static String EXCHANGE_NAME="exchange";

    private static String DL_EXCHANGE_NAME="dl_exchange";

    public static void main(String []args) throws IOException {
        Channel channel = MqUtil.getChannel();

        // 普通队列
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        Map<String, Object> arguments = new HashMap<>();
        /*--------------↓↓↓最关键一步,设置队列的死信队列↓↓↓----------------*/
        // x-dead-letter-exchange属性用于指定死信队列
        arguments.put("x-dead-letter-exchange", DL_EXCHANGE_NAME);
        channel.queueDeclare(QUEUE_NAME,false,false,false,arguments);
        /*--------------↑↑↑最关键一步,设置队列的死信队列↑↑↑----------------*/
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"info");

        // 设置超时时间5000ms
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("5000").build();
        String msg = "hello";
        channel.basicPublish(EXCHANGE_NAME, "info", properties, msg.getBytes());
        System.out.println("Se: " + msg);

        MqUtil.close();
    }
}

```

## 消费者

``` java
package com.lyf.springboot.mq;

import com.lyf.springboot.utils.MqUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class Dl_Reciver {
    private static String DL_EXCHANGE_NAME="dl_exchange";
    private static String DL_QUEUE_NAME="dl_hello";



    public static void main(String []args) throws IOException {
        Channel channel = MqUtil.getChannel();

        channel.exchangeDeclare(DL_EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        channel.queueDeclare(DL_QUEUE_NAME,false,false,false,null);
        channel.queueBind(DL_QUEUE_NAME,DL_EXCHANGE_NAME,"#");
        // 消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "utf-8");
                System.out.println("DL_Re: " + msg);
            }
        };
        channel.basicConsume(DL_QUEUE_NAME,false,consumer);
    }
}

```

启动顺序: 先启动消费者监听,后启动生产者.消息5s后被死信队列消费

参考:
- https://www.cnblogs.com/gaoyuechen/p/8693902.html
- https://my.oschina.net/10000000000/blog/1626278