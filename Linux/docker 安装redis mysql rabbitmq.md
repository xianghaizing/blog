---
title: docker 安装redis mysql rabbitmq
tags: docker,redis,mysql,rabbitmq
grammar_cjkRuby: true

---

>[toc]

# 基本命令

命令格式: **docker 命令 [镜像/容器]名字**

常用命令:
>search	查询镜像
pull 拉取镜像
run 创建新容器并运行
start/stop/restart 启动/停止/重启容器
rm 删除容器
ps 列出容器 -a 列出所有容器包括已停止的
rmi 删除镜像

# 安装redis
`docker pull redis`

`docker run -d -p 6379:6379 --name myredis redis`

- --d 后台运行
- -p 指定端口映射 **主机端口:容器端口**
- --name 容器别名,方便后续使用


# 安装mysql
`docker pull mysql:5.6`

`docker run -d -p 3306:3306 --name mymysql -e MYSQL_ROOT_PASSWORD=123456 mysql:5.6`

- **-e MYSQL_ROOT_PASSWORD=123456** 初始化 root 用户的密码



# 安装rabbitmq
`docker pull rabbitmq:management`

`docker run -d -p 5672:5672 -p 15672:15672 --name rabbitmq rabbitmq:management`

- 默认用户`guest`密码`guest`
- 访问地址: http://ip:15672

