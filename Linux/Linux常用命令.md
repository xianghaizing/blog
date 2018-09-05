> [TOC]

# 基本命令
1. 查看Tomcat日志: tail -n 20 -f catalina.out 
2. 查看指定列表: find ./ -name 'ser*'
3. 搜索指定文件: locate <文件名>
    ``` elixir
    $ yum install mlocate #安装
    $ updatedb #更新索引库
    $ locate 文件名 #搜索
    ```
4. 查看端口占用:netstat -anp|grep <端口号>
5. 查看进程: ps -aux | grep <进程名>
6. 查看进程: ps -ef | grep <进程名>
7. 杀死进程: kill -9 <pid>
8. 复制文件: cp -rf <文件> <路径>
9. 搜索

# vim命令
vim 操作: (非编辑模式下)
1. 跳转首行: :行号
2. 查找内容: /内容
