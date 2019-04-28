---
title: linux常用技能
tags: 阿里云镜像,图形界面,克隆虚拟机
grammar_cjkRuby: true
---

> [toc]

# linux替换阿里云镜像

第一步：备份你的原镜像文件，以免出错后可以恢复。
`cp /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.backup`

第二步：下载新的CentOS-Base.rep到/etc/yum.repos.d/
`wget -O /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-6.repo`
提示: 没有安装wget运行`yum install wget -y`

第三步：运行`yum makecache`生成缓存



# centos6.6安装图形界面

`yum groupinstall "Desktop"`

`yum groupinstall "X Window System"`

`yum groupinstall "Desktop Platform"`

`yum groupinstall "Chinese Support"`

`yum groupinstall "virt-manager"`

完整命令:

`yum -y groupinstall "Desktop" "X Window System" "Desktop Platform" "Chinese Support" "virt-manager"`

启动图形化界面工具
`virt-manager &`

`startx`

# 克隆虚拟机后网络问题

centos6 在虚拟机里克隆完成后,网络连不上.需要修改网卡配置.执行 `vi /etc/udev/rules.d/70-persistent-net.rules` 会看到多行网卡信息,保留最后一行删除掉其他行.
把最后一行的name由**eth-xx**改成**eht-0**,重启网络`service network restart`
