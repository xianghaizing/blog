---
title: Java8 新特性
grammar_cjkRuby: true
---

> [toc]

# 第一章 lambda表达式

## 1.1.第一个lambda表达式

Lambda 表达式—— 一种紧凑的、传递行为的方式,形式`()->{}`
功能之一可以简化匿名内部类的使用:

``` java
// jdk7
Runnable r1 = new Runnable() {
    @Override
    public void run() {
        System.out.println("Hello Runnable");
    }
};
// jdk8
Runnable r2 = ()->System.out.println("Hello lambda");
```

## 1.2.函数接口

Lambda表达式的使用需要配合函数式接口,函数接口是只有一个抽象方法的接口，用作 Lambda 表达式的类型。
常用的函数式接口有:

|   接口  |  参数   |  返回类型   |   示例  |
| --- | --- | --- | --- |
| Predicate<T> |	T |	boolean |	boolean test(T t);是或者不是 |
| Consumer<T>| T| 	void| 	void accept(T t);输出一个值 |
| Function<T,R>| 	T| 	R| 	R apply(T t);获取长度| 
| Supplier<T>| 	None| 	T| 	T get();工厂方法| 

### 1.2.1.字符串长度是否大于5

``` java
Predicate<String> p = (s)->s.length()>5;
boolean hello = p.test("Hello");
System.out.println(hello);//false
```

### 1.2.2.打印字符串

``` java
//1个参数可以省略()括号
Consumer<String> c = s->System.out.println(s);
c.accept("Hello");//Hello
```

### 1.2.3.获取数字长度

``` java
Function<Integer, String> f = i->"长度为: "+i.toString().length();
String l = f.apply(100);
System.out.println(l);//长度为: 3
```

### 1.2.4.获取字符串

``` java
Supplier s = ()->"Hello Java";
Object o = s.get();
System.out.println(o);//Hello Java
```

## 1.3.函数接口默认方法连用

函数接口可以通过默认方法进行连用, 比如Function中的andThen方法

``` java
Function<String,Integer> f1 = s -> Integer.parseInt(s);
//第 2 个函数将整数乘以 10 返回
Function<Integer,Integer>  f2 = i -> i * 10;
//调用 andThen 方法，并且输出结果
System.out.println("转成整数并乘以10以后的结果是：" + f1.andThen(f2).apply("2"));
//转成整数并乘以10以后的结果是：20
```

# 第二章 Stream流 API

流的使用:
1. 创建流
2. 中间操作
3. 终止流

## 2.1.创建方式

``` java
//通过集合
List<Integer> list1 = Arrays.asList(1, 2, 3);//定义list
Stream<Integer> s1 = list1.stream();//顺序流
Stream<Integer> s2 = list1.parallelStream();//并行流

//通过数组
Integer[] arr1 = {1, 2, 3};
Stream<Integer> s3 = Arrays.stream(arr1);

//通过Stream的of
Stream<Integer> s4 = Stream.of(1, 2, 3);

//创建无限流
// 迭代
Stream<Integer> s5 = Stream.iterate(0, x -> x + 2);
s5.limit(3).forEach(System.out::println);
// 生成
Stream<Double> s6 = Stream.generate(Math::random);
s6.limit(3).forEach(System.out::println);
```

## 2.2.常用方法

    sorted()	
    forEach()	
    filter()	
    map()	
    flatMap()	
    reduce()	
    min() 
    max() 	
	

## 2.3.终止方式

``` java
// collect 收集
List<Integer> list1 = Arrays.asList(1, 2, 3);
Stream<Integer> stream = list1.stream().map(n -> n + 1);
List<Integer> collect = stream.collect(Collectors.toList());

// reduce 收集
List<Integer> list2 = Arrays.asList(1, 2, 3, 4, 5, 6);
Integer sum = list2.stream().reduce(0, (x1, x2) -> x1 + x2);
System.out.println(sum);
```

# 第三章 接口扩展

接口添加静态方法static和默认方法default

``` java
public interface Me {
    // 默认方法
    default String getName(){
        return "Hello World";
    }
    // 静态方法
    public static void show(){
        System.out.println("静态方法");
    }
}

class Test implements Me{
    public static void main(String[] args){
        String name = new Test().getName();
        System.out.println(name);
        Me.show();
    }
}
```
    注意:
    1. 父类可以重写接口默认方法
    2. 默认接口冲突时,实现类必须重写

