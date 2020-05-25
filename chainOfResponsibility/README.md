## 责任链模式

### 定义

责任链模式是将链中每一个节点看作是一个对象，每个节点处理的请求均不同，且内部自动维护一个下一节点对象。当一个请求从链式的首端发出时，会沿着链的路径依次传递给每一个节点对象，直至没有处理这个请求为止。属于行为型模式。

是多个对象都有机会处理请求，从而避免了请求的发送者和接收者之间的耦合关系。将这些对象连城一条链并沿着这条链传递该请求，直到有一个对象处理它为止。

### 应用场景

我们经常提到的有关 部门就是典型的责任链模式，我们大家或多或少都找过有关部门办理事情吧，往往我们为了 办件事需要奔跑与各个部门之间，知道所有部门都办理了，这件事情才算结束。

责任链模式主要解耦了请求与处理，客户只需将请求发送到链上即可，无需关心请求的具体内容和处理细节，请求还会自动进行传递直至有节点对象进行处理。适用于以下应用场景：

- 多个对象可以处理同一请求，但具体哪个对象处理则在运行时动态决定
- 在不明确指定接收者的情况下，向多个对象中的一个提交一个请求
- 可动态指定一组对象处理请求

### 角色分工

责任链模式主要包含两种角色：

- 抽象处理者：定义一个请求处理的方法，并维护一个下一个处理节点Handler对象的引用
- 具体处理者:对请求进行处理，如果不感兴趣，则进行转发

责任链模式的本质是解耦请求与处理，让请求在处理链中能进行传递与被处理，理解责任链模式应当理解的是其模式而不是其具体表现，责任链模式的独到之处是其将节点褚丽哲组合成了链式结构，并允许节点自身决定是否进行请求处理或者 转发，相当于请求流动了起来。

### 例1：利用责任链模式进行数据校验拦截

我想大家应该都有设计或者使用过登陆模块吧，登陆模块的设计其实就是我们应用责任链模式的典型场景，接下来，我将用责任链模式来模拟登陆的实现

- 创建用户类

```java
@Data
public class User {
    public User(String name,String pwd){
        this.name = name;
        this.pwd = pwd;
    }
    //用户名
    private String name;
    //密码
    private String pwd;
    //角色
    private String role;
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
```

- 创建Handler抽象类

```java
public abstract class Handler {
    protected Handler chainHandler;
    public  void next(Handler handler){
        this.chainHandler = handler;
    }
    public abstract void doHandler(User user);
}
```

next 用户表示下一个节点

- 空校验节点，校验用户是否存在的节点以及校验用户权限的节点

```java
public class ValidateEmptyHandler extends Handler{
    public void doHandler(User user) {
        if(StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPwd())){
            System.out.println("账号或者密码为空");
            return;
        }
        System.out.println("为空校验通过，继续执行校验程序");
        chainHandler.doHandler(user);
    }
}
```

```java
public class ValidateExistsHandler extends Handler{
    public void doHandler(User user) {
        //主要校验用户是否存在,校验存在，并且添加用户的角色信息
        user.setRole("系统管理员");
        chainHandler.doHandler(user);
        System.out.println("用户存在，并且添加了用户角色信息，登陆成功");
    }
}
```

```java
public class AuthHandler extends Handler{
    public void doHandler(User user) {
        //校验角色的权限
        if(!"超级管理员".equals(user.getRole())){
            System.out.println("您不是超级管理员，没有权限操作");
            return;
        }
        System.out.println("获取您的操作列表，允许操作");
    }
}
```

- 创建UserService 为用户的请求处理类

```java
public class UserService {
    public void login(String name,String pwd){
       Handler emptyHandler = new ValidateEmptyHandler();
       Handler existsHandler = new ValidateExistsHandler();
       Handler authHandler = new AuthHandler();
       emptyHandler.next(existsHandler);
       existsHandler.next(authHandler);

       emptyHandler.doHandler(new User(name,pwd));
    }
}
```

- 创建测试类

```java
public class Test {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.login("张三","123456");
    }
}
```

- 运行结果：

![1590191842337](C:\Users\ADMINI~1\AppData\Local\Temp\1590191842337.png)

其实我们平时使用的很多权限校验框架都是运用这样一个原理，将各个维度的 权限 处理解耦之后再串联起来，各自处理各自相关的职责。如果职责与自己不相关则抛给链上的下一个Handler。

