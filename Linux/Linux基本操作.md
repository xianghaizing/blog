>[TOC]

# 基本操作
1. 创建文件
``` makefile
1) touch 文件名 #创建文件
2) cat>>文件名 #创建并写入 
   ctrl+d #保存并退出
3) vi 文件名 #创建并进入vi编辑器 
    #i进入编辑 
    #wq保存退出
```


2. 创建文件夹

``` makefile
mkdir 文件夹名字
mkdir test	#创建一个文件夹
mkdir test01 test02	#创建多个文件夹
mkdir -p 111/222		#创建父子文件夹
mkdir -p 11/{22,33,44}	#创建父多子文件夹
```

3. 修改文件

``` makefile
1) 移动
mv 文件(夹) 新名字 #移动文件
2) 替换
:s/old/new/   #用old替换new，替换当前行的第一个匹配
:s/old/new/g  #用old替换new，替换当前行的所有匹配
:%s/old/new/  #用old替换new，替换所有行的第一个匹配
:%s/old/new/g #用old替换new，替换整个文件的所有匹配
#也可以用v或V选择指定行，然后执行
```

4. 查看文件夹

``` makefile
1) ls 
ls -l		#详细信息
ls -lh 	#详细信息kb表示
ls -d */ 	#显示文件夹
ls -R 	#递归显示
ls -t		#按时间显示
2) ll
```

5. 查看文件

``` makefile
1) cat 文件名 #第一行开始 
2) tac 文件名 #最后一行开始 (不常用)
3) more 和 less(常用)
   more 文件名
   Enter #向下n行，需要定义，默认为1行； 
   Ctrl f #向下滚动一屏； 
   空格键 #向下滚动一屏； 
   Ctrl b #返回上一屏； 
   = #输出当前行的行号； 
   :f #输出文件名和当前行的行号； 
   v #调用vi编辑器； 
   ! #命令 调用Shell，并执行命令； 
   q #退出more
4) head 和 tail 
```

6. tail -f 和 tailf 动态监听日志

``` makefile
二者的区别：
1. tailf 总是从文件开头一点一点的读， 而tail -f 则是从文件尾部开始读
2. tailf check文件增长时，使用的是文件名， 用stat系统调用；而tail -f 则使用的是已打开的文件描述符； 
注：tail 也可以做到类似跟踪文件名的效果； 但是tail总是使用fstat系统调用，而不是stat系统调用；
结果就是：默认情况下，当tail的文件被偷偷删除时，tail是不知道的，而tailf是知道的。
```

7. 删除复制查找

``` makefile
1) rm 文件
2) rm -rf 文件夹
3) cp 文件 路径
4) cp -r 文件夹 路径
5) find 目录(/) 文件(name*) 
6) grep 正则 文件名
7) grep -R 正则 *
8) locate 文件名
```

8. 创建账户

``` makefile
1) useradd 用户名
2) passwd 用户名
3) userdel 用户名
4) rm -rf 文件夹
5) su 用户名 #新创建的用户会在/home中创建同名文件夹
```

9.设置文件权限

``` makefile
drw-rw-rw- 	#目录
-rw-rw-rw-	   #二进制文件
lrw-rw-rw-     #链接
r read读 w write写 x 可执行

1) ln (link)
ln 文件 新文件 #同步修改
ln -s 文件 新文件 #快捷方式 
2) chmod #改变文件权限
chmod 777 文件
chmod u+x 文件
chown #改变所有者
chgrp #改变用户组
```

10. 压缩和解压

``` makefile
1) tar -zcvf 压缩文件名 路径
2) tar -zxvf 压缩文件名
```

# vim操作

**yum install vim #安装vim**
``` makefile
1) 编辑
    vi 文件 #i 进入编辑模式 o 插入一行
2) 复制粘贴
    yy #复制行
    v	#复制字符
    V	#复制行
    p	#粘贴
3) 删除
    d	#删除
    dd	#删除行
    10dd 	#删除10行
    D		#删除当前字符到行尾
4) 撤销
    u	#撤销
5) 跳转
    gg 	  #跳转到文件头
    G 	  #跳转到文件尾
    gg=G    #自动缩进 （非常有用）
    :120    #跳转到120行
    $ 	  #跳转到行尾
    0	    #跳转到行首
6) 查找
    /text　　#查找text，按n健查找下一个，按N健查找前一个
    ?text　　#查找text，反向查找，按n健查找下一个，按N健查找前一个
    :set ignorecase　　  #忽略大小写的查找
    :set noignorecase　　#不忽略大小写的查找
    :set nu		#显示行号
```

11. 免密登陆










