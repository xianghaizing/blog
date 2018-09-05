---
title: Redis环境搭建
grammar_cjkRuby: true
---

在Linux环境中搭建Redis
1.在`/usr`文件夹中新建一个文件夹(如:soft),开始下载redis

``` shell
$ wget http://download.redis.io/releases/redis-2.8.17.tar.gz #下载
$ tar xzf redis-2.8.17.tar.gz # 解压
$ cd redis-2.8.17 #切换到目录
$ make #执行编译
```

编译过程中报错的话,需要安装gcc, 但依然不能直接使用make编译需要指定编译库

``` shell
$ yum -y install gcc #-y直接安装不需要确认
$ make MALLOC=libc #执行编译
```

解决办法安装tcl

``` shell
$ yum install tcl
```


2.启动服务

``` shell
$ cd src
$ ./redis-server
```

三种启动方式:

 1. 直接启动,加&后台启动, `./redis-server &` 
 2. 指定配置配件文件启动, `./redis-server ../redis.conf -p 6380` 
 3. linux开机自动启动

3.启动客户端

``` shell
$ cd src
$ ./redis-cli
```

4.执行操作

``` shell
redis> set foo bar
OK
redis> get foo"bar"
```