---
title: Spring Cloud 2-Zuul 网关服务(六)
tags: Spring Cloud, Zuul
grammar_cjkRuby: true
---

> [toc]

# 1.pom.xml
``` xml
<!-- zuul 网关服务 -->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
```

# 2.application.yml

``` yaml
spring:
  application:
    name: zuul-proxy

zuul:
  routes: 
    a: 
      path: /a/**
      url: http://localhost:8091/
    b:
      path: /b/**
      serviceId: hystrix-client
      
server:
  port: 8888
```
- 把请求`路径a`代理到http://localhost:8091/ `通过url指定`
- 把请求`路径b`代理到hystrix-client `通过serviceId指定`

# Application.java

``` java
@EnableZuulProxy
@SpringBootApplication
public class ZuulProxyApplication {

 public static void main(String[] args) {
  SpringApplication.run(ZuulProxyApplication.class, args);
 }

}
```

> 访问:  http://localhost:8888/a/hystrix/he 
Hello World!
访问:  http://localhost:8888/b/hystrix/he 
Hello World!