# 第四章 Optional类

*Optional 是为核心类库新设计的一个数据类型，用来替换 null 值。人们对原有的 null 值
有很多抱怨，甚至连发明这一概念的 Tony Hoare 也是如此，他曾说这是自己的一个“价值
连城的错误”。作为一名有影响力的计算机科学家就是这样：虽然连一毛钱也见不到，却
也可以犯一个“价值连城的错误”。
人们常常使用 null 值表示值不存在， Optional 对象能更好地表达这个概念。使用 null 代
表值不存在的最大问题在于 NullPointerException 。一旦引用一个存储 null 值的变量，程
序会立即崩溃。使用 Optional 对象有两个目的：首先， Optional 对象鼓励程序员适时检查
变量是否为空，以避免代码缺陷；其次，它将一个类的 API 中可能为空的值文档化，这比
阅读实现代码要简单很多。 —— 《Java8 函数式编程》*


```java
// 创建3种形式
Optional<String> op1 = Optional.empty();// 空的值
Optional<Integer> op2 = Optional.of(2);// 含有2的值
Optional<Integer> op3 = Optional.ofNullable(null);// 允许为null的值

// 基本操作
Integer num = op2.get();// 获取实例
boolean b = op3.isPresent();// 判断是否为空

// 条件判断
String hello = op1.orElse("hello");// 如果为null就hello
Integer age = op3.orElseGet(()->18);// 如果为null就生成新值
```

# 第五章 方法引用

通过`::`调用构造或者方法

## 5.1.调用构造

``` java
public class Me {
    private String name;
    public Me() { }

    public Me(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Me d1 = new Me();// 传统方式
        Supplier<Me> s = Me::new;// 构造引用
        Me d2 = s.get();//获取
        Function<String, Me> f = Me::new;//有参构造引用
        Me d3 = f.apply("hello");//传参
    }
}
```

## 5.2.调用方法

``` java
System.out.println("hello");// 传统方式
Consumer<Object> c = System.out::println;// 引用类静态方法
c.accept("hello");

String str1 = "abc", str2 = "abc";
System.out.println(str1.equals(str2));// 传统方式
BiPredicate<String, String> b = (x,y)->x.equals(y);// 引用实例方法
System.out.println(b.test(str1,str2));
```

# 第六章 新日期和时间API

## 6.1.LocalDate LocalTime LocalDateTime
本地日期 本地时间 和 本地时间日期
``` java
LocalDate date = LocalDate.of(2014, 3, 18);
System.out.println(date);

LocalTime time = LocalTime.of(13, 45, 20);
System.out.println(time);

LocalDateTime dt1 = LocalDateTime.of(2014, Month.MARCH, 18, 13, 45, 20);
System.out.println(dt1);
```

## 6.2.Instant
精确到纳秒

``` java
Instant day = Instant.now();
System.out.println(day);
```

## 6.3.Duration Period
日期间隔分为机器阅读和人类阅读

``` java
// 机器阅读
Instant now1 = Instant.now();
Instant now2 = Instant.now();
Duration d1 = Duration.between(now1, now2);
System.out.println(d1.getNano());//0

// 人阅读
Period tenDays = Period.between(LocalDate.of(2014, 3, 8),
        LocalDate.of(2014, 3, 18));
System.out.println(tenDays.getDays());//10

DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
String today = dtf.format(LocalDateTime.now());
System.out.println(today);
```

# 第七章 重复注解和参数注解

## 7.1.重复注解

``` java
import java.lang.annotation.Repeatable;

@Repeatable(Authors.class)
@interface Author {
    String name();
}

@interface Authors {
    Author[] value();
}
```

## 7.2.参数注解

``` java
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({PARAMETER})
@Retention(RUNTIME)
public @interface MyParam {
    String value() default "";
}
```

## 代码示例

``` java
@Author(name = "li")
@Author(name = "wang")
@Author(name = "liu")
public void hello(@MyParam("hello") String text){
    System.out.println(text);
}
```

















