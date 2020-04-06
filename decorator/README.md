## 装饰者模式

## <font face="黑体" color="blue" size="5">定义</font>

<font face="宋体" size="3">装饰者模式是指在不改变原有对象的基础之上，将功能附加到对象上，提供了比继承更有弹性的替代方案（扩展原有的对象的功能），属于结构型模式。装饰者模式在我们的生活中应用也是比较多的，比如，买车子选配置，卖电脑增加配置等等。给对象扩展一些额外的功能。</font>

## <font face="黑体" color="blue" size="5">适用场景</font>

<font face="宋体" size="3">1、用于扩展一个类的功能或者给一个类添加附加职责。</font>

<font face="宋体" size="3">2、动态的给一个对象添加功能，这些功能在动态的撤销。</font>

## <font face="黑体" color="blue" size="5">例子</font>

<font face="宋体" size="3">我们来看这么一个场景，假如我们去买电脑，买完电脑的时候我们可能觉得电脑的配置不够高，我们会增加一些配置，例如选择SSD固态硬盘，增加一个16G内存条等等。</font>

- **创建一个电脑类:**

```java
public class Computer {
    //获得商品信息
    protected String getMsg(){
        return "电脑";
    }
    //获得商品价格
    public int getPrice(){
        return 3888;
    }
}
```

- **创建一个电脑加SSD固态硬盘的类：**

```java
public class ComputerAddSSD extends Computer{
    //商品信息中增加了SSD固态
    @Override
    protected String getMsg() {
        return super.getMsg() + "SSD固态硬盘";
    }
    //整体价格需要加上1000元
    @Override
    public int getPrice() {
        return super.getPrice() + 1000;
    }
}

```

- **创建一个加了固态和内存条的类:**

```java
public class ComputerAddSSDAndMemory extends ComputerAddSSD{
    //选择电脑不仅加了SSD还加了内存条
    @Override
    protected String getMsg() {
        return super.getMsg() + "加了一个16G的内存条";
    }
    //价格又加了500元
    @Override
    public int getPrice() {
        return super.getPrice() + 500;
    }
}
```

- **客户端测试代码**

```java
public class ComputerTest {
    public static void main(String[] args) {
        Computer computer = new Computer();
        System.out.println(computer.getMsg() + ",价格：" + computer.getPrice());
        ComputerAddSSD computerAddSSD = new ComputerAddSSD();
        System.out.println(computerAddSSD.getMsg() + ",价格：" + computerAddSSD.getPrice());
        ComputerAddSSDAndMemory computerAddSSDAndMemory = new ComputerAddSSDAndMemory();
        System.out.println(computerAddSSDAndMemory.getMsg() + ",价格：" + computerAddSSDAndMemory.getPrice());
    }
}
```

- **运行结果：**

![1585533485055](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/computer1.jpg)

<font face="宋体" size="3">运行结果，虽然是正确的，但是如果我们要加一个SSD和两个16G内存条的话，这个类是没有办法创建出来的，也无法自动计算出价格的，除非再创建有一个类来做定制。但是，如果需求在继续变化呢？一直这么加显然是不合理的。那么我们就用装饰者模式来解决这个问题。</font>

- **创建一个抽象的电脑类：**

```java
public abstract class Computer {
    //获得商品信息
    protected abstract String getMsg();
    //获得商品价格
    protected abstract int getPrice();
}
```

- **创建一个电脑的套餐基础类：**

```java
public class ComputerMeal extends Computer{
    protected String getMsg() {
        return "电脑";
    }
    protected int getPrice() {
        return 3888;
    }
}
```

- **创建一个扩展套餐的抽象装饰者类**

```java
public class ComputerMealDecorator extends Computer{
    private Computer computer;
    public ComputerMealDecorator(Computer computer){
        this.computer  = computer;
    }
    protected String getMsg() {
        return this.computer.getMsg();
    }

    protected int getPrice() {
        return this.computer.getPrice();
    }
}
```

- **创建一个SSD的装饰类**

```java
public class ComputerSSDDecorator extends ComputerMealDecorator{
    public ComputerSSDDecorator(Computer computer) {
        super(computer);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "1个SSD固态硬盘";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 1000;
    }
}
```

创建一个内存条的装饰类

```java
public class ComputerMealMemory extends ComputerMealDecorator{

    public ComputerMealMemory(Computer computer) {
        super(computer);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "1个16G的内存条";
    }
    @Override
    protected int getPrice() {
        return super.getPrice() + 500;
    }
}
```

- **测试类**

```java
public class ComputerTest {
    public static void main(String[] args) {
        Computer computer;
        //我要买电脑
        computer = new ComputerMeal();
        //机械硬盘太差了，我要SSD固态
        computer = new ComputerSSDDecorator(computer);
        //内存太小了，我要加内存条
        computer = new ComputerMealMemory(computer);
        //内存还是太小了，我再加跟内存条
        computer = new ComputerMealMemory(computer);
        System.out.println(computer.getMsg() + ",价格为：" + computer.getPrice());
    }
}
```

- **运行结果：**

![1585535231157](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/computer2.jpg)

- **类图：**

![1585535302071](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/computer3.jpg)

## <font face="黑体" color="blue" size="5">例子2</font>

<font face="宋体" size="3">我们的登陆功能，经常可能需要进行扩展。例如增加第三方等，虽然我们可以用适配器模式进行设计，但是我们此时用装饰者来试一下。</font>

- **用户类：**

```java
@Data
public class User {
    private String username;
    private String password;
    private String mid;
    private String info;
}
```

- **返回信息类：**

```java
@Data
public class ResultMsg {
    private int code;
    private String msg;
    private Object data;
    public ResultMsg(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
```

- **定义注册和登陆的接口：**

```java
public interface loginService {
    /**
     * 注册接口
     * @param name
     * @param psw
     * @return
     */
    ResultMsg regist(String name,String psw);

    /**
     * 登陆接口
     * @param name
     * @param psw
     * @return
     */
    ResultMsg login(String name,String psw);
}
```

- **登陆的实现类:**

```java
public class loginServiceImpl implements loginService{
    public ResultMsg regist(String name, String psw) {
        return new ResultMsg(200,"注册成功",new User());
    }

    public ResultMsg login(String name, String psw) {
        return null;
    }
}
```

- **升级之后的代码，创建一个新的接口继承原来的接口：**

```java
public interface loginThirdService extends loginService{
    //QQ登陆
    ResultMsg loginForQQ(String id);
    //微信登陆
    ResultMsg loginForWeChat(String id);
    //手机号登陆
    ResultMsg loginForPhone(String telphone,String code);
    //token 登陆
    ResultMsg loginForToken(String token);
}
```

- **创建新的业务逻辑的处理类，实现新创建的接口：**

```java
public class loginForThirdServiceImpl implements loginThirdService{
    private loginService loginService;

    public loginForThirdServiceImpl(loginService loginService){
        this.loginService = loginService;
    }

    public ResultMsg regist(String name,String psw){
        return loginService.regist(name,psw);
    }

    public ResultMsg login(String name,String psw){
        return loginService.login(name,psw);
    }

    public ResultMsg loginForQQ(String id) {
         System.out.println("ID：" + "的用户开始进行登录操作！");
        return new ResultMsg(200,"QQ登陆成功",new User());
    }

    public ResultMsg loginForWeChat(String id) {
        return new ResultMsg(200,"微信登陆成功",new User());
    }

    public ResultMsg loginForPhone(String telphone, String code) {
        return new ResultMsg(200,"电话登陆成功",new User());
    }

    public ResultMsg loginForToken(String token) {
        return new ResultMsg(200,"TOKEN 登陆成功",new User());
    }
}
```

- **客户端测试代码：**

```java
public class Test {
    public static void main(String[] args) {
        loginThirdService loginThirdService = new loginForThirdServiceImpl(new loginServiceImpl());
        ResultMsg resultMsg = loginThirdService.loginForQQ("123123");
        System.out.println(resultMsg.getMsg());
    }
}
```

