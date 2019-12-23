---
title: linux安装tmux分屏插件
tags: linux,tmux,分屏
renderNumberedHeading: true
grammar_cjkRuby: true
grammar_code: true
---


> [toc]

# 安装tmux
`yum install -y tmux` TMUX2版本以下

# 基本使用
使用tmux一般使用**命令和快捷键**来操作,使用规则:

`Ctrl+b` + 按键, **一定注意使用规则,先按`Ctrl+b`松开,然后再按其他键才能生效!!!**

我自己使用比较多的快捷键:(以下把`Ctrl+b`简称为`pre`)
- `tmux` 创建窗口
- `pre %` 水平分屏
- `pre "` 垂直分屏
- `pre d` 后台退出
- `tmux a` 连接窗口,多个窗口通过`tmux -a -t 窗口名/窗口序号`
- `tmux ls` 查看窗口
- `Ctrl+d` 关闭窗口

其他快捷操作,参考: https://www.cnblogs.com/liuguanglin/p/9290345.html

# 鼠标操作
鼠标操作可以快捷调整窗口大小、活动窗口、窗口切换等功能

进入tmux
`tmux`

编辑配置文件
`vim ~/.tmux.conf`

添加配置

``` 
set -g mouse-resize-pane on
set -g mouse-select-pane on
set -g mouse-select-window on
set -g mode-mouse on
```
> 作用分别是:
> - 开启用鼠标拖动调节pane的大小（拖动位置是pane之间的分隔线） 
> - 开启用鼠标点击pane来激活该pane 
> - 开启用鼠标点击来切换活动window（点击位置是状态栏的窗口名称） 
> - 开启window/pane里面的鼠标支持（也即可以用鼠标滚轮回滚显示窗口内容，此时还可以用鼠标选取文本）
> 原文链接：https://blog.csdn.net/fk1174/article/details/79220227

进入tmux命令模式
`Ctrl+b` + `:`

刷新配置
`source ~/.tmux.conf`