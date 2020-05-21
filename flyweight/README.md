## 享元模式

### 定义

享元模式（Flyweight Pattern）主要用于减少创建对象的数量，以减少内存占用和提高性能。这种类型的设计模式属于结构型模式，它提供了减少对象数量从而改善应用所需的对象结构的方式。

主要解决的问题：在有大量对象时，有可能会造成内存溢出，我们把其中共同的部分抽象出来，如果有相同的业务请求，直接返回在内存中已有的对象，避免重新创建。

一般享元模式有是三个参与角色：

抽象享元角色：享元对象抽象基类或者接口，同时定义出对象的外部状态和内部状态的接口和实现。

具体享元角色：实现抽象角色定义的业务。该角色的内部状态处理应该与环境无关，不能出现会有一个操作改变内部状态，同时修改了外部状态。

享元工厂角色：负责管理享元对象池和创建享元对象。

### 应用场景

当系统中多处需要同一组信息时，可以把这些信息封装到一个对象中，然后对该对象进行缓存，这样，一个对象就可以提供给多处需要使用的地方，避免大量同一对象的多次创建，消耗大量的内存空间。享元模式其实就是工厂模式的一个改进机制，享元模式同样要求创建一个或者一组对象，并且就是通过工厂方法生成对象的，只不过享元模式中为工厂方法增加了缓存这一个功能。主要总结为以下应用场景：

1、系统有大量相似对象。

2、需要缓冲池的场景。

### 例子1：使用享元模式实现共享池业务

下面我们来举个例子：每年三四月份是招聘高峰期，同样也是租房市场的高峰期，各个中介机构为了能把房子尽快租出去，往往会采用房源共享的方式，找房子的时候，我们肯定会到各个机构平台去查询一下房源信息包含：地址、格局、价格等。现在我们写一个查询房源信息的例子，可以通过地址、价格等查到相关信息。

- 创建IHource 接口

```java
public interface IHouse {
    void showHouse();
}
```

- 创建House类

```java
@Data
public class House implements IHouse{
    private String address;
    private String  pattern;
    private int price;

    public House(String address,String pattern){
        this.address = address;
        this.pattern = pattern;
    }
    public void showHouse() {
        this.price = new Random().nextInt(500000000);
        System.out.println(String.format("地址：%s，格局:%s，格：%s:元，",this.address,this.pattern,this.price));
    }
}
```

- 创建HouseFactory

```java
public class HouseFactory {
    public static IHouse queryHouse(String address,String pattern){
        return new House(address,pattern);
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        IHouse house = HouseFactory.queryHouse("A社区","两室一厅");
        house.showHouse();
    }
}
```

- 运行结果

![1589245980799](C:\Users\ADMINI~1\AppData\Local\Temp\1589245980799.png)

分析上面的对吗，我们发现客户端进行查询时，系统通过HouseFactory 直接创建了一个房子对象，但是这样的话，当某个瞬间如果有大量的用户请求同一张票的信息时，系统就会创建出大量该房子对象，系统内存压力骤增。而其实更好的做法应该是缓存该房子对象，然后复用提供给其他查询请求，这样一个对象就足以支撑数以千计的查询请求，对内存完全没有压力，使用享元模式可以很好地解决这个问题。我们继续优化我们的代码，只需要HouseFactory 类中进行更改，增加缓存机制:

- 优化HouseFactory

```java
public class HouseFactory {
    private static Map<String,IHouse> housePool = new ConcurrentHashMap<String,IHouse>();
    public static IHouse queryHouse(String address,String pattern){
        String key = address + ":" + pattern;
        if(HouseFactory.housePool.containsKey(key)){
            System.out.println("使用缓存对象" + key);
            return HouseFactory.housePool.get(key);
        }
        System.out.println("首次查询，创建对象：" + key);
        IHouse iHouse = new House(address,pattern);
        HouseFactory.housePool.put(key,iHouse);
        return iHouse;
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        IHouse house = HouseFactory.queryHouse("A社区","两室一厅");
        house.showHouse();
        IHouse house1 = HouseFactory.queryHouse("A社区","两室一厅");
        house1.showHouse();
        IHouse house2 = HouseFactory.queryHouse("A社区","两室一厅");
        house2.showHouse();
        IHouse house3 = HouseFactory.queryHouse("A社区","两室一厅");
        house3.showHouse();
    }
}
```

