---
tags: Java,排序,Compare
grammar_cjkRuby: true
---

>[TOC]

在Java中使用集合来存储数据时非常常见的,集合排序功能也是常用功能之一.下面看一下如何进行集合排序,常用的方法有: `Comparator`和`Comparable`

# Comparator接口

使用步骤:
1. 新建比较类,
2. 实现Comparator接口,
3. 重写compare方法,

```java?linenums
package sort;

import java.util.Comparator;

public class LuckBoyCompare implements Comparator<LuckBoy>{

    @Override
    public int compare(LuckBoy o1, LuckBoy o2) {
        return o1.getAge()-o2.getAge();
    }

}

```

* 调用`Collections.sort()`方法进行排序,
* 形式:`Collections.sort(集合, 比较器实例)`.

```java?linenums
@Test
public void test1() {
    List<LuckBoy> boyList = new ArrayList<LuckBoy>();
    LuckBoy boy1 = new LuckBoy("张三",13,"上海");
    LuckBoy boy2 = new LuckBoy("李四",12,"北京");
    LuckBoy boy3 = new LuckBoy("王五",18,"深圳");
    LuckBoy boy4 = new LuckBoy("马六",17,"南京");

    boyList.add(boy1);
    boyList.add(boy2);
    boyList.add(boy3);
    boyList.add(boy4);

    System.out.println("排序前:");
    for (LuckBoy luckBoy : boyList) {
        System.out.println(luckBoy);
    }

    System.out.println("排序后:");
    Collections.sort(boyList, new LuckBoyCompare());
    for (LuckBoy luckBoy : boyList) {
        System.out.println(luckBoy);
    }
}
```

_LuckBoy.java_

```java?linenums
package sort;

public class LuckBoy{
    private String name;
    private Integer age;
    private String city;

    public LuckBoy() {
        super();
    }

    public LuckBoy(String name, Integer age, String city) {
        super();
        this.name = name;
        this.age = age;
        this.city = city;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "LuckBoy [name=" + name + ", age=" + age + ", city=" + city + "]";
    }
}
```

_打印结果:_

```
排序前:
LuckBoy [name=张三, age=13, city=上海]
LuckBoy [name=李四, age=12, city=北京]
LuckBoy [name=王五, age=18, city=深圳]
LuckBoy [name=马六, age=17, city=南京]
排序后:
LuckBoy [name=李四, age=12, city=北京]
LuckBoy [name=张三, age=13, city=上海]
LuckBoy [name=马六, age=17, city=南京]
LuckBoy [name=王五, age=18, city=深圳]
```

# Comparable接口

使用步骤:

*   数据模型实现Comparable接口,
*   重写compareTo方法,

```java?linenums
package sort;

public class LuckBoy implements Comparable<LuckBoy>{
//TODO 中间代码省略
    @Override
    public int compareTo(LuckBoy o) {
        return this.age-o.age;
    }
}

```

*   调用`Collections.sort()`方法进行排序,
*   形式:`Collections.sort(集合)`

```java?linenums
@Test
public void test2() {
    List<LuckBoy> boyList = new ArrayList<LuckBoy>();
    LuckBoy boy1 = new LuckBoy("张三",13,"上海");
    LuckBoy boy2 = new LuckBoy("李四",12,"北京");
    LuckBoy boy3 = new LuckBoy("王五",18,"深圳");
    LuckBoy boy4 = new LuckBoy("马六",17,"南京");

    boyList.add(boy1);
    boyList.add(boy2);
    boyList.add(boy3);

    boyList.add(boy4);

    System.out.println("============================");
    System.out.println("排序前:");
    for (LuckBoy luckBoy : boyList) {
        System.out.println(luckBoy);
    }

    System.out.println("排序后:");
    Collections.sort(boyList);
    for (LuckBoy luckBoy : boyList) {
        System.out.println(luckBoy);
    }
}
```

_打印结果:_

```
排序前:
LuckBoy [name=张三, age=13, city=上海]
LuckBoy [name=李四, age=12, city=北京]
LuckBoy [name=王五, age=18, city=深圳]
LuckBoy [name=马六, age=17, city=南京]
排序后:
LuckBoy [name=李四, age=12, city=北京]
LuckBoy [name=张三, age=13, city=上海]
LuckBoy [name=马六, age=17, city=南京]
LuckBoy [name=王五, age=18, city=深圳]
```

# 区别

**Comparator** 使用灵活,不需要修改源码.但是,使用时需要传入比较器对象;
**Comparable** 使用简单,但是需要修改源码.