### 例2 ，结合建造者模式优化

因为责任链模式具备链式结构，从上面的代码中，我们可以看到负责组装链式结构的角色是UserService，当链式结构较长时，UserService的工作会非常繁琐，并且UserService 代码相对臃肿，且后续更改处理者或者消息类型时，都必须在UserService 中进行修改，不符合开闭原则。产生这些问题的原因就是因为链式结构的组装过于复杂，而对于复杂结构的创建，很自然我们会想到建造者模式，使用建造者模式，我们完全可以对UserService指定的处理节点进行自动链式组装，用户只需要指定处理节点对象，其他任何事情都无需关心，并且客户指定的处理节点对象顺序不同，构造出来的 链式结构也随之不同。

- 我们首先改造一下我们的Handler

```java
public abstract class Handler<T> {
    protected Handler chainHandler;
    public  void next(Handler handler){
        this.chainHandler = handler;
    }
    public abstract void doHandler(User user);
    public static class Builder<T>{
        private Handler<T> head;
        private Handler<T> tail;
        public Builder<T> addHandler(Handler<T> handler){
            if(this.head == null){
                this.head = this.tail = handler;
                return this;
            }
            this.tail.next(handler);
            this.tail = handler;
            return this;
        }
        public Handler<T> build(){
            return this.head;
        }
    }
}
```

```java
public class UserService {
    public void login(String name,String pwd){
        Handler.Builder builder = new Handler.Builder();
        builder.addHandler(new ValidateEmptyHandler())
                .addHandler(new ValidateExistsHandler())
                .addHandler(new AuthHandler());
        builder.build().doHandler(new User(name,pwd));
    }
}
```

因为建造者模式要构建的是节点褚丽哲，因此我们把Builder 作为Handler 静态内部类，并且因为客户端无需进行链式组装，因此我们还可以把链式组装方法next()方法设置为private，使得Handler 更加高内聚，

### 在源码中体现

- Filter

```java
public interface Filter {
    default void init(FilterConfig filterConfig) throws ServletException {
    }

    void doFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;

    default void destroy() {
    }
}
```

这个Filter 接口定义非常简单，相当于责任链模型中的Hadnler 抽象角色，那么他是如何完成责任链的呢？我们来看另一个类FileterChain，

```java
public interface FilterChain {
    void doFilter(ServletRequest var1, ServletResponse var2) throws IOException, ServletException;
}
```

FilterChain也只是定义了一个doFilter()方法，那么他们是怎么串联成一个责任链的呢？实际上J2EE只是定义了一个规范，具体逻辑处理是由使用者自己来实现的。我们来看下Spring的MockFilterChain

```java
public class MockFilterChain implements FilterChain {
    @Nullable
    private ServletRequest request;
    @Nullable
    private ServletResponse response;
    private final List<Filter> filters;
    @Nullable
    private Iterator<Filter> iterator;
        public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
        Assert.notNull(request, "Request must not be null");
        Assert.notNull(response, "Response must not be null");
        Assert.state(this.request == null, "This FilterChain has already been called!");
        if (this.iterator == null) {
            this.iterator = this.filters.iterator();
        }

        if (this.iterator.hasNext()) {
            Filter nextFilter = (Filter)this.iterator.next();
            nextFilter.doFilter(request, response, this);
        }

        this.request = request;
        this.response = response;
    }
}
```

它把链条中的所有Filter 放到List 中，然后调用doFilter()方法时，循环迭代List

- Netty 中串行化处理Pipeline 也采用了责任链模式。他底层采用双向链表的数据结构，将链上的各个处理器串联起来

客户端每一个请求的到来，Netty都认为Pipeline 中的所有的处理器都有机会处理它，因此，对于入栈的请求全部从头节点开始往后传播，一直传播到尾节点才会把消息释放掉。来看一个Netty的责任处理器接口ChannelHandler：

```java
public interface ChannelHandler {
    void handlerAdded(ChannelHandlerContext var1) throws Exception;

    void handlerRemoved(ChannelHandlerContext var1) throws Exception;

    /** @deprecated */
    @Deprecated
    void exceptionCaught(ChannelHandlerContext var1, Throwable var2) throws Exception;

    @Inherited
    @Documented
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Sharable {
    }
}
```

Netty 对责任处理接口做了更细粒度的划分，处理器被分成了两种，一种是入栈处理器ChannelboundHandler，另一种是出栈处理器ChannelOutBoundHandler，这两个接口都继承自ChannelHandler。而所有的处理器最终都在添加Pipeline。所以，添加删除责任处理器的接口的行为在Netty 的ChannelPipeline 中进行了规定：

```java
public interface ChannelPipeline extends ChannelInboundInvoker, ChannelOutboundInvoker, Iterable<Entry<String, ChannelHandler>> {
    ChannelPipeline addFirst(String var1, ChannelHandler var2);

    ChannelPipeline addFirst(EventExecutorGroup var1, String var2, ChannelHandler var3);

    ChannelPipeline addLast(String var1, ChannelHandler var2);

    ChannelPipeline addLast(EventExecutorGroup var1, String var2, ChannelHandler var3);

    ChannelPipeline addBefore(String var1, String var2, ChannelHandler var3);

    ChannelPipeline addBefore(EventExecutorGroup var1, String var2, String var3, ChannelHandler var4);

    ChannelPipeline addAfter(String var1, String var2, ChannelHandler var3);

    ChannelPipeline addAfter(EventExecutorGroup var1, String var2, String var3, ChannelHandler var4);

    ChannelPipeline addFirst(ChannelHandler... var1);

    ChannelPipeline addFirst(EventExecutorGroup var1, ChannelHandler... var2);

    ChannelPipeline addLast(ChannelHandler... var1);

    ChannelPipeline addLast(EventExecutorGroup var1, ChannelHandler... var2);

    ChannelPipeline remove(ChannelHandler var1);

    ChannelHandler remove(String var1);

    <T extends ChannelHandler> T remove(Class<T> var1);

    ChannelHandler removeFirst();

    ChannelHandler removeLast();

    ChannelPipeline replace(ChannelHandler var1, String var2, ChannelHandler var3);

    ChannelHandler replace(String var1, String var2, ChannelHandler var3);

    <T extends ChannelHandler> T replace(Class<T> var1, String var2, ChannelHandler var3);

    ChannelHandler first();

    ChannelHandlerContext firstContext();

    ChannelHandler last();

    ChannelHandlerContext lastContext();

    ChannelHandler get(String var1);

    <T extends ChannelHandler> T get(Class<T> var1);

    ChannelHandlerContext context(ChannelHandler var1);

    ChannelHandlerContext context(String var1);

    ChannelHandlerContext context(Class<? extends ChannelHandler> var1);

    Channel channel();

    List<String> names();

    Map<String, ChannelHandler> toMap();

    ChannelPipeline fireChannelRegistered();

    ChannelPipeline fireChannelUnregistered();

    ChannelPipeline fireChannelActive();

    ChannelPipeline fireChannelInactive();

    ChannelPipeline fireExceptionCaught(Throwable var1);

    ChannelPipeline fireUserEventTriggered(Object var1);

    ChannelPipeline fireChannelRead(Object var1);

    ChannelPipeline fireChannelReadComplete();

    ChannelPipeline fireChannelWritabilityChanged();

    ChannelPipeline flush();
}
```

在默认实现类中讲所有的Handler都串成了一个链表，

```java
public class DefaultChannelPipeline implements ChannelPipeline {
    static final InternalLogger logger = InternalLoggerFactory.getInstance(DefaultChannelPipeline.class);

    final AbstractChannelHandlerContext head;
    final AbstractChannelHandlerContext tail;
```

在Pipeline中的任意一个节点，只要我们不手动的往下传播下去，这个事件就会终止传播在当前节点。对于入栈数据，默认会传递到尾节点进行回收。对于出栈数据会把数据写回客户端也意味着事件的终止。

### 责任链模式的优点和缺点

**优点**

- 将请求与处理解耦
- 请求处理者（节点对象）只需关注自己感兴趣的请求进行处理即可，对于不感兴趣的请求，直接转发给下一级节点对象
- 具备链式传递处理请求功能，请求发送者无需知晓链路结构，只需等待请求处理结果
- 链路结构灵活，可以通过改变链路结构动态地新增或者删减责任。
- 易于扩展新的请求处理类节点，符合开闭原则

缺点：

- 责任链太长或者处理时间过长，会影响整体性能

- 如果节点对象存在循环引用时，会造成死循环，造成系统崩溃