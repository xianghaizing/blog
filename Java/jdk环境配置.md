>[TOC]

# 安装jdk

目前最新jdk版本是1.8[官网下载 jdk_1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html),安装完成后:

1.  快捷键`win+r`打开命令行窗口
2.  输入`cmd`打开命令控制台
3.  输入`java -version`查看java版本,看到如下信息表示安装成功:

```
C:\Users\WTG>java -version
java version "1.8.0_112"
Java(TM) SE Runtime Environment (build 1.8.0_112-b15)
Java HotSpot(TM) 64-Bit Server VM (build 25.112-b15, mixed mode)
```

# 配置环境

安装完成后,要运行java文件,还需要配置系统环境变量: **`JAVA_HOME` 和 `classpath`**.

1.  依次打开:`计算机(或者此电脑)` > `高级系统设置` > `高级` > `环境变量`
2.  `系统变量` > `新建`,变量名为:`JAVA_HOME`, 变量值为jdk安装的路径,比如:`C:\Program Files\Java\jdk1.8.0_112`(**注意:以你自己安装的路径为准!**) > `确定`保存.
3.  找到`Path` > `编辑(或者双击)` > `变量值`, 添加配置:`;%JAVA_HOME%\bin;%JAVA_HOME%\jre\bin;`(**注意:新手不要省略分号;**) > `确定`保存.
4.  此时环境已经配置成功,**重新打开cmd命令行窗口**来验证一下,输入`javac -version`看到如下信息表示配置成功:

```
C:\Users\WTG>javac -version
javac 1.8.0_112
```

# HelloWorld

下面编写第一个java程序`HelloWorld`, 在`F:/盘根目录`新建文件`HelloWorld.java`(**注意:文件后缀必须为java**)打开`记事本`写入以下代码(**文件名和代码都区分大小写**):

```java?linenums
public class HelloWorld {
    public static void main(String args[]){
        System.out.println("HelloWorld");
    }
}
```

运行命令: `javac` 和 `java`

```
F:\>javac HelloWorld.java
F:\>java HelloWorld
HelloWorld
```

# 安装eclipse

项目开发需要**快速编写java代码**,码农们也需要`代码提示`、`语法高亮`等功能.可以选择自己喜欢的开发工具,比如: eclipse、myeclipse、idea等等，建议先使用eclipse。