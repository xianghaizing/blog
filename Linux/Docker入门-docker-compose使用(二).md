---
title: Docker入门-docker-compose使用(二)
tags: Docker
grammar_cjkRuby: true
---

Docker容器大行其道,直接通过 docker pull + 启动参数的方式运行比较麻烦, 可以通过docker-compose插件快速创建容器

1.安装docker-compose

- 安装

```
curl -L "https://github.com/docker/compose/releases/download/1.23.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose

``` 
- 添加权限
`
chmod +x /usr/local/bin/docker-compose
`

- 查看
``` 
$ docker-compose --version
docker-compose version 1.23.1, build 1719ceb
```
- 删除
`
rm /usr/local/bin/docker-compose
`


2.创建docker-compose.yml

```
version: '3.1'
services:
    zoo1:
        image: zookeeper
        restart: always
        hostname: zoo1
        ports:
            - 2181:2181
        environment:
            ZOO_MY_ID: 1
            ZOO_SERVERS: server.1=zoo1:2888:3888
```

3.执行自动安装部署

`
docker-compose up -d
`
使用`-d`后台运行容器

```
[root@localhost zook]# systemctl start firewalld.service
[root@localhost zook]# docker-compose up -d
Creating network "zook_default" with the default driver
Creating zook_zoo1_1_5803e136833d ... done
```
4.连接容器

```
[root@localhost zook]# docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                                        NAMES
3efbee2be660        zookeeper           "/docker-entrypoint.…"   2 minutes ago       Up 2 minutes        2888/tcp, 0.0.0.0:2181->2181/tcp, 3888/tcp   zook_zoo1_1_bad722f020ce
[root@localhost zook]# docker exec -it 3efbee2be660 /bin/bash

```

5.启动zookeeper服务

```
bash-4.4# ./bin/zkCli.sh -server 127.0.0.1:2181
Connecting to 127.0.0.1:2181
2018-12-09 16:10:10,644 [myid:] - INFO  [main:Environment@100] - Client environment:zookeeper.version=3.4.13-2d71af4dbe22557fda74f9a9b4309b15a7487f03, built on 06/29/2018 04:05 GMT
2018-12-09 16:10:10,649 [myid:] - INFO  [main:Environment@100] - Client environment:host.name=zoo1
...
2018-12-09 16:10:10,859 [myid:] - INFO  [main-SendThread(localhost:2181):ClientCnxn$SendThread@879] - Socket connection established to localhost/127.0.0.1:2181, initiating session
[zk: 127.0.0.1:2181(CONNECTING) 0] 2018-12-09 16:10:10,922 [myid:] - INFO  [main-SendThread(localhost:2181):ClientCnxn$SendThread@1303] - Session establishment complete on server localhost/127.0.0.1:2181, sessionid = 0x100018a493b0000, negotiated timeout = 30000
```

6.客户端验证

```
bash-4.4# ./bin/zkServer.sh status
ZooKeeper JMX enabled by default
Using config: /conf/zoo.cfg
Mode: standalone
``` 

`docker-compose up`会优先使用已有的容器，而不是重新创建容器。

下面的方法可以解决你的问题：

1. `docker-compose up -d --force-recreate `

使用 `--force-recreate` 可以强制重建容器,否则只能在容器配置有更改时才会重建容器

2. `docker-compose down` 

停止所有容器，并删除容器,下次使用docker-compose up时就一定会是新容器了



