---
title: Spring Cloud 2.x-前言
tags: Spring Cloud,Spring Boot
grammar_cjkRuby: true
---

> [toc]

微服务已经成为企业开发的标配,阿里的Dubbo已经被企业大规模使用,Spring Cloud作为后起之秀,同时也是 `Spring全家桶`Spring家族的成员,未来前景一片光明,是时候开始研究学习了.

网上大部分文章都是基于Spring Cloud 1.x和Spring Boot 1.x进行搭建和介绍,Spring Cloud Finchley的`Service Release 2 (SR2) 版本`已发布,那就直接上手2.x. **Spring Cloud Finchley 基于 Spring Boot 2.0.x 而构建**.

查看[发布说明](https://github.com/spring-projects/spring-cloud/wiki/Spring-Cloud-Finchley-Release-Notes)以了解更多信息，可从 [Maven中央仓库](https://repo1.maven.org/maven2/org/springframework/cloud/spring-cloud-dependencies/Finchley.SR2/spring-cloud-dependencies-Finchley.SR2.pom)获取源码。

**已升级的模块**

| 模块 | 版本 |
| --- | --- |
| Spring Cloud Gateway | 2.0.2.RELEASE |
| Spring Cloud Sleuth | 2.0.2.RELEASE |
| Spring Cloud Config | 2.0.2.RELEASE |
| Spring Cloud Netflix | 2.0.2.RELEASE |
| Spring Cloud Commons | 2.0.2.RELEASE |
| Spring Cloud Contract | 2.0.2.RELEASE |
| Spring Cloud Vault | 2.0.2.RELEASE |
| Spring Cloud Openfeign | 2.0.2.RELEASE |
| Spring Cloud AWS | 2.0.1.RELEASE |
| Spring Cloud Cloud Foundry | 2.0.1.RELEASE |
| Spring Cloud Security | 2.0.1.RELEASE |

其他详细更新内容[请查看发布公告](https://spring.io/blog/2018/10/24/spring-cloud-finchley-sr2-is-available)，主要是各模块的 bug 修复。

> GA: General Availability,正式发布的版本，官方推荐使用此版本。。
RELEASE: 正式版，等价于GA
SNAPSHOT: 快照版，可以稳定使用，且仍在继续改进版本。
PRE: 预览版,内部测试版. 主要是给开发人员和测试人员测试和找BUG用的，不建议使用；

**模块介绍**

| 模块 | 功能 |
| --- | --- |
| Spring Cloud Netflix| 与各种Netflix OSS组件集成（Eureka，Hystrix，Zuul，Archaius等）。 |
|  Spring Cloud Eureka | 服务发现和注册中心,统一管理服务 |
|  Spring Cloud Ribbon | 客户端RestTemplate负载均衡组件 |
|  Spring Cloud Feign | 声明式服务调用,简化RestTemplate使用 |
|  Spring Cloud Hystrix | 断路容错保护组件,实现优雅降级 |
|  Spring Cloud Hystrix Dashboard | 监控仪表盘组件,监测节点和集群的请求 |
|  Spring Cloud Zuul | 网关服务,映射服务请求路径 |
|  Spring Cloud Config | 分布式配置中心,统一管理所有项目配置 |
|  Spring Cloud Bus | 消息总线,批量刷新服务配置 |