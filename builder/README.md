## 建造者模式

### 定义

建造者模式是将一个复杂对象的构建过程与他的表示分离，使得同样的构建过程可以创建不同的表示，属于创建型模式。使用建造者模式对于用户而言只需执行需要建造的类型就可以获得对象，建造过程及细节不需要了解。

建造者模式适用于创建对象需要很多步骤，但是步骤的顺序不一定固定。如果一个对象有非常复杂的内部结构（很多属性），可以将复杂对象的创建和使用进行分离。

### 角色分工

建造者模式的设计主要有四个 角色：

- 产品：要创建的产品类的对象
- 建造者抽象：建造者的抽象类，规范产品对象的各个组成部分的建造，一般由子类实现具体的建造过程
- 建造者：具体的Builder类，根据不同的业务逻辑，具体化对象的各个组成部分的创建
- 调用者：调用具体的建造者，来创建对象的各个部分，在指导者中不涉及具体产品的信息，只负责保证对象各部分完成创建 或按某种顺序创建。

### 应用场景

建造者模式适用于一个具体较多的零件的复杂产品的创建过程，由于需求的变化，组成这个复杂产品的各个零件经常猛烈变化，但是他们的组合方式却相对稳定。

建造者模式适用于以下几种场景：

- 相同的方法，不同的执行顺序，产生不同的结果时。
- 多个部件或者零件，都可以装配到一个对象中，但是产生的结果又不相同。
- 产品类非常复杂 ，或者产品类中的调用顺序不同产生不同的作用
- 当初始化一个对象特别复杂，参数多，而且很多参数都具有默认值 时。

### 例1：基本写法

我们就以课程为例，我们的老师在我给我们备课的时候，往往需要准备PPT课件、课堂笔记、课后作业，讲课等内容。

- 首先我们一个构造一个产品类 Course

```java
@Data
public class Course {
    private String name;
    private String ppt;
    private String homework;
    private String note;
    private String teach;
    @Override
    public String toString() {
        return "Course{" +
                "name='" + name + '\'' +
                ", ppt='" + ppt + '\'' +
                ", homework='" + homework + '\'' +
                ", note='" + note + '\'' +
                ", teach='" + teach + '\'' +
                '}';
    }
}
```

- 创建建造者类 CourseBuilder ，将复杂的构造过程封装起来，构造步骤由调用者决定

```java
public class CourseBuilder {
    private Course course = new Course();
    public void  addName(String name){
        course.setName(name);
    }
    public void  addPPT(String ppt){
        course.setPpt(ppt);
    }
    public  void addHomework(String homework){
        course.setHomework(homework);
    }
    public void addNote(String note){
        course.setNote(note);
    }
    public void addTeach(String teach){
        course.setTeach(teach);
    }
    public Course build(){
        return course;
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        CourseBuilder courseBuilder = new CourseBuilder();
        courseBuilder.addName("计算机原理");
        courseBuilder.addHomework("手写计算机结构图");
        courseBuilder.addNote("做好笔记");
        courseBuilder.addPPT("准备好PPT课件");
        courseBuilder.addTeach("讲课");
        System.out.println(courseBuilder.build());
    }
}
```

- 运行结果

![1590538523239](C:\Users\ADMINI~1\AppData\Local\Temp\1590538523239.png)

- 类图：

![1590538587711](C:\Users\ADMINI~1\AppData\Local\Temp\1590538587711.png)

### 例2：建造者模式的链式写法

在应用中，建造者模式通常采用链式编程的方式构造对象。

- 将Course变为CourseBuilder的内部类

- 将构造步骤添加进入，没完成一个步骤，都返回this

```java
public class CourseBuilder {
    private Course course = new Course();
    public CourseBuilder  addName(String name){
        course.setName(name);
        return this;
    }
    public CourseBuilder  addPPT(String ppt){
        course.setPpt(ppt);
        return this;
    }
    public  CourseBuilder addHomework(String homework){
        course.setHomework(homework);
        return this;
    }
    public CourseBuilder addNote(String note){
        course.setNote(note);
        return this;
    }
    public CourseBuilder addTeach(String teach){
        course.setTeach(teach);
        return this;
    }
    public Course build(){
        return course;
    }

    @Data
    public class Course {
        private String name;
        private String ppt;
        private String homework;
        private String note;
        private String teach;

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    ", ppt='" + ppt + '\'' +
                    ", homework='" + homework + '\'' +
                    ", note='" + note + '\'' +
                    ", teach='" + teach + '\'' +
                    '}';
        }
    }

}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        CourseBuilder courseBuilder = new CourseBuilder()
                .addHomework("手写计算机结构图")
                .addName("计算机原理")
                .addPPT("准备好PPT课件")
                .addNote("做好笔记")
                .addTeach("讲课");
        System.out.println(courseBuilder.build());
    }
}
```

- 类图变化

![1590539775845](C:\Users\ADMINI~1\AppData\Local\Temp\1590539775845.png)

### 源码中的建造者模式

- JDK的 StringBuilder ，他提供了append()方法 ，给我们开放构造步骤，最后调用toString()方法就可以获得一个好的完成字符串。

  ```java
  public StringBuilder append(String var1) {
      super.append(var1);
      return this;
  }
  //super
  public AbstractStringBuilder append(String str) {
          if (str == null)
              return appendNull();
          int len = str.length();
          ensureCapacityInternal(count + len);
          str.getChars(0, len, value, count);
          count += len;
          return this;
      }
  ```

- MyBatis 的 CacheBuilder

```java
public Cache build() {
  setDefaultImplementations();
  Cache cache = newBaseCacheInstance(implementation, id);
  setCacheProperties(cache);
  // issue #352, do not apply decorators to custom caches
  if (PerpetualCache.class.equals(cache.getClass())) {
    for (Class<? extends Cache> decorator : decorators) {
      cache = newCacheDecoratorInstance(decorator, cache);
      setCacheProperties(cache);
    }
    cache = setStandardDecorators(cache);
  } else if (!LoggingCache.class.isAssignableFrom(cache.getClass())) {
    cache = new LoggingCache(cache);
  }
  return cache;
}
```

- MyBatis 中，SqlSessionFactoryBuilder 通过调用build()方法获得的是一个SqlSessionFactory()类：，如下

![1590540291376](C:\Users\ADMINI~1\AppData\Local\Temp\1590540291376.png)

- Spring 中 BeanDefinitionBuilder 通过调用getBeanDefinition()方法获得一个BeanDefinition对象。

![1590540447758](C:\Users\ADMINI~1\AppData\Local\Temp\1590540447758.png)

### 建造者模式的优缺点

**建造者模式的优点**

1、封装性好，创建和使用分离

2、扩展性好，建造类之间独立，一定程度上解耦

**建造者模式的缺点**

1、产生多余的Builder对象

2、产品内部发生变化，建造者都要修改，成本较大

### 建造者 模式和工厂模式的区别

1、建造者模式更加注重方法的调用顺序，工厂模式注重于创建对象。

2、创建对象的力度不同，建造者模式创建复杂的对象，由各种复杂的部件组成，工厂模式创建出来的都一样。

3、关注重点不一样，工厂模式只需要把对象创建出来就可以了，而建造者模式不仅要创建出这个对象，还要知道这个对象是由那些部件组成的。

4、建造者模式根据建造过程中的顺序不一样，最终的对象部件组成也不一样。

