---
title: Hadoop介绍
tags: Hadoop,HDFS,MapReduce
grammar_cjkRuby: true
---


Hadoop是Apache基金会所开发的分布式系统基础架构。最核心的设计就是：**HDFS**和**MapReduce**。
- HDFS为海量的数据提供了存储
- MapReduce则为海量的数据提供了计算

HDFS 分布式文件系统（Hadoop Distributed File System）可以把低廉的硬件设备，比如废旧主机。联系起来组成分布式集群，实现存储海量数据、高吞吐量和高容错性，性能远高于一般数据库（Mysql Oracle）

- NameNode节点： 存放元数据信息，记录真实数据的存储位置
- Secondary NameNode节点： 合并fsimage镜像和logs日志
- DataNode节点： 存放真实数据

MapReduce 海量数据并行计算，运行在HDFS之上。主要功能是海量数据的查询、处理、计算等功能

Yarn模块，用来调度MapReduce的任务
- NodeManager 节点管理
- ResourceManager 资源管理

Hadoop搭建3种方式：
- Local (Standalone) Mode 本地（独立）模式
- Pseudo-Distributed Mode 伪分布式模式
- Fully-Distributed Mode 完全分布式模式