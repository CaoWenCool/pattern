[toc]****_````_****



## 工厂模式

### <font face="黑体" color="blue" size="5">背景</font>

<font face="宋体" size="3">工厂模式是Java中常用的设计模式之一。这种类型的设计模式属于创建型设计模式，它提供了一种创建对象的最佳方式。在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。</font>

### <font face="黑体" color="blue" size="5">分类</font>

<font face="宋体" size="3">工厂模式主要分为 **简单工厂模式**、**工厂方法模式**和**抽象工厂模式**</font>

### <font face="黑体" color="blue" size="5">简单工厂模式</font>

<font face="宋体" size="3">简单工厂模式是指有一个工厂对象决定创建出哪一种产品类的实例。</font>

<font face="宋体" size="3">简单工厂适用于工厂类负责创建的对象较少的场景，且客户端只需要传入工厂类的参数，对于如何创建对象的逻辑不需要关心。</font>

#### <font face="黑体" color="green" size="5">简单工厂的例子</font>

<font face="宋体" size="3">我们以电脑生产商为例。电脑生产生可以生产 联想、华为、苹果等电脑。</font>

- **代码实现 **

<font face="宋体" size="3">首先我们创建一个电脑标准的IComputer接口</font>

```java
public interface IComputer {
    /**
     * 选择CPU芯片
     */
    void selectCpu();
    /**
     * 安装操作系统
     */
    void installSystem();
}
```

<font face="宋体" size="3">创建一个各个电脑的工厂类：例如：AppleComputerFactory</font>

```java
public class AppleComputerFactory implements IComputer{
    @Override
    public void selectCpu() {
        System.out.println("选择苹果芯片");
    }
    @Override
    public void installSystem() {
        System.out.println("安装 苹果操作系统");
    }
}
```

```java
public class ThinkComputerFactory implements IComputer{
    @Override
    public void selectCpu() {
        System.out.println("选择酷睿系列的CPU");
    }
 
    @Override
    public void installSystem() {
        System.out.println("选择 Windows 系列的操作系统");
    }
}
```

```java
public class HuaWeiComputerFactory implements IComputer{
    @Override
    public void selectCpu() {
        System.out.println("选择麒麟海思的CPU");
    }
 
    @Override
    public void installSystem() {
        System.out.println("选择鸿蒙操作系统");
    }
}
```



- **客户端调用**

```java
public class SimpleFactoryTest {
    public static void main(String[] args) {
        IComputer computer = new HuaWeiComputerFactory();
        computer.installSystem();
        computer.selectCpu();
    }
}
```

- **运行结果**

![简单工厂运行结果](factory/image/simpleFactory1.jpg)

<font face="宋体" size="3">上面的代码中，父类IComputer 指向子类 HuaWeiComputerFactory 的引用，应用层的代码需要依赖于 HuaWeiComputerFactory，如果我们的工厂需要生产更多的品牌的电脑，我们需要继续增加 类似于 华硕的工厂类，戴尔的工厂类等等，那么我们的客户端的代码就会变得越来越臃肿。因为，我们需要想办法来吧这种依赖性减弱，把创建的细节隐藏。因此，我们吧简单工厂模式进行优化</font>

- **简单工厂的优化**

<font face="宋体" size="3">创建ComputerFactory 工厂类</font>

```java
public class ComputerFactory {
 
    public static IComputer create(String name){
        if("Apple".equals(name)){
            return new AppleComputerFactory();
        }else if("HuaWei".equals(name)){
            return new HuaWeiComputerFactory();
        }else if("ThinkPad".equals(name)){
            return new ThinkComputerFactory();
        }else{
            return null;
        }
    }
}
```

- **客户端调用如下：**

```java
ComputerFactory factory = new ComputerFactory();
factory.create("HuaWei");
```



<font face="宋体" size="3">客户端调用虽然简单了，但是如果我们的业务继续增加，那么工厂中的create 方法就要根据产品链的丰富每次进行修改代码。不符合开闭原则，因为我们利用反射技术记性优化。</font>

```java
    public IComputer create1(String className){
        if(!(null == className || "".equals(className))){
            try {
                return (IComputer) Class.forName(className).newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
```



```java
ComputerFactory factory = new ComputerFactory();
IComputer computer = factory.create1("com.example.factory.simplefactory.HuaWeiComputerFactory");
computer.selectCpu();
computer.installSystem();
```

<font face="宋体" size="3">优化之后，产品不断增加我们也不需要修改 ComputerFactory 类了，但是，有个问题是，方法参数是字符串，可控性有待提升。因此我们在优化：</font>

- **简单工厂的二次优化：**

```java
public IComputer create2(Class<? extends IComputer> clazz){
    if(null != clazz){
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    return null;
}
```

```java
ComputerFactory factory = new ComputerFactory();
IComputer computer = factory.create2(HuaWeiComputerFactory.class);
computer.installSystem();
computer.selectCpu();
```

####  <font face="黑体" color="green" size="5">简单工厂模式在源码的体现</font>

- **例如 Calendar类**

```java
private static Calendar createCalendar(TimeZone zone,
                                       Locale aLocale)
{
    CalendarProvider provider =
        LocaleProviderAdapter.getAdapter(CalendarProvider.class, aLocale)
                             .getCalendarProvider();
    if (provider != null) {
        try {
            return provider.getInstance(zone, aLocale);
        } catch (IllegalArgumentException iae) {
            // fall back to the default instantiation
        }
    }
 
    Calendar cal = null;
 
    if (aLocale.hasExtensions()) {
        String caltype = aLocale.getUnicodeLocaleType("ca");
        if (caltype != null) {
            switch (caltype) {
            case "buddhist":
            cal = new BuddhistCalendar(zone, aLocale);
                break;
            case "japanese":
                cal = new JapaneseImperialCalendar(zone, aLocale);
                break;
            case "gregory":
                cal = new GregorianCalendar(zone, aLocale);
                break;
            }
        }
    }
    if (cal == null) {
        // If no known calendar type is explicitly specified,
        // perform the traditional way to create a Calendar:
        // create a BuddhistCalendar for th_TH locale,
        // a JapaneseImperialCalendar for ja_JP_JP locale, or
        // a GregorianCalendar for any other locales.
        // NOTE: The language, country and variant strings are interned.
        if (aLocale.getLanguage() == "th" && aLocale.getCountry() == "TH") {
            cal = new BuddhistCalendar(zone, aLocale);
        } else if (aLocale.getVariant() == "JP" && aLocale.getLanguage() == "ja"
                   && aLocale.getCountry() == "JP") {
            cal = new JapaneseImperialCalendar(zone, aLocale);
        } else {
            cal = new GregorianCalendar(zone, aLocale);
        }
    }
    return cal;
}
```

- **logback的LoggerFactory 中的多个重载方法**

```java
public static Logger getLogger(String name) {
    ILoggerFactory iLoggerFactory = getILoggerFactory();
    return iLoggerFactory.getLogger(name);
}
public static Logger getLogger(Class<?> clazz) {
    Logger logger = getLogger(clazz.getName());
    if (DETECT_LOGGER_NAME_MISMATCH) {
        Class<?> autoComputedCallingClass = Util.getCallingClass();
        if (autoComputedCallingClass != null && nonMatchingClasses(clazz, autoComputedCallingClass)) {
            Util.report(String.format("Detected logger name mismatch. Given name: \"%s\"; computed name: \"%s\".", logger.getName(), autoComputedCallingClass.getName()));
            Util.report("See http://www.slf4j.org/codes.html#loggerNameMismatch for an explanation");
        }
    }
    return logger;
}
```

<font face="宋体" size="3">简单工厂也有缺点：工厂类的职责相对过重，不易于扩展过于复杂的产品结构</font>

### <font face="黑体" color="blue" size="5">工厂方法模式</font>

<font face="宋体" size="3">工厂方法模式是指定一个创建对象的接口，但让实现这个接口的类来决定实例化那个类，工厂方法让类的实例化推迟到子类中进行。在工厂方法模式中，用户只需要关心所需产品对应的工厂，无须关心创建的细节，而且加入新的产品符合开闭原则。</font>

<font face="宋体" size="3">工厂方法模式主要解决产品扩展的问题，在简单工厂中，随着产品链的丰富，如果每个电脑的创建逻辑都是有区别的，工厂的职责就会变得越来越多，有点像万能的工厂，并不利于维护。根据单一职责的原则，我们将智能进行拆分，专人干专事。使得苹果工厂创建苹果电脑，华为工厂创建华为电脑。代码如下：</font>

