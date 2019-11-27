---
title: linux搭建GitLab
tags: GitLab,CentOS6
grammar_wavedrom: true
grammar_decorate: true
---

> [toc]

# 1. 安装VMware和CentOS
本教程采用CentOS6.6

配置静态IP参考: https://www.cnblogs.com/linyufeng/p/8515337.html

# 2. 安装必备Linux插件
(安装过可以不用安装)

`yum install -y vim wget`

# 3. 准备安装GitLab

`yum install -y postfix sshd policycoreutils-python`

- `postfix` 邮件通知
- `sshd` ssh服务(一般系统已经有了,可以不装)
- `policycoreutils-python` GitLab必备依赖

设置postfix开机自启: `chkconfig --add postfix`


# 4. 开始安装GitLab

官网下载地址: https://packages.gitlab.com/app/gitlab/gitlab-ce/search?q=10.0.2

![][1]

以CentOS 6 为例: https://packages.gitlab.com/gitlab/gitlab-ce/packages/el/6/gitlab-ce-10.0.2-ce.0.el6.x86_64.rpm

安装方式2种: **使用yum** 和 **rpm包**,本文介绍yum方式

`curl -s https://packages.gitlab.com/install/repositories/gitlab/gitlab-ce/script.rpm.sh | sudo bash`

`yum install -y gitlab-ce-10.0.2-ce.0.el6.x86_64`


# 5. 配置GitLab

主要配置两个地方: **访问GitLab的IP地址**和**发送邮件的账号配置**

`vim  /etc/gitlab/gitlab.rb`


``` 
external_url 'http://192.168.183.200' # 你自己的IP

### Email Settings
gitlab_rails['gitlab_email_enabled'] = true
gitlab_rails['gitlab_email_from'] = '你的邮箱'
gitlab_rails['gitlab_email_display_name'] = '邮件名字'

### GitLab email server settings
### 这里邮件服务器使用的是QQ企业邮箱,163自行修改
gitlab_rails['smtp_enable'] = true
gitlab_rails['smtp_address'] = "smtp.exmail.qq.com"
gitlab_rails['smtp_port'] = 465
gitlab_rails['smtp_user_name'] = "你的邮箱"
gitlab_rails['smtp_password'] = "邮箱密码"
gitlab_rails['smtp_authentication'] = "login"
gitlab_rails['smtp_enable_starttls_auto'] = true
gitlab_rails['smtp_tls'] = true
```

**ESC**保存并退出 `:wq`

# 6. 启动GitLab

刷新配置: `gitlab-ctl reconfigure`
重启服务: `gitlab-ctl restart`


# 7. 访问GitLab

此时还不能访问,需要**关闭防火墙** `service iptables stop` 或者 在防火墙中**开放80端口**:

编辑iptables 
`vi /etc/sysconfig/iptables`

添加配置
`-A INPUT -m state --state NEW -m tcp -p tcp --dport 80 -j ACCEPT`

重启防火墙 
`service iptables restart`

访问 http://192.168.183.200/

![][2]

第一次登录需要修改**root**密码, **密码8位以上**,修改完就可以登录

![][3]

GitLab初始界面

![][4]

# 8. GitLab基本操作

添加用户2种方式: **root后台添加**和**自己注册**

![方式1][5]

![方式2][6]

用户权限

![][7]
- Regular: 可以访问自己组和项目
- Admin: 可以访问所有组和项目

添加组

![][8]

组权限

![][9]

- Private: 私用,只允许组成员可见
- Internal: 内部,所有登录用户可见
- Public: 公共,所有人可见

创建仓库和添加成员到组,很简单,不再赘述. 重点说下仓库成员权限,

![][10]

- Guest：可以创建issue、发表评论，不能读写版本库
- Reporter：可以克隆代码，不能提交，QA、PM可以赋予这个权限
- Developer：可以克隆代码、开发、提交、push，RD可以赋予这个权限
- Master：可以创建项目、添加tag、保护分支、添加项目成员、编辑项目，核心RD负责人可以赋予这个权限
- Owner：可以设置项目访问权限 - Visibility Level、删除项目、迁移项目、管理组成员，开发组leader可以赋予这个权限

默认**Developer角色**不能push到master, master分支受保护;可以修改如下:

![][11]

测试发邮件

``` 
gitlab-rails console
irb(main):003:0> Notify.test_email('邮箱', 'Message Subject', 'Message Body').deliver_now
```


  [1]: ./images/1571043451079.jpg "1571043451079.jpg"
  [2]: ./images/1571043778917.jpg "1571043778917.jpg"
  [3]: ./images/1571043868533.jpg "1571043868533.jpg"
  [4]: ./images/1571043887444.jpg "1571043887444.jpg"
  [5]: ./images/1571043934899.jpg "1571043934899.jpg"
  [6]: ./images/1571043956534.jpg "1571043956534.jpg"
  [7]: ./images/1571044017989.jpg "1571044017989.jpg"
  [8]: ./images/1571044070815.jpg "1571044070815.jpg"
  [9]: ./images/1571044088054.jpg "1571044088054.jpg"
  [10]: ./images/1571044127858.jpg "1571044127858.jpg"
  [11]: ./images/1571109153153.jpg "1571109153153.jpg"