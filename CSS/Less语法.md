> [TOC]


![less](./images/1536376391885.png)
Less 是一门 CSS 预处理语言，它扩充了 CSS 语言，增加了诸如变量、混合（mixin）、函数等功能，让 CSS 更易维护、方便制作主题、扩充。

less语法格式如下：

``` less
@background:#000;
@width:200px;

.box{
    width: @width;
    height: @width;
    background: @background;
}
```

页面可以引用less.js，直接使用less文件。
注意
   1、less的文件一定要在js的文件（less.js）上面
   2、用link标签引入less文件，需要把res属性的值改为stylesheet/less

也可以通过第三方工具将less文件编译成css使用；下载地址：http://koala-app.com/index-zh.html
编译后的文件：

``` css
.box {
  width: 200px;
  height: 200px;
  background: #000000;
}
```

# 变量
less里声明一个变量的方法：@变量名:值

	用变量去定义一个属性名的时候，在用的时候，一定要给这个名字加上一对大括号。
	
	用变量去定义一个路径的时候，在用的时候，一定要在整个路径的外面加上一对引号，并且路径变量名在用的时候也要加大括号。
``` less
@w:200px;
@border:1px solid #f00;
@property:color;
@value:green;
@images:'../images';

.box{
    width: @w;
    height: @w;
    border: @border;
    @{property}: @value;
    background-@{property}: @value;
    background-image: url('@{images}/banner.jpg');
}
```
编译后：

``` css
.box {
  width: 200px;
  height: 200px;
  border: 1px solid #ff0000;
  color: #008000;
  background: #008000;
  background-image: url('../images/banner.jpg');
}
```

# 混合写法
混合写法，把另一个选择器的名字放在这个样式里，这个样式就会具有放入的选择器的样式

``` less
@width:200px;
@border:5px solid #f00;

.class{
    font: 20px/40px "微软雅黑";
    color: #fff;
    text-align: center;
    background: green;
    border: @border;
}
.box1{
    width: @width;
    height: @width;
    .class;
}
```

编译后：

``` css
.class {
  font: 20px/40px "微软雅黑";
  color: #fff;
  text-align: center;
  background: green;
  border: 5px solid #ff0000;
}
.box1 {
  width: 200px;
  height: 200px;
  font: 20px/40px "微软雅黑";
  color: #fff;
  text-align: center;
  background: green;
  border: 5px solid #ff0000;
}
```

# 混合带参数

``` less
.bg(@bg){
    background: @bg;
}
.box3{
    height: 200px;
    .bg(blue);
}
```

编译后：

``` css
.box3 {
  height: 200px;
  background: #0000ff;
}
```

# 混合带默认参数

``` less
.border1(@w:10px){
    border: @w solid green;
}
.box4{
    height: 200px;
   .border1();
   .border1(40px);
}
```

编译后：

``` css
.box4 {
  height: 200px;
  border: 10px solid #008000;
  border: 40px solid #008000;
}
```

# 混合带多个参数
``` less
.border2(@w:10px,@style:solid,@color:#000){
    border: @w @style @color;
}

.box5{
    height: 300px;
    //.border2();
    //.border2(@w:30px);
    //.border2(@style:dotted);
    //.border2(@color:#f00);
    .border2(@w:20px,@style:dotted,@color:#f00);
}
```
编译后：

``` css
.box5 {
  height: 300px;
  border: 20px dotted #ff0000;
}
```

# 自动加浏览器前缀

``` less
.boxShadow(@x:5px,@y:5px,@area:5px,@color:#ccc){
    -webkit-box-shadow: @x @y @area @color;
    -moz-box-shadow: @x @y @area @color;
    -ms-box-shadow: @x @y @area @color;
    -o-box-shadow: @x @y @area @color;
    box-shadow: @x @y @area @color;
}

.box6{
   .boxShadow(); 
   width: 300px;
   height: 300px;
   background: red;
}
```

编译后：