- **运行结果：**

![1586178682817](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/login.jpg)



<font face="宋体" size="3">装饰者模式最本质的特征是将原有的类的附加功能抽离出来，简化原有类的逻辑。通过这样的案例，我们可以得出结论，其实抽象的装饰者是可有可无的，具体可以根据业务模型来进行选择。</font>

## <font face="黑体" color="blue" size="5">装饰者模式和适配器模式对比</font>

<font face="宋体" size="3">装饰者和适配者都是包装模式，装饰者也是一种特殊的代理模式。</font>

|      | 装饰者模式                                                   | 适配器模式                                                   |
| ---- | ------------------------------------------------------------ | ------------------------------------------------------------ |
| 定义 | 装饰者和被装饰者都实现同一个 接口，主要目的是为了扩展之后依旧保留OOP关系 | 适配器和被适配者没有必然的联系，通常采用继承或者代理的形式进行包装 |
| 形式 | 是一种非常特殊的适配器模式                                   | 没有层级关系，装饰者模式有层级关系                           |
| 关系 | 满足 is-a 的关系                                             | 满足has -a的关系                                             |
| 功能 | 注重覆盖和扩展                                               | 注重兼容和转换                                               |
| 设计 | 前置考虑                                                     | 后置考虑                                                     |

## <font face="黑体" color="blue" size="5">在源码中的应用</font>

- **InputStream的类结构图：**

![1585536380563](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/inputStream.jpg)

<font face="宋体" size="3">在Spring中的TransactionAwareCacheDecorator类我们可以来尝试一下，这个类主要用来处理事务缓存的。</font>

```java
private final Cache targetCache;
/**
 * Create a new TransactionAwareCache for the given target Cache.
 * @param targetCache the target Cache to decorate
 */
public TransactionAwareCacheDecorator(Cache targetCache) {
   Assert.notNull(targetCache, "Target Cache must not be null");
   this.targetCache = targetCache;
}
/**
 * Return the target Cache that this Cache should delegate to.
 */
public Cache getTargetCache() {
   return this.targetCache;
}
```

<font face="宋体" size="3">TransactionAwareCacheDecorator 就是对Cache的一个包装。</font>

- **MVC中的装饰者模式HttpHeadResponseDecorator类**

```java
public class HttpHeadResponseDecorator extends ServerHttpResponseDecorator {


   public HttpHeadResponseDecorator(ServerHttpResponse delegate) {
      super(delegate);
   }
```

- **Mybatis 中的 处理缓存的设计**

![1585537476952](https://github.com/CaoWenCool/pattern/blob/master/decorator/image/MybarisDecorator.jpg)

```java
public interface Cache {
    String getId();

    void putObject(Object var1, Object var2);

    Object getObject(Object var1);

    Object removeObject(Object var1);

    void clear();

    int getSize();

    ReadWriteLock getReadWriteLock();
}
```

<font face="宋体" size="3">从名字中我们可以看出，比如LruCache 最近最少使用的缓存，FifoCache先入先出算法的缓存，TransactionCache事务相关的缓存都是采用装饰者模式。</font>

## <font face="黑体" color="blue" size="5">装饰者模式的优缺点</font>

- **优点:**

<font face="宋体" size="3">1、装饰者是继承的有力补充，比继承灵活，不改变原有对象的情况下动态地给一个对象扩展功能，即插即用</font>

<font face="宋体" size="3">2、通过使用不同装饰类以及这些装饰类的排列组合，可以实现不同的效果</font>

<font face="宋体" size="3">3、装饰者完全遵循开闭原则</font>

- **缺点：**

<font face="宋体" size="3">1、会出现更多的代码，更多的类，增加程序的复杂性</font>

<font face="宋体" size="3">2、动态装饰时，多层装饰时会更加复杂。</font>

## <font face="黑体" color="blue" size="5">源码地址</font>

[装饰者模式:](https://github.com/CaoWenCool/pattern/tree/master/decorator)

https://github.com/CaoWenCool/pattern/tree/master/decorator