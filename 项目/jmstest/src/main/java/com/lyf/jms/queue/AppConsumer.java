package com.lyf.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AppConsumer {
  private final static String url = "tcp://192.168.184.200:61616";
  private final static String queueName = "queue-test";

  public static void main(String[] args) throws JMSException {
    //1. 创建连接工厂
    ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

    //2. 创建连接
    Connection connection = connectionFactory.createConnection();

    //3. 启动连接
    connection.start();

    //4. 创建会话
    Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    //5. 创建目标
    Destination destination = session.createQueue(queueName);

    //6. 创建消费者
    MessageConsumer consumer = session.createConsumer(destination);

    //7. 创建监听器
    consumer.setMessageListener((message) -> {
      //8. 接收消息
      TextMessage textMessage = (TextMessage) message;
      try {
        System.out.println("接收消息: " + textMessage.getText());
      } catch (JMSException e) {
        e.printStackTrace();
      }
    });
  }
}
