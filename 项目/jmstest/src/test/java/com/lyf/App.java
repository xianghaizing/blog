package com.lyf;

import com.lyf.spring.ProducerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml"})
public class App {
  
  @Autowired
  private ProducerServiceImpl producerService;
  
  @Test
  public void producer(){
    for (int i = 0; i < 100; i++) {
      producerService.sendMessage("test_"+i);
    }
  }
}
