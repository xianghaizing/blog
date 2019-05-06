---
title: Linux安装 jdk Tomcat MySQL
tags: jdk, Tomcat, MySQL
---

> [toc]

# 1.jdk安装
官网下载
http://www.oracle.com/technetwork/java/javase/downloads/index.html 下载linux版本**jdk-xxx.tar.gz**

上传并解压 `tar -zxf jdk-xxx.tar.gz`

配置环境变量 `vi /etc/profile` ,复制解压后jdk目录,示例:

``` ini
JAVA_HOME= /usr/soft/1.8.0_191
PATH=$PATH:$JAVA_HOME/bin

export PATH USER LOGNAME MAIL HOSTNAME HISTSIZE HISTCONTROL

```
刷新配置 `source /etc/profile`, 查看是否安装成功`java -version`

``` 
java version "1.8.0_191"
Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
```

# 2.Tomcat安装

下载tomcat https://tomcat.apache.org/download-80.cgi

上传并解压 `tar apache-tomcat-xx.tar.gz`

进入bin目录 `cd ./bin`, 启动tomcat `./startup.sh`

进入logs目录查看日志 `tail -f catalina.out`

关闭防火墙, 就可以通过浏览器访问了(ip:8080)
- `service iptables status`  查看防火墙状态
- `service iptables stop`    关闭防火墙

# 3.Mysql安装

环境: **CentOS6**

如果已经安装过先卸载,查看是否安装`rpm -qa | grep mysql`, 卸载 `yum –y remove 包名` 或者 `rpm -e --nodeps 包名`

安装mysql`yum -y install mysql-server mysql mysql-devel`

- 启动： `service mysqld start`
- 查看： `ps aux | grep mysql`
- 停止： `service mysqld stop`
- 重启： `service mysqld restart`

创建root管理员 `mysqladmin -u root password 123456`

登陆 `mysql -uroot -p123456`

允许其他客户端访问,先关闭防火墙.然后在**sql命令界面**执行以下操作

``` sql
mysql> grant all privileges on *.* to 'root'@'%' identified by '123456' with grant option;
Query OK, 0 rows affected (0.01 sec)

mysql> FLUSH   PRIVILEGES;
Query OK, 0 rows affected (0.02 sec)
```

环境: **CentOS7**

下载
`wget -i -c http://dev.mysql.com/get/mysql57-community-release-el7-10.noarch.rpm`

安装
`yum -y install mysql57-community-release-el7-10.noarch.rpm`
`yum -y install mysql-community-server`

启动
`systemctl start mysqld.service`

查看状态
`systemctl status mysqld.service`

查看初始密码
`grep "password" /var/log/mysqld.log`

```
grep "password" /var/log/mysqld.log
1 [Note] A temporary password is generated for root@localhost: DcGSI?xMp7Dt
```

回车后会提示输入密码
`mysql -uroot -p`

修改密码安全策略,否则简单密码如root或者123456不能用

`set global validate_password_policy=0;`
`set global validate_password_length=1;`

修改密码
`ALTER USER 'root'@'localhost' IDENTIFIED BY 'new password';`

授权可视化工具连接
`grant all on *.* to root@'%' identified by '数据库密码';`

关闭防火墙

参考: https://www.cnblogs.com/brianzhu/p/8575243.html