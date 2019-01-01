---
title: Spring Cloud 2-Hystrix 断路容错保护(四)
tags: Spring Cloud, Hystrix
grammar_cjkRuby: true
---

> [toc]

容错保护就是当请求的服务报错或者超时时,可以优雅降级.介绍两种实现:

 - RestTemplate 容错 
 - FeignClient 容错

# 1.RestTemplate 容错

## pom.xml

``` xml
<!-- hystrix 断路器 -->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

## application.yml

``` yaml
spring:
  application:
    name: hystrix-client
    
server:
  port: 8091
```

## application.java

``` java
@EnableCircuitBreaker
@SpringBootApplication
public class HystrixClientApplication {
 
 @Bean
 @LoadBalanced
 RestTemplate restTemplate(){
  return new RestTemplate();
 }
 
 public static void main(String[] args) {
  SpringApplication.run(HystrixClientApplication.class, args);
 }

}
```
`@EnableCircuitBreaker` 开启断路器功能

## HelloService.java

``` java
@Service
public class HelloService {

  @Autowired
  private RestTemplate template;
  
  @HystrixCommand(fallbackMethod = "errorCallback")
  public String hello(){
    return template.getForObject("http://HELLO-SERVICE/hello",String.class);
  }

  public String errorCallback(){
    return "error";
  }
  
}
```

- `@HystrixCommand(fallbackMethod = "errorCallback") fallbackMethod` 指定报错回调 
- `errorCallback` 错误回调方法

Controller.java

## Controller.java

``` java
@RestController
@RequestMapping("hystrix")
public class HystrixHelloController {

  @Autowired
  private HelloService helloService;
  
  @GetMapping("hi")
  public String hi(){
    return helloService.hello();
  }
}
```

> 访问: http://localhost:8091/hystrix/hi
Hello World!
关闭服务在访问
访问: http://localhost:8091/hystrix/hi
error

# 2.FeignClient 容错

## pom.xml

``` xml
<!-- feign 声明式服务调用 -->
<dependency>
   <groupId>org.springframework.cloud</groupId>
   <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

## application.yml

``` yaml
spring:
  application:
    name: hystrix-client
    
server:
  port: 8091

feign:
  hystrix:
    enabled: true
```

## Application.java

``` java
@EnableFeignClients
@EnableCircuitBreaker
@SpringBootApplication
public class HystrixClientApplication {
 
 @Bean
 @LoadBalanced
 RestTemplate restTemplate(){
  return new RestTemplate();
 }
 
 public static void main(String[] args) {
  SpringApplication.run(HystrixClientApplication.class, args);
 }

}
```
必须同时开启断路器`@EnableCircuitBreaker`和feign客户端`@EnableFeignClients`


## ServiceClient.java

``` java
@FeignClient(value = "hello-service", fallback = HystrixClientFallback.class)
public interface HelloServiceClient {
  
  @RequestMapping(method = RequestMethod.GET, value = "/hello")
  String hello();
  
}
```

`fallback` 指定回调的类

## Fallback.java

``` java
@Component
public class HystrixClientFallback implements HelloServiceClient {

  @Override
  public String hello() {
    return "error-feign";
  }
  
}
```

> 访问: http://localhost:8091/hystrix/he
Hello World!
关闭服务再访问:
访问: http://localhost:8091/hystrix/he
error-feign



































