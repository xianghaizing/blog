---
tags: linux,80端口,防火墙
---

在虚拟机中配置成功并正常启动nginx服务后，但浏览器无法访问服务，原因可能是linux中未开放80端口(nginx默认的端口为80)。

1、执行该命令打开端口文件

``` nginx
vi /etc/sysconfig/iptables
```
2、复制一行将端口设置为80

``` nginx
-A INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT
```

3、保存退出

``` nginx
:wq
```

4、重启服务使设置生效

``` nginx
service iptables restart
```

也可以直接关闭防火墙，学习阶段可以不用管防火墙，公司中都是运维在管理。

``` nginx
service iptables stop
```