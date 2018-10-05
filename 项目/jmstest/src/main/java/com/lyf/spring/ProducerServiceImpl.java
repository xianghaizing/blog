package com.lyf.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.TextMessage;

@Component
public class ProducerServiceImpl implements ProducerService {

  @Autowired
  private JmsTemplate jmsTemplate;

  // @Resource(name = "topicDestination")
  @Resource(name = "queueDestination")
  private Destination destination;

  @Override
  public void sendMessage(String message) {
    jmsTemplate.send(destination, session -> {
      TextMessage textMessage = session.createTextMessage(message);
      System.out.println("发送消息: " + textMessage.getText());
      return textMessage;
    });
  }
}