- 运行结果

![1589246642522](C:\Users\ADMINI~1\AppData\Local\Temp\1589246642522.png)

可以看到第一次查询创建对象之后，后续查询相同房源信息的时候都是使用的缓存对象。

- 类图如下：

![1589246777203](C:\Users\ADMINI~1\AppData\Local\Temp\1589246777203.png)

其中IHouse 就是抽象享元角色，House 就是具体享元角色，HouseFactory就是想元工厂。

### 例子2：数据库连接池

我们经常使用的数据库连接池，因为我们使用Connection对象时主要消耗在简历连接和关闭连接的时候，为了提高Connection在调用时的性能，我们和将Connection对象在调用前创建好缓存起来，用的时候从缓存中取值，用完再放回去，达到资源重复利用的目的。

```java
public class Connectionpool {
    private Vector<Connection> pool;
    
    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "root";
    private String driverClassName = "com.mysql.jdbc.Driver";
    private int poolSize = 100;
    public Connectionpool(){
        pool = new Vector<Connection>(poolSize);
        try {
            Class.forName(driverClassName);
            for(int i=0;i<poolSize;i++){
                Connection connection = DriverManager.getConnection(url,username,password);
                pool.add(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized Connection getConnection() {
        if (pool.size() > 0) {
            Connection connection = pool.get(0);
            pool.remove(connection);
            return connection;
        }
        return null;
    }
    public  synchronized void release(Connection connection){
        pool.add(connection);
    }
}
```

### 享元模式在源码中的应用

- Spring 中的享元模式

Java 中将String 类定义为final（不可改变的），JVM中字符串一般保存在字符串常量池中，java 会确保一个字符串在常量池中只有一个拷贝，这个字符串常量池在JDK6 以前是位于常量池中，位于永久代，在JDK7中，JVM将其从永久代拿出来放在堆中。

我们先做一个测试：

```java
public class StringTest {
    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "ab";
        String s3 = "a" + "b";
        String s4 = "a" + new String("b");
        String s5 = new String("ab");
        String s6 = s5.intern();
        String s7 = "a";
        String s8 = "b";
        String s9 = s7 + s8;
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);
        System.out.println(s1 == s9);
        System.out.println(s4 == s5);
        System.out.println(s1 == s6);
    }
}
```

运行结果：

![1589329198401](C:\Users\ADMINI~1\AppData\Local\Temp\1589329198401.png)



String 类的final 修饰的，以字面量的形式创建String变量时，JVM会在编译期间就把该字面量 “ab”。放到字符串常量池中，由java 程序启动的时候就已经加载到内存中了。这个字符串常量池的特点就是有且仅有一份相同的字面量，如果有其他相同的字面量，JVM则返回这个字面量的引用，如果没有相同的字面量，则在字符串常量池创建这个字面量并返回它的引用。

由于s2指向的字面量 “ab”在常量池中已经存在了（s1 先于s2），于是JVM就返回这个字面量 绑定的引用，所以s1 ==  s2

s3中字面的拼接就是 ab，jvm在编译期间就已经对他进行优化，所以s1和s3也是相等的

s4中的new String("b")生成了两个对象，b ，new Sting("b"),b存在于字符串常量池，new String("b")存在于堆中，Stirng s4 = “a” + new String("b"),其实是两个对象的相加，编译器不会进行优化，相加的结果存在于堆中，而s1 存在于字符串常量池中，所以不等。s1 == s9 的原理是一样的。