``` css
.box6 {
  -webkit-box-shadow: 5px 5px 5px #cccccc;
  -moz-box-shadow: 5px 5px 5px #cccccc;
  -ms-box-shadow: 5px 5px 5px #cccccc;
  -o-box-shadow: 5px 5px 5px #cccccc;
  box-shadow: 5px 5px 5px #cccccc;
  width: 300px;
  height: 300px;
  background: red;
}
```

# 匹配模式

``` less
.pos(r){
    position: relative;
}
.pos(a){
    position: absolute;
}
.pos(f){
    position: fixed;
}
.box4{
    .pos(r);
    .pos(a);
    .pos(f);
    left: 10px;
    top: 20px;
    width: 200px;
    height: 200px;
    background: red;
}
```

编译后：

``` css
.box4 {
  position: relative;
  position: absolute;
  position: fixed;
  left: 10px;
  top: 20px;
  width: 200px;
  height: 200px;
  background: red;
}
```

# 匹配模式带默认参数和属性

``` less
//匹配模式
.triangle(top,@w:5px,@c:red){
    border-width: @w;
    border-color: transparent transparent @c transparent;
    border-style: dashed dashed solid dashed;
}
.triangle(right,@w:5px,@c:red){
    border-width: @w;
    border-color: transparent transparent transparent @c;
    border-style: dashed dashed dashed solid;
}
.triangle(bottom,@w:5px,@c:red){
    border-width: @w;
    border-color: @c transparent transparent transparent;
    border-style: solid dashed dashed dashed;
}
.triangle(left,@w:5px,@c:red){
    border-width: @w;
    border-color: transparent @c transparent transparent;
    border-style: dashed solid dashed dashed ;
}

//公用的样式，需要放到下面这个class里，第一个参数是固定的格式（@_）,后面的参数与上面保持一致
.triangle(@_,@w:5px,@c:red){
    width: 0;
    height: 0;
    overflow: hidden; 
}
.box3{
    .triangle(right,50px,green);
}
```

编译后：

``` css
.box3 {
  border-width: 50px;
  border-color: transparent transparent transparent #008000;
  border-style: dashed dashed dashed solid;
  width: 0;
  height: 0;
  overflow: hidden;
}
```

# 嵌套

``` less
#box{
    width: 500px;
    padding: 20px;
    border: 1px solid #f00;
    h2{
        font: 20px/20px "微软雅黑";
    }
}
```

编译后：

``` css
#box {
  width: 500px;
  padding: 20px;
  border: 1px solid #f00;
}
#box h2 {
  font: 20px/20px "微软雅黑";
}
```

# 嵌套操作上一层 &

``` less
#box{
    width: 500px;
    padding: 20px;
    border: 1px solid #f00;
    &{
        border: 5px solid #f00;
    }
    h2{
        font: 20px/20px "微软雅黑";
    }
}
```

编译后：

``` css
#box {
  width: 500px;
  padding: 20px;
  border: 1px solid #f00;
  border: 5px solid #f00;
}
#box h2 {
  font: 20px/20px "微软雅黑";
}
```

# 运算
在做减法运算的时候，一定要记着，减号前后要加上一个空格，不然就会报错

``` less
@w:300px;
.box1{
    width: @w;
    height: @w+100;
    height: @w - 100;
    border: 1px solid #f00;
    position: relative;
    left: @w*2;
    top: @w/3;
}
```

编译后：

``` css
.box1 {
  width: 300px;
  height: 400px;
  height: 200px;
  border: 1px solid #f00;
  position: relative;
  left: 600px;
  top: 100px;
}
```

# 避免编译 ~
避免编译，就把不需要编译的内容前面先加上一个`~`，把内容放到一对引号中

``` less
@width: 200px;
@height: 600px;

.box2{
    width: @width;
    height: @height;
    border: 1px solid #f00;
    div{
        width: @width/2;
        height: @height/2;
        background: green;
        margin: (@height - @height/2)/2 auto 0 auto;
        filter: ~'alpha(opacity:50)';
    }
}
```

编译后：

``` css
.box2 div {
  width: 100px;
  height: 300px;
  background: green;
  margin: 150px auto 0 auto;
  filter: alpha(opacity:50);
}
```