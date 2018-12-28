---
title: Spring Cloud 2-Feign 声明式服务调用(三)
tags: Spring Cloud,Feign
grammar_cjkRuby: true
---

> [toc]

*简化RestTemplate调用形式*

# 1. pom.xml

``` xml
<!-- feign 声明式服务调用 -->
<dependency>
 <groupId>org.springframework.cloud</groupId>
 <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

# 2. application.yml

``` yaml
spring:
  application:
    name: feign-client
    
server:
  port: 8086
```
# 3. Application.java

``` java
@SpringBootApplication
@EnableFeignClients
public class FeginClientApplication {

 public static void main(String[] args) {
  SpringApplication.run(FeginClientApplication.class, args);
 }

}
```
`@EnableFeignClients` 启动Feign客户端

# 4. Client.java

``` java
@Service
@FeignClient("hello-service")
public interface HelloServiceClient {
  
  @RequestMapping(method = RequestMethod.GET, value = "/hello")
  String hi();
  
}
```
- `@FeignClient("hello-service")` 指定注册服务的名字
- `/hello` 服务请求地址


**注意:**

``` java
@RequestMapping(method = RequestMethod.GET, value = "/hello")
```
1. 不能使用`GetMapping`
2. 需通过`value`指定请求路径


访问:  http://localhost:8086/feign/hi

``` 
Hello World!
```

























