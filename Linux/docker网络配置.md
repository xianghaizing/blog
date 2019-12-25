---
title: docker网络配置
tags: docker,net
renderNumberedHeading: true
grammar_cjkRuby: true
grammar_code: true
---


>[toc]

# 网络模式
docker网络模式有四种:
- bridge模式,默认模式
- host模式,与主机共享ip
- container模式,与指定容器共享ip,是对host模式的补充
- none模式,独立网络

使用方式: `--net=网络模式`
- host: `--net=host`
- container: `--net=container:容器名或者id`

# 示例配置

``` yml
version: '3'
services:
  eureka:
    build: ./eureka
    image: lin-eureka:1
    container_name: lin-eureka
    restart: always
    network_mode: host
  zuul:
    build: ./zuul
    image: lin-zuul:1
    container_name: lin-zuul
    restart: always
    network_mode: host
  redis:
    build: ./redis
    image: lin-redis:1
    container_name: lin-redis
    restart: always
    network_mode: host
  redis-server:
    image: redis:latest
    container_name: lin-redis-server
    restart: always
    network_mode: host
```