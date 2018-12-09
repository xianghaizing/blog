---
title: Docker入门-安装(一)
tags: Docker
grammar_cjkRuby: true
---

在CentOS 7.0下安装Docker, CentOS 7.0默认使用的是firewall作为防火墙
- 查看防火墙状态
    `firewall-cmd --state`
- 停止firewall
    `systemctl stop firewalld.service`
- 启动firewall
    `systemctl start firewalld.service`
- 禁止firewall开机启动
    `systemctl disable firewalld.service`


1.移除旧版本：

```
yum remove docker \
          docker-client \
          docker-client-latest \
          docker-common \
          docker-latest \
          docker-latest-logrotate \
          docker-logrotate \
          docker-selinux \
          docker-engine-selinux \
          docker-engine
```

2.安装必要依赖：

`
yum install -y yum-utils device-mapper-persistent-data lvm2
`

3.更换国内镜像：

`
yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
`

4.更新 yum 缓存：

`
yum makecache fast
`

5.安装 Docker-ce：

`
yum -y install docker-ce
`

6.启动 Docker 后台服务

`
systemctl start docker
`

7.测试运行 hello-world

`
 docker run hello-world
`

8.运行交互式容器

```
root@root:~$ docker run -it ubuntu:15.10 /bin/bash
root@dc0050c79503:/#
```
- -i：允许你对容器内的标准输入进行交互
- -t：在新容器内指定一个伪终端或终端
- -d: 后台启动容器
- –rm：容器退出后立即删除容器。一般情况下，无需指定此参数，指定--rm可以避免浪费空间

退出容器交互命令 `ctrl+d`


9.后台运行容器
```
root@root:~$ docker run -d ubuntu:15.10 /bin/bash
root@dc0050c79503:/#
```

10.查看运行的容器

```
[root@localhost ~]# docker ps 
CONTAINER ID        IMAGE               COMMAND             CREATED             STATUS              PORTS               NAMES
46353af5c8ce        ubuntu:15.10        "/bin/bash"         4 minutes ago       Up 4 minutes                            gracious_allen
```

11.停止容器

```
[root@localhost ~]# docker stop 46353af5c8ce
46353af5c8ce
```
12.查看本机镜像

```
[root@localhost ~]# docker image ls
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
zookeeper           latest              f336949ce7a1        6 weeks ago         148MB
hello-world         latest              4ab4c602aa5e        3 months ago        1.84kB
ubuntu              15.10               9b9cb95443b5        2 years ago         137MB
training/webapp     latest              6fae60ef3446        3 years ago         349MB
```
结构说明：

- REPOSITORY：仓库名称
- TAG：标签名称
- IMAGE ID：镜像ID
- CREATED：创建时间
- SIZE：所占用的空间

列出指定镜像

```
[root@localhost ~]# docker image ls ubuntu
REPOSITORY          TAG                 IMAGE ID            CREATED             SIZE
ubuntu              15.10               9b9cb95443b5        2 years ago         137MB
```

13.删除镜像

```
[root@localhost ~]# docker image rm 6fae60ef3446
```

14.查找镜像

```
[root@localhost ~]# docker search ubuntu 
NAME                                                   DESCRIPTION                                     STARS               OFFICIAL            AUTOMATED
ubuntu                                                 Ubuntu is a Debian-based Linux operating sys…   8910                [OK]                
dorowu/ubuntu-desktop-lxde-vnc                         Ubuntu with openssh-server and NoVNC            253                                     [OK]
rastasheep/ubuntu-sshd                                 Dockerized SSH service, built on top of offi…   187                                     [OK]
```