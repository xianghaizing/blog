---
title: linux高频操作: host,用户管理,免密登陆,管道,文件权限,脚本,防火墙,查找
tags: host,用户管理,免密登陆,管道,文件权限,脚本,防火墙,内容查找
grammar_cjkRuby: true
---

>[toc]

# 1. 修改hosts和hostname

修改hosts	`vi /etc/hosts`
修改hostname	`vi /etc/sysconfig/network`
修改ip	`vi /etc/sysconfig/network-scripts/ifcfg-eth0`

# 2. 用户管理

添加用户	`useradd 用户名`
修改密码	`passwd 用户名`
删除用户	`userdel 用户名`
切换用户	`su 用户名` 或者 `su - 用户名`(区别加上 - 代表环境变量也是全新的)
```
passwd lin
Changing password for user lin.
New password: 
```

# 3. 免秘登陆

- 生成秘钥 `ssh-keygen -t rsa`
- 查看秘钥 `ll ~/.ssh`
- 复制秘钥 `cat ~/.ssh >> authorized_keys`

把所有虚拟机生成的id_rsa.pub文件内容放在 **`~/.ssh/authorized_keys`** 文件中,然后每个虚拟机都复制一份

本地拷贝到远程
`scp -r local user@ip:remote`
远程拷贝到本地
`scp user@ip:remote local`


# 4. 文件末尾添加 >>

`cat id_rsa.pub >> authorized_keys`

# 5. 设置可执行文件

`chmod +x 文件` 或者 `chmod 777 文件`

# 6. 任何地方调用

把脚本放到/bin/下

# 7. Centos6 永久关闭防火墙
启动：`service iptables start`
关闭：`service iptables stop`
查看状态：`service iptables status`
开机禁用：`chkconfig iptables off`


# 8. Centos 7 防火墙控制

启动：`systemctl start firewalld`
关闭：`systemctl stop firewalld`
查看状态：`systemctl status firewalld`
开机禁用：`systemctl disable firewalld`

# 9. 查找包含指定字符串的文件

`grep -rn "node04" ./etc/hadoop/*`