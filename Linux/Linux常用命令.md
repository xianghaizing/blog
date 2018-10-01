> [TOC]

# 基本命令
1. 查看Tomcat日志: `tail -n 20 -f catalina.out` 
2. 查看指定列表: `find ./ -name 'ser*'`
3. 搜索指定文件: `locate 文件名`

    ``` nginx
    yum install mlocate #安装
    updatedb #更新索引库
    locate 文件名 #搜索
    ```
4. 查看端口占用: `netstat -anp|grep 端口号`
5. 查看进程: `ps -aux|grep 进程名`
6. 查看进程: `ps -ef|grep 进程名`
7. 杀死进程: `kill -9 <pid>`
8. 复制文件: `cp -rf <文件> <路径>`

# vim命令
vim 操作: (非编辑模式下)
1. 跳转首行: :行号

2. 查找内容: /内容

# 常用插件

1. lrzsz 上传下载 
    `rz` 上传,可以拖文件到窗口直接上传
    `sz` 下载

2. yum 下载插件 `yum install 插件 -y`

3. wget 网络请求 `wget 网址`

4. rpm linux自带包管理 
    安装 `rpm  –ivh 包`
    查找 `rpm -q 包`
    卸载 `rpm –e 包`
5. tar 解压压缩
    解压 `tar -zvxf 文件`
    压缩 `tar -zcf 压缩包名 压缩目标`



