#### <font face="黑体" color="green" size="5">工厂方法模式的例子</font>

- 代码实现

IComputerFactory 接口

```
public interface IComputerFactory {
    IComputer create();
}
```

创建子工厂：

```java
public class AppleComputerFactory implements IComputerFactory{
    @Override
    public IComputer create() {
        return new AppleComputer();
    }
}
```

- 测试代码：

```java
public class MethodFactoryTest {
 
    public static void main(String[] args) {
        IComputerFactory factory = new AppleComputerFactory();
        IComputer computer = factory.create();
        computer.selectCpu();
        computer.installSystem();
    }
}
```

- 类图如下：

```
  ![](https://github.com/CaoWenCool/pattern/blob/master/factory/image/classMethodFactory.jpg)
```

#### <font face="黑体" color="green" size="5">工厂方法模式的适用场景：</font>

<font face="宋体" size="3">1、创建对象需要大量重复的代码</font>

<font face="宋体" size="3">2、客户端不依赖于产品类实例如何被创建、实现等细节</font>

<font face="宋体" size="3">3、一个类通过其子类来指定创建那个对象</font>

#### <font face="黑体" color="green" size="5">工厂方法的缺点：</font>

<font face="宋体" size="3">1、类的个数容易过多，增加复杂度</font>

<font face="宋体" size="3">2、增加了系统的抽象性和理解难度</font>

### <font face="黑体" color="blue" size="5">抽象工厂模式</font>

<font face="宋体" size="3">抽象工厂模式是指提供一个创建一系列相关或者相互依赖对象的接口，无须指定他们具体的类。客户端不依赖于产品类实例如果被创建、实现等细节，强调的是一系列 相关的产品对象（属于同一个产品族）一起使用创建对象需要大量重复的代码。需要提供一个产品的库，所有的产品以同样的接口出现，从而是客户端不依赖于具体实现。</font>

<font face="宋体" size="3">我们还是以电脑工厂为例，我们的电脑在制作过程中，需要先研发数据自己的CPU，进口相关的零件等等。</font>

#### <font face="黑体" color="green" size="5"> 抽象工厂的例子</font>

- 创建代码

<font face="宋体" size="3">1、创建研发CPU和进口零件的接口</font>

```java
public interface developmentCPU {
 
    void  developCPU();
}
```



```java
public interface ImportParts {
 
    void importParts();
 
}
```

<font face="宋体" size="3">2、创建抽象类的接口</font>



```java
public interface ComputerFactory {
    developmentCPU createCPU();
    ImportParts createParts();
}
```

<font face="宋体" size="3">3、创建苹果工厂研发CPU和进口零件的类</font>



```java
public class AppleCPU implements developmentCPU{
    @Override
    public void developCPU() {
        System.out.println("研发苹果的CPU");
    }
}
```



```java
public class AppleParts implements ImportParts{
    @Override
    public void importParts() {
        System.out.println("苹果工厂开始进口零件");
    }
}
```

<font face="宋体" size="3">4、创建苹果工厂类</font>



```java
public class AppleFactory implements ComputerFactory{
    @Override
    public developmentCPU createCPU() {
        return new AppleCPU();
    }
 
    @Override
    public ImportParts createParts() {
        return new AppleParts();
    }
}
```

<font face="宋体" size="3">5、创建苹果工厂测试类：</font>

```java
public class AbstractFactoryTest {
 
    public static void main(String[] args) {
        AppleFactory factory = new AppleFactory();
        factory.createCPU().developCPU();
        factory.createParts().importParts();
    }
}
```

- 运行结果

![](https://github.com/CaoWenCool/pattern/blob/master/factory/image/abstractFactory.jpg)

#### <font face="黑体" color="green" size="5">抽象工厂的优缺点</font>

<font face="宋体" size="3">1、规定了所有可能被创建的产品集合，产品族中扩展新的产品困难，需要修改抽象工厂的接口</font>

<font face="宋体" size="3">2、增加系统的抽象性和理解难度</font>

### <font face="黑体" color="green" size="5">项目源代码</font>