---
title: java反射--PropertyDescriptor类、Introspector类、BeanUtils库
tags: 反射,PropertyDescriptor类,Introspector类
renderNumberedHeading: true
grammar_cjkRuby: true
grammar_code: true
---

原文地址: https://blog.csdn.net/weixin_42069143/article/details/82119724

# JavaBean的内省与BeanUtils库

内省(Introspector) 是Java 语言对JavaBean类属性、事件的一种缺省处理方法。
　　JavaBean是一种特殊的类，主要用于传递数据信息，这种类中的方法主要用于访问私有的字段，且方法名符合某种命名规则。如果在两个模块之间传递信息，可以将信息封装进JavaBean中，这种对象称为“值对象”(Value Object)，或“VO”。方法比较少。这些信息储存在类的私有变量中，通过set()、get()获得。
例如类UserInfo ：

```
package com.peidasoft.instrospector;  

public class UserInfo {  

    private long userId;  
    private String userName;  
    private int age;  
    private String emailAddress;  

    public long getUserId() {  
        return userId;  
    }  

    public void setUserId(long userId) {  
        this.userId = userId;  
    }  

    public String getUserName() {  
        return userName;  
    }  

    public void setUserName(String userName) {  
        this.userName = userName;  
    }  

    public int getAge() {  
        return age;  
    }  

    public void setAge(int age) {  
        this.age = age;  
    }  

    public String getEmailAddress() {  
        return emailAddress;  
    }  

    public void setEmailAddress(String emailAddress) {  
        this.emailAddress = emailAddress;  
    }  
}

```

在类UserInfo中有属性userName，那我们可以通过getUserName, setUserName来得到其值或者设置新的值。通过getUserName/setUserName来访问userName属性，这就是默认的规则。Java JDK中提供了一套API用来访问某个属性的getter/setter方法，这就是内省。

# JDK内省类库：

　PropertyDescriptor类：(属性描述器)
　　PropertyDescriptor类表示JavaBean类通过存储器导出一个属性。主要方法：
　　1. getPropertyType()，获得属性的Class对象；
　　2. getReadMethod()，获得用于读取属性值的方法；
　　3. getWriteMethod()，获得用于写入属性值的方法；
　　4. hashCode()，获取对象的哈希值；
　　5. setReadMethod(Method readMethod)，设置用于读取属性值的方法；
　　6. setWriteMethod(Method writeMethod)，设置用于写入属性值的方法。

实例代码如下：

```
package com.peidasoft.instrospector;  

import java.beans.BeanInfo;  
import java.beans.Introspector;  
import java.beans.PropertyDescriptor;  
import java.lang.reflect.Method;  

public class BeanInfoUtil {  

    // 设置bean的某个属性值  
    public static void setProperty(UserInfo userInfo, String userName) throws Exception {  
        // 获取bean的某个属性的描述符  
        PropertyDescriptor propDesc = new PropertyDescriptor(userName, UserInfo.class);  
        // 获得用于写入属性值的方法  
        Method methodSetUserName = propDesc.getWriteMethod();  
        // 写入属性值  
        methodSetUserName.invoke(userInfo, "wong");  
        System.out.println("set userName:" + userInfo.getUserName());  
    }  

    // 获取bean的某个属性值  
    public static void getProperty(UserInfo userInfo, String userName) throws Exception {  
        // 获取Bean的某个属性的描述符  
        PropertyDescriptor proDescriptor = new PropertyDescriptor(userName, UserInfo.class);  
        // 获得用于读取属性值的方法  
        Method methodGetUserName = proDescriptor.getReadMethod();  
        // 读取属性值  
        Object objUserName = methodGetUserName.invoke(userInfo);  
        System.out.println("get userName:" + objUserName.toString());  
    }  
}
```

**Introspector类:**
　　将JavaBean中的属性封装起来进行操作。在程序把一个类当做JavaBean来看，就是调用Introspector.getBeanInfo()方法，得到的BeanInfo对象封装了把这个类当做JavaBean看的结果信息，即属性的信息。
　　getPropertyDescriptors()，获得属性的描述，可以采用遍历BeanInfo的方法，来查找、设置类的属性。具体代码如下：

```
import java.beans.BeanInfo;  
import java.beans.Introspector;  
import java.beans.PropertyDescriptor;  
import java.lang.reflect.Method;  

public class BeanInfoUtil {  

    // 通过内省设置bean的某个属性值  
    public static void setPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {  
        // 获取bean信息  
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);  
        // 获取bean的所有属性列表  
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();  
        // 遍历属性列表，查找指定的属性  
        if (proDescrtptors != null && proDescrtptors.length > 0) {  
            for (PropertyDescriptor propDesc : proDescrtptors) {  
                // 找到则写入属性值  
                if (propDesc.getName().equals(userName)) {  
                    Method methodSetUserName = propDesc.getWriteMethod();  
                    methodSetUserName.invoke(userInfo, "alan");  // 写入属性值  
                    System.out.println("set userName:" + userInfo.getUserName());  
                    break;  
                }  
            }  
        }  
    }  

    // 通过内省获取bean的某个属性值  
    public static void getPropertyByIntrospector(UserInfo userInfo, String userName) throws Exception {  
        BeanInfo beanInfo = Introspector.getBeanInfo(UserInfo.class);  
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();  
        if (proDescrtptors != null && proDescrtptors.length > 0) {  
            for (PropertyDescriptor propDesc : proDescrtptors) {  
                if (propDesc.getName().equals(userName)) {  
                    Method methodGetUserName = propDesc.getReadMethod();  
                    Object objUserName = methodGetUserName.invoke(userInfo);  
                    System.out.println("get userName:" + objUserName.toString());  
                    break;  
                }  
            }  
        }  
    }  
}
```

通过这两个类的比较可以看出，都是需要获得PropertyDescriptor，只是方式不一样：前者通过创建对象直接获得，后者需要遍历，所以使用PropertyDescriptor类更加方便。

```
package com.peidasoft.instrospector;  

public class BeanInfoTest {  

    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
        UserInfo userInfo = new UserInfo();  
        userInfo.setUserName("peida");  
        try {  
            BeanInfoUtil.getProperty(userInfo, "userName");  
            BeanInfoUtil.setProperty(userInfo, "userName");  
            BeanInfoUtil.getProperty(userInfo, "userName");  
            BeanInfoUtil.setPropertyByIntrospector(userInfo, "userName");  
            BeanInfoUtil.getPropertyByIntrospector(userInfo, "userName");  
            BeanInfoUtil.setProperty(userInfo, "age");  // IllegalArgumentException  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
```

**输出结果：**

```
get userName:peida  
set userName:wong  
get userName:wong  
set userName:alan  
get userName:alan  
java.lang.IllegalArgumentException: argument type mismatch  
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)  
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)  
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)  
    at java.lang.reflect.Method.invoke(Method.java:483)  
    at com.peidasoft.instrospector.BeanInfoUtil.setProperty(BeanInfoUtil.java:22)  
    at com.peidasoft.instrospector.BeanInfoTest.main(BeanInfoTest.java:26)
```

说明：BeanInfoUtil.setProperty(userInfo,”age”);报错是应为age属性是int数据类型，而setProperty方法里面默认给age属性赋的值是String类型。所以会爆出argument type mismatch参数类型不匹配的错误信息。



# BeanUtils工具包

　　由上述可看出，内省操作非常的繁琐，所以所以Apache开发了一套简单、易用的API来操作Bean的属性——BeanUtils工具包。
　　BeanUtils工具包：下载：<http://commons.apache.org/beanutils/>，注意：应用的时候还需要一个logging包<http://commons.apache.org/logging/>
　　使用BeanUtils工具包完成上面的测试代码：

```
package com.peidasoft.instrospector;  

import java.lang.reflect.InvocationTargetException;  
import org.apache.commons.beanutils.BeanUtils;  
import org.apache.commons.beanutils.PropertyUtils;  

public class BeanInfoTest {  

    /** 
     * @param args the command line arguments 
     */  
    public static void main(String[] args) {  
        UserInfo userInfo = new UserInfo();  
        userInfo.setUserName("peida");  
        try {  
            BeanUtils.setProperty(userInfo, "userName", "peida");  
            System.out.println("set userName:" + userInfo.getUserName());  
            System.out.println("get userName:" + BeanUtils.getProperty(userInfo, "userName"));  
            BeanUtils.setProperty(userInfo, "age", 18);  
            System.out.println("set age:" + userInfo.getAge());  
            System.out.println("get age:" + BeanUtils.getProperty(userInfo, "age"));  
            System.out.println("get userName type:" + BeanUtils.getProperty(userInfo, "userName").getClass().getName());  
            System.out.println("get age type:" + BeanUtils.getProperty(userInfo, "age").getClass().getName());  
            PropertyUtils.setProperty(userInfo, "age", 8);  
            System.out.println(PropertyUtils.getProperty(userInfo, "age"));  
            System.out.println(PropertyUtils.getProperty(userInfo, "age").getClass().getName());  
            PropertyUtils.setProperty(userInfo, "age", "8");  // IllegalArgumentException  
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {  
            e.printStackTrace();  
        }  
    }  
}  
运行结果：
[java] view plain copy
set userName:peida  
get userName:peida  
set age:18  
get age:18  
get userName type:java.lang.String  
get age type:java.lang.String  

java.lang.Integer  
Exception in thread "main" java.lang.IllegalArgumentException: Cannot invoke com.peidasoft.instrospector.UserInfo.setAge on bean class   
    'class com.peidasoft.instrospector.UserInfo' - argument type mismatch - had objects of type "java.lang.String" but expected signature "int"  
    at org.apache.commons.beanutils.PropertyUtilsBean.invokeMethod(PropertyUtilsBean.java:2181)  
    at org.apache.commons.beanutils.PropertyUtilsBean.setSimpleProperty(PropertyUtilsBean.java:2097)  
    at org.apache.commons.beanutils.PropertyUtilsBean.setNestedProperty(PropertyUtilsBean.java:1903)  
    at org.apache.commons.beanutils.PropertyUtilsBean.setProperty(PropertyUtilsBean.java:2010)  
    at org.apache.commons.beanutils.PropertyUtils.setProperty(PropertyUtils.java:896)  
    at com.peidasoft.instrospector.BeanInfoTest.main(BeanInfoTest.java:32)  
Caused by: java.lang.IllegalArgumentException: argument type mismatch  
    at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)  
    at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)  
    at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)  
    at java.lang.reflect.Method.invoke(Method.java:483)  
    at org.apache.commons.beanutils.PropertyUtilsBean.invokeMethod(PropertyUtilsBean.java:2116)  
    ... 5 more
```


说明：
　　1. 获得属性的值，例如，BeanUtils.getProperty(userInfo, “userName”)，返回字符串。
　　2. 设置属性的值，例如，BeanUtils.setProperty(userInfo, “age”, 8)，参数是字符串或基本类型自动包装。设置属性的值是字符串，获得的值也是字符串，不是基本类型。　　　3\. BeanUtils的特点：
　　1). 对基本数据类型的属性的操作：在WEB开发、使用中，录入和显示时，值会被转换成字符串，但底层运算用的是基本类型，这些类型转到动作由BeanUtils自动完成。
　　2). 对引用数据类型的属性的操作：首先在类中必须有对象，不能是null，例如，private Date birthday=new Date();。操作的是对象的属性而不是整个对象，例如，BeanUtils.setProperty(userInfo, “birthday.time”, 111111);

```
package com.peidasoft.Introspector;  
import java.util.Date;  

public class UserInfo {  

    private Date birthday = new Date(); // 引用类型的属性，不能为null  

    public void setBirthday(Date birthday) {  
        this.birthday = birthday;  
    }  
    public Date getBirthday() {  
        return birthday;  
    }        
}
```


```
package com.peidasoft.Beanutil;  

import java.lang.reflect.InvocationTargetException;  
import org.apache.commons.beanutils.BeanUtils;  
import com.peidasoft.Introspector.UserInfo;  

public class BeanUtilTest {  
    public static void main(String[] args) {  
        UserInfo userInfo=new UserInfo();  
         try {  
            BeanUtils.setProperty(userInfo, "birthday.time","111111");  // 操作对象的属性，而不是整个对象  
            Object obj = BeanUtils.getProperty(userInfo, "birthday.time");    
            System.out.println(obj);            
        }   
         catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }   
         catch (InvocationTargetException e) {  
            e.printStackTrace();  
        }  
        catch (NoSuchMethodException e) {  
            e.printStackTrace();  
        }  
    }  
}
```

PropertyUtils类和BeanUtils不同在于，运行getProperty、setProperty操作时，没有类型转换，使用属性的原有类型或者包装类。由于age属性的数据类型是int，所以方法PropertyUtils.setProperty(userInfo,”age”, “8”)会爆出数据类型不匹配，无法将值赋给属性