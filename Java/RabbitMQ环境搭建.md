---
title: RabbitMQ环境搭建
tags: 消息,RabbitMQ
---

1. AMQP协议，可以跨语言通信 Advance Message Queuing Protocol
2. RabbitMQ底层使用Erlang语言进行编写
3. 开源、性能优秀，稳定
4. 与SpringAMQP完美整合
5. 集群模式丰富，表达式，镜像队列模型


## 1．安装erlang环境

安装依赖环境

``` nginx
yum -y install make gcc gcc-c++ kernel-devel m4 ncurses-devel openssl-devel unixODBC-devel
```

下载最新 Erlang 19.0

``` groovy
wget http://erlang.org/download/otp_src_19.0.tar.gz
```

解压 `tar -xvzf otp_src_19.0.tar.gz`
配置

``` jboss-cli
./configure --prefix=/usr/local/erlang --with-ssl -enable-threads -enable-smmp-support -enable-kernel-poll --enable-hipe --without-javac
```

编译安装 `make && make install`
配置profile `vim /etc/profile`

``` makefile
ERLANG_HOME=/usr/local/erlang
PATH=$PATH:$ERLANG_HOME/bin

export PATH USER LOGNAME MAIL HOSTNAME HISTSIZE HISTCONTROL
```

使其生效 `source /etc/profile`
**重启**,检查erl 

``` shell
[root@root ~]# erl
Erlang/OTP 19 [erts-8.0] [source] [64-bit] [async-threads:10] [hipe] [kernel-poll:false]

Eshell V8.0  (abort with ^G)
```

## 2．安装 rabbitmq

``` shell
wget http://www.rabbitmq.com/releases/rabbitmq-server/v3.6.3/rabbitmq-server-generic-unix-3.6.3.tar.xz

xz -d rabbitmq-server-generic-unix-3.6.3.tar.xz

tar -xvf rabbitmq-server-generic-unix-3.6.3.tar

cd ./rabbitmq_server-3.6.3/sbin/
```

启用web管理界面

``` bash
./rabbitmq-plugins enable rabbitmq_management
```

启动

``` vbscript
./rabbitmq-server -detached
```

添加用户

``` lsl
./rabbitmqctl add_user admin 111111
```

设置权限

``` jboss-cli
./rabbitmqctl set_user_tags admin administrator
```

浏览器访问
http://192.168.37.220:15672

## 3. 资料

参考: https://blog.csdn.net/ALLsharps/article/details/52062416


