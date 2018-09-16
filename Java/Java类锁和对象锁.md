> [TOC]

# 一、类锁和对象锁

1. 类锁：在代码中的方法上加了`static和synchronized`的锁，或者`synchronized(xxx.class)`
2. 对象锁：在代码中的方法上加了`synchronized`的锁，或者`synchronized(this)`的代码段
3. 方法锁和私有锁：都属于对象锁
    私有锁：在类内部声明一个私有属性如`private Object lock`，在需要加锁的代码段`synchronized(lock)`

# 二、使用注意

1. 类锁和对象锁不会产生竞争，二者的加锁方法不会相互影响。

2. 私有锁和对象锁也不会产生竞争，二者的加锁方法不会相互影响。

3. synchronized直接加在方法上和synchronized(this)都是对当前对象加锁，二者的加锁方法够成了竞争关系，同一时刻只能有一个方法能执行。

# 三、参考资料
*   [Java类锁和对象锁实践](http://ifeve.com/java-locks/)



