---
title: 使用Feign通过服务名调用服务,找不到服务
tags: fegin,eureka
renderNumberedHeading: true
grammar_cjkRuby: true
grammar_code: true
---

报错环境:
1. eureka注册中心在远程服务器上
2. 本地服务**注册到远程**的eureka注册中心
3. 本地服务通过**Fegin组件+服务名**调用服务

报错时,注册中心的情况:

``` tab
Application				Status
SERVICE-HOT			>>==192.168.22.180:8308==<<
SERVICE-REDIS		>>==127.0.0.1:4046==<<
SERVICE-ZUUL		>>==127.0.0.1:4041==<<
```

报错情况:


``` java
feign.RetryableException: >>==connect timed out executing==<< GET http://SERVICE-REDIS/redis/NaviKey-24149-1
	at feign.FeignException.errorExecuting(FeignException.java:84)
	at feign.SynchronousMethodHandler.executeAndDecode(SynchronousMethodHandler.java:113)
```


排查后发现:**是因为注册服务ip问题**,导致本地服务无法通过服务名字找到相应的服务.

1. 远程项目和eureka服务部署在同一台服务器上,所以通过127.0.0.1去服务中心注册服务是没问题的
2. 本地项目通过远程服务器ip来注册服务,也没问题
3. 在同一个注册中心上,可以互相发现服务,就是通过名字可以找到对方,没错
4.  问题在于, 当本地**SERVICE-HOT**服务在eureka中心找到**SERVICE-REDIS**服务后,redis服务告诉他,我的服务ip地址是`127.0.0.1:4046`.那么,这就有问题了!!!
5.  本地服务和远程服务不在一台服务器上,你的`127.0.0.1`是自己的服务器ip,本地服务肯定找不到
6.  所以,注册服务时,统一都使用服务器ip来注册就行了,修改后的配置中心如下:

``` tab
Application				Status
SERVICE-HOT			>>==192.168.22.180:8308==<<
SERVICE-REDIS		>>==192.168.22.180:4046==<<
SERVICE-ZUUL		>>==192.168.22.180:4041==<<
```

不修改服务注册配置,还有一种办法: **使用fegin时,通过url指定访问服务的ip**

``` java
@FeignClient(value = "SERVICE-REDIS",
        >>==url="http://192.168.22.180:4041/service-redis"==<< ,
        fallback = RedisSvcFallback.class)
public interface RedisSvc {
```