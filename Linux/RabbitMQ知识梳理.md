---
title: RabbitMQ知识梳理 
tags: RabbitMQ
grammar_cjkRuby: true
---

> [toc]

# 基本概念

生产者和消费者、队列、交换机、路由键、绑定

## 交换机类型：
- fanout 广播模式，效率高，不处理路由键
- direct 路由模式，处理路由键，1vs1
- topic 主题模式，匹配路由键，1vs多
- headers模式，效率差，不用


## RabbitMQ 运转流程：

生产者发送消息过程：
1. 连接到 RabbitMQ Broker 建立一个连接( Connection) ，开启信道 (Channel)
2. 声明交换器 ，设置相关属性
3. 声明队列，设置相关属性
4. 通过路由键将交换器和队列绑定起来
5. 发送消息至 RabbitMQ Broker ，其中包含路由键、交换器等信息
6. 相应的交换器根据接收到的路由键查找相匹配的队列
7. 如果找到 ，则将从生产者发送过来的消息存入相应的队列中
8. 如果没有找到 ，则根据生产者配置的属性选择丢弃还是回退给生产者
9. 关闭信道和连接

消费者接收消息的过程:
1. 连接到 RabbitMQ Broker ，建立一个连接(Connection ，开启 个信道(Channel)
2. 向 RabbitMQ Broker 请求消费相应队列中的消息，可能会设置相应的回调函数，以及做些准备工作
3. 等待 RabbitMQ Broker 回应并投递相应队列中的消息， 消费者接收消息
4. 消费者确认（ack）接收到的消息
5. RabbitMQ 从队列中删除相应己经被确认的消息
6. 关闭信道和连接

## AMQP协议

即Advanced Message Queuing Protocol，一个提供统一消息服务的应用层标准高级消息队列协议

# 入门使用

## 安装环境:
1. windows安装（最简单，适合初学者）
2. linux安装（标准和推荐方式，生产使用）
3. docker安装（最最简单，前提是会docker）

## 交换机和队列:
- exchangeDeclare 声明交换机
- queueDeclare 声明队列
- queueBind 队列绑定
- exchangeBind 交换机绑定
- basicPublish 发送消息
- basicConsume 消费消息
- basicAck 消息确认
- basicReject 拒绝1条
- basicNack 拒绝多条

# 进阶使用

## 消息去从
1. 正常消费，从队列中移除
2. 被拒绝(Reject或Nack)，进入死信队列
3. 未被确认confirm，重回队列（mandatory、basicRecover）
4. 消息超时TTL（Time to Live 的简称，即过期时间），进入死信队列

## 消息确认投递
- 生产者通过事务确认，性能低
- 消费者通过confirm确认，推荐

## 消息防止丢失

1. 备份交换机,Altemate Exchange ，简称AE
``` java
Map<String, Object> args = new HashMap<String , Object>(); 
args.put("a1ternate-exchange" , "myAe"); 
channe1.exchangeDec1are( "norma1Exchange" , "direct" , true , fa1se , args); 
channe1.exchangeDec1are( "myAe " , "fanout" , true , fa1se , nu11) ; 
channe1.queueDec1are( "norma1Queue " , true , fa1se , fa1se , nu11); 
channe1.queueB nd( norma1Queue "norma1Exchange" , " norma1Key"); 
channe1.queueDec1are( "unroutedQueue " , true , fa1se , fa1se , nu11);
channel.queueBind( "unroutedQueue " , "myAe " , "");
```
2. 死信队列
3. 镜像队列
4. 持久化 druable

## 过期时间 (TTL)
1. 队列设置, 队列中所有消息都有相同的过期时间

``` java
Map<String, Object> argss = new HashMap<String , Object>(); 
argss.put("x-message-ttl " , 6000); 
channel . queueDeclare(queueName , durable , exclusive , autoDelete , argss) ;
```

2. 消息设置,对消息本身进行单独设置，每条消息的 TTL 可以不同

``` java
AMQP.BasicProperties properties = new AMQP.BasicProperties() ; 
properties.setExpiration("60000"); 
channel . basicPublish (exchangeName, routingKey, mandatory, propertes, "ttlTestMessage".getBytes());
```

## 消息分发
- 轮询分发(round-robin )，默认
- 均衡分发，channel.basicQos(1)

# 高级队列

## 1.死信队列

DLX ，全称为 Dead-Letter-Exchange ，可以称之为死信交换器，也有人称之为死信邮箱。当
消息在一个队列中变成死信 (dea message) 之后，它能被重新被发送到另一个交换器中，这个
交换器就是 DLX ，绑定 DLX 的队列就称之为死信队列。

消息变成死信 般是由于以下几种情况:
1、消息被拒绝 (Basic.Reject/Basic .Na ck) ，井且设置 requeue 参数为 alse;
2、消息过期;
3、令队列达到最大长度。

``` java
channel . exchangeDeclare("dlx_exchange " , "dir ect "); // 创建 DLX: dlx_exchange 
Map<String, Object> args = new HashMap<String, Object>(); 
args . put("x-dead-letter-exchange" , " dlx exchange "); 
//为队列 myqueue 添加 DLX
channel . queueDeclare("myqueue" , false , false , false , args); 
也可以为这个 DLX 指定路由键，如果没有特殊指定，则使用原队列的路由键:
args.put("x-dead-letter-routing-key" , "dlx-routing-key");
```
## 2.延迟队列
通过DLX死信队列和TTL时间过期模拟出延迟队列的功能。

## 3.优先队列
优先级高的消息具备优先被消费的特权，默认最低为0。如果消费速度大于生产速度，则没有意义

``` java
Map<String, Object> args =new HashMap<String, Object>() ; 
args . put( "x-rnax-priority" , 10) ; 
channel.queueDeclare( " queue . priority" , true , fa1se , false , args) ;
```
## 4.镜像队列

参考书籍:
《RabbitMQ 实战指南》朱忠华 著 

![][1]


  [1]: ./images/1573806351961.jpg "RabbitMQ 实战指南"