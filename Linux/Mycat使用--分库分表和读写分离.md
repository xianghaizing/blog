---
title: Mycat使用--分库分表和读写分离
tags: Mycat,分库分表,读写分离
grammar_cjkRuby: true
grammar_code: true
---

> [toc]

具体操作参看: https://blog.csdn.net/vbirdbest/article/details/83448757 写得非常好,**兄嘚~优秀!!!**
下边针对部署过程中出现的问题说一下自己的感受

# 1. 模拟多数据库节点

**方法1**
安装虚拟机后，安装多个linux，再安装多个mysql数据库。生产适用，个人操作难度较大

**方法2**
使用docker安装多个mysq，简单方便适合学习

**方法3**
在一个mysql中，使用多个数据库来模拟！**只为学习,部署环境就越简单越好**

# 2. 配置文件

核心配置文件有3个: `rule.xml` `server.xml` `schema.xml`,主要修改最后一个,看一下我的基本配置:

``` xml
<?xml version="1.0"?>
<!DOCTYPE mycat:schema SYSTEM "schema.dtd">
<mycat:schema xmlns:mycat="http://io.mycat/">
	
	<!-- Mycat虚拟数据库 -->
	<schema name="TESTDB" checkSQLschema="false" sqlMaxLimit="100">
		<!-- Mycat虚拟表	表名	物理节点	路由规则 -->
		<table name="tbl_order" dataNode="dn1,dn2,dn3" >>==rule="mod-long"==<< /> 
	</schema>
	
	<!-- 节点 -->
	<dataNode name="dn1" dataHost="localhost1" database="db1" />
	<dataNode name="dn2" dataHost="localhost1" database="db2" />
	<dataNode name="dn3" dataHost="localhost1" database="db3" />
	
	<!-- 节点信息配置 -->
	<dataHost name="localhost1" maxCon="1000" minCon="10" balance="0"
			  writeType="0" dbType="mysql" dbDriver="native" switchType="1"  slaveThreshold="100">
		<heartbeat>select user()</heartbeat>
		<!-- 写配置 -->
		<writeHost host="hostM1" url="localhost:3306" user="root" password="123456">
		<!-- 读配置 -->
		<!-- <readHost host="hostS2" url="192.168.1.200:3306" user="root" password="xxx" /> -->
		</writeHost>
	</dataHost>
</mycat:schema>
```

说明一下:

`rule` 指定分片规则,默认`auto-sharding-long`根据范围进行路由, 比如:
- 第1-1000条存到dn1节点
- 第1001-2000条存到dn2节点
- 第2001~3000条存到dn3节点
(数据量少的话,看不到效果,一开始用默认的,还以为配错了)

`rule="mod-long"`根据id路由(id%分片数),比如:
- 第1条存到是dn1节点
- 第2条存到是dn2节点
- 第3条存到是dn3节点
- 第4条存到是dn1节点
- 依次类推

Mycat默认端口`8066` 数据管理端口`9066` 运行Mycat前,系统要提前安装jdk


