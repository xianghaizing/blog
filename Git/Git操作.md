---
title: Git/Git操作
---
> [TOC]

# 创建版本库

``` ldif
cd: 切换目录,比如 cd e: 切换到e盘
mkdir: 创建一个文件夹
pwd: 查看当前目录
git init: 初始化一个本地库
$ git config --global user.name "name" 设置用户名
$ git config --global user.email "email@example.com" 设置邮箱
```

# 基本文件操作

``` ldif
git add: 添加文件到缓存区
git commit: 提交文件到分支,-m 添加说明 
git status: 查看文件是否有修改
git diff: 对比文件修改内容
git log: 查看提交历史
git reset --hard HEAD^: 回到上一个版本
git reset --hard xxx: 回到指定版本,版本号可以不写全
git reflog: 查看历史提交版本
git rm: 删除版本库文件
rm: 删除本地文件
ls: 查看文件目录
```

# 创建远程版本库

``` basic
1) 创建SSH Key
$ ssh-keygen -t rsa -C "your email"
在用户主目录里找到.ssh目录，里面有id_rsa和id_rsa.pub
2) 登陆GitHub，打开“Account settings”，“SSH Keys”页面。点“Add SSH Key”，
填上任意Title，在Key文本框里粘贴id_rsa.pub文件的内容。点“Add Key”，你	就应该看到已经添加的Key
3) 验证是否成功
	$ ssh -T git@github.com
Hi xxx! You've successfully authenticated, but GitHub does not provide shell access.
```

# 添加远程库

``` basic
1) 登陆GitHub，在右上角找到“Create a new repo”按钮，创建一个新的仓库
2) 在Repository name填入learngit，其他保持默认设置，点击“Create repository”	按钮，就成功地创建了一个新的Git仓库
3) $ git remote add origin git@github:<your github address>，添加远程库，名	字叫做origin
4) $ git push -u origin master，把本地库推送到远程库上。由于远程库是空的，我	们第一次推送master分支时，加上了-u参数，Git不但会把本地的master分支内容	推送的远程新的master分支，还会把本地的master分支和远程的master分支关联	起来，在以后的推送或者拉取时就可以简化命令
5) 以后提交只用写，$ git push origin master
6) 克隆远程库，$ git clone 仓库地址
7) 删除远程连接 git remote rm origin
```

# 分支管理

``` shell
1. 创建dev分支，切换到dev分支
	$ git checkout -b dev #创建并切换
	相当于以下两条命令：
	$ git branch dev
	$ git checkout dev
2. 查看当前分支
	$ git branch
	* dev
	  master
3. 修改readme.txt,add. commit
4. 切换回master
	$ git checkout master
5. 合并分支
	$ git merge dev
6. 删除分支
	$ git branch -d dev
7. 解决冲突
	找到冲突文件,去掉<<<<< =====
    $ git add 冲突文件名字
    $ git commit -m ‘注释’
```




