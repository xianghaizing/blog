---
title: Hadoop之HDFS读写流程
tags: hadoop,hdfs
grammar_cjkRuby: true
---

> [toc]

# 1. HDFS写流程
![HDFS写流程](./images/1559917252877.png)

>副本存放策略: 上传的数据块后,触发一个新的线程,进行存放。
第一个副本:与client最近的机器(基于性能考虑)
第二个副本:跨机器存放该副本(考虑数据安全性)
第三个副本:与第一个，第二个副本都不在同一个机架上(考虑数据安全性)

# 2. HDFS写流程
![HDFS读流程](./images/1559917478626.png)