s4 == s5 两个相加的结果都在堆中，所以不相等。

s1 == s6中，s5.intern()方法能使得一个位于堆中的字符串在运行期间动态地加入到字符串常量池中（字符串常量池的内容就是程序启动的时候就已经加载好了），如果字符串常量池中有该对象对应的字面量，则返回该字面量在字符串常量池中的引用，否则，创建复制一份该字面量到字符串常量池并返回它的引用，因为s1 == s6 为true。

- Integer 的享元模式

我们先看一个例子：

```java
public class IntegerTest {
    public static void main(String[] args) {
        Integer a = Integer.valueOf(100);
        Integer b = 100;

        Integer c = Integer.valueOf(1000);
        Integer d = 1000;

        System.out.println("a == b:" + (a == b));
        System.out.println("d == d:" + (c == d));
    }
}
```

运行结果：

![1589329268591](C:\Users\ADMINI~1\AppData\Local\Temp\1589329268591.png)

之所以会是这样的结果，是因为Integer也是用到享元模式，我们看下Integer的源码：

```JAVA
public static Integer valueOf(int i) {
    if (i >= IntegerCache.low && i <= IntegerCache.high)
        return IntegerCache.cache[i + (-IntegerCache.low)];
    return new Integer(i);
}
```

我们发现Integer 源码中的valueOf()方法做了一个条件判断，如果目标值在-128到127之间，则直接从缓存中取值，否则创建新的对象。那么JDK为何要这样做呢？因为在-128到127之间的数据在int范围内是使用最为频繁的，为了节省频繁创建对象带来的内存消耗，这里就用到了享元模式，来提高性能。

- Long中的享元模式

```java
private static class LongCache {
    private LongCache(){}

    static final Long cache[] = new Long[-(-128) + 127 + 1];

    static {
        for(int i = 0; i < cache.length; i++)
            cache[i] = new Long(i - 128);
    }
}
```

同样Long中也存在缓存，不过不能指定缓存最大值。

- Apache Commons Pool2中的享元模式

对象池的基本思路是：将用过的对象保存起来，等下一次需要这种对象的时候，再拿出来重复使用，从而在一定程度上减少频繁创建对象所造成的开销。用于充当保存对象“容器”的对象，被称为对象池。

Apache Commons Pool 实现了对象池的功能。定义了对象的生成、销毁、激活、钝化等操作及其状态转化，并提供几个默认的对象池实现。有几个重要的对象：

PooledObject(池对象)：用于封装对象（如：线程、数据库连接、TCP连接），将其包裹成可被池管理的对象

PooledObjectFactory(池对象工厂)：定义了操作PooledObject 实例声明周期的一些方法

PooledObjectFactory 必须实现线程安全

ObjectPool(对象池)：ObjectPool 负责管理PooledObject ，如借出对象，返回对象，校验对象，有多少激活对象，有多少空闲对象。

### 享元模式的内部状态和外部状态

享元模式的定义为我们提出了两个要求：细粒度和共享对象。因为要求细粒度对象，所以不可避免地会使对象数量多且性质相近，此时我们就将这些对象的信息分成两个部分：内部状态和外部状态

内部状态指对象共享出来的信息，存储在享元对象内部并且不会随着环境的改变而改变；外部状态指对象得以依赖的一个标记，是随着环境改变而改变的，不可共享的状态。

比如，连接池中的连接对象，保存在连接对象中的用户名、密码、连接url等信息，在创建对象的时候就设置好了，不会随着环境的改变而改变，这些为内部状态。而每个连接要回收利用时，我们需要给他标记为可用状态，这些为外部状态。

### 优缺点

**优点**

1、减少对象的创建、降低内存中对象数量，降低系统的内存，提高效率。

2、减少内存之外的其他资源占用

**缺点**

1、关注内、外状态，关注线程安全问题

2、使得系统、程序的逻辑复杂化

