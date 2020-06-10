## 状态模式

### 定义

状态模式也成为了状态机模式，是允许对象在内部状态发生改变时改变他的行为，对象看起来好像修改了它的类，属于行为型模式。

在状态模式（State Pattern）中，类的行为是基于它的状态改变的。这种类型的设计模式属于行为型模式。

在状态模式中，我们创建表示各种状态的对象和一个行为随着状态对象改变而改变的 context 对象。

### 应用场景

生活中，状态模式也是十分常见的，例如网购过程中订单状态的变化，坐电梯时电梯的变化等等。

在软件开发过程中，对于某一个操作，可能存在不同的情况。通常处理情况问题最直接的方式就是使用 if-else 或者 switch...case 条件语句进行枚举。但是这种做法对于复杂状态的判断天然存在弊端：条件判断语句过于臃肿，可读性差，且不具备扩展性，维护难度也大。而如果转换思维，将这些不同状态独立起来用各个不同的类进行表示，系统处于哪种情况，直接使用相应的状态类对象进行处理，消除了 if..else, switch ...case 等冗余语句，代码更具有层次且具备良好的扩展力。

状态模式主要解决的就是当控制一个对       象状态的条件表达式过于复杂时的情况。通过把状态的判断逻辑转移到表示不同的一系列类中，可以把复杂的 判断逻辑简化 。对象的行为依赖于它的状态（属性），并且会根据他的状态 改变而改变他的相关行为。状态模式适用于以下场景：

- 行为随着状态改变而改变的场景
- 一个操作中含有庞大的多分支结构，并且这些分支取决于对象的状态。

### 角色分工

状态模式主要分为三种角色

1、环境类角色：它定义了客户程序需要的接口并维护一个具体状态角色的实例，将与状态相关的操作委托给当前的Concrete State对象来处理。

2、抽象状态角色：定义一个接口以封装使用上下文环境的的一个特定状态相关的行为。

3、具体状态角色：实现抽象状态定义的接口。

![1591749811898](C:\Users\ADMINI~1\AppData\Local\Temp\1591749811898.png)

### 例子1

我想大家应该都登陆过博客系统吧 。当我们看到一篇优秀的博客的时候往往会忍不住进行评论收藏，如果你此时处于登陆的情况下，那么就直接进行评论收藏，但是如果你此时未登录，那么你就需要先登陆，然后才能评论收藏。这里涉及到的状态 ：登陆和未登录，行为：评论和收藏。

- 首先创建抽象状态角色 UserState 类

```java
public abstract class UserState {
    protected AppContext appContext;
    public AppContext getAppContext() {
        return appContext;
    }
    public void setAppContext(AppContext appContext) {
        this.appContext = appContext;
    }
    public abstract void favorite();
    public abstract void comment(String comment);
}

```

- 登陆状态类 LoginState类

```java
public class LoginState extends UserState{
    public void favorite() {
        System.out.println("收藏成功！！！");
    }

    public void comment(String comment) {
        System.out.println("评论：" + comment);
    }
}
```

- 创建未登录状态

```java
public class UnLoginState extends UserState{
    public void favorite() {
        System.out.println("跳转到登陆界面");
        this.appContext.setState(this.appContext.STATE_LOGIN);
        this.appContext.getCurrentState().favorite();

    }
    public void comment(String comment) {
        System.out.println("跳转到登陆界面");
        this.appContext.setState(this.appContext.STATE_LOGIN);
        this.appContext.getCurrentState().comment(comment);
    }
}
```

- 创建上下文角色

```java
public class AppContext {
    public static final UserState STATE_LOGIN = new LoginState();
    public static final UserState STATE_UNLOGIN = new UnLoginState();
    private UserState currentState = STATE_UNLOGIN;
    {
        STATE_LOGIN.setAppContext(this);
        STATE_UNLOGIN.setAppContext(this);
    }
    public void setState(UserState state){
        this.currentState = state;
        this.currentState.setAppContext(this);
    }
    public UserState getCurrentState() {
          return this.currentState;
    }
    public void favorite(){
        this.currentState.favorite();
    }
    public void comment(String comment){
        this.currentState.comment(comment);
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        AppContext appContext = new AppContext();
        appContext.favorite();
        appContext.comment("这咋写得这么好呢？");
    }
}
```

- 运行结果

![1591145120976](C:\Users\ADMINI~1\AppData\Local\Temp\1591145120976.png)

### 利用状态机实现订单状态流转控制

状态机是状态模式的一种应用，相当于上下文角色的一个升级版本。在工作流或者游戏等各种系统中大量使用，如各种工作流引擎，它几乎是状态机的子集和实现，封装状态的变化规则。

Spring中也提供了我们一个很好的解决方案。Spring 中 的组件名称就叫StateMachine(状态机)。状态机帮助开发者简化状态控制的开发过程，让状态机结构更加层次化。下面我们将用Spring状态机模拟一个 订单状态流转的过程。

- 添加依赖

```xml
<dependency>
    <groupId>org.springframework.statemachine</groupId>
    <artifactId>spring-statemachine-core</artifactId>
    <version>2.0.1.RELEASE</version>
</dependency>
```

- 创建订单类实体 Order 类

```java
public class Order {
    private int id;
    private OrderStatus states;
    public  void setStates(OrderStatus states){
        this.states = states;
    }
    public OrderStatus getStates() {
        return states;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", states=" + states +
                '}';
    }
}
```

- 创建订单状态枚举类和状态转换枚举

```java
public enum OrderStatusChangeEvent {
    //支付
    PAYED,
    //发货
    DELIVERY,
    //确认收货
    RECEIVED;
}
```

```java
public enum OrderStatus {
    // 待支付，待发货，待收货，订单结束
    WAIT_PAYMENT, WAIT_DELIVER, WAIT_RECEIVE, FINISH;
}
```

- 添加状态流转配置

```java
@Configuration
@EnableStateMachine(name = "orderStateMachine")
public class OrderStateMachineConfig extends StateMachineConfigurerAdapter<OrderStatus,OrderStatusChangeEvent> {

    /**
     * 配置状态
     * @param states
     * @throws Exception
     */
    public void configure(StateMachineStateConfigurer<OrderStatus, OrderStatusChangeEvent> states) throws Exception {
        states
                .withStates()
                .initial(OrderStatus.WAIT_PAYMENT)
                .states(EnumSet.allOf(OrderStatus.class));
    }

    /**
     * 配置状态转换事件关系
     * @param transitions
     * @throws Exception
     */
    public void configure(StateMachineTransitionConfigurer<OrderStatus, OrderStatusChangeEvent> transitions) throws Exception {
        transitions
                .withExternal().source(OrderStatus.WAIT_PAYMENT).target(OrderStatus.WAIT_DELIVER).event(OrderStatusChangeEvent.PAYED)
                .and()
                .withExternal().source(OrderStatus.WAIT_DELIVER).target(OrderStatus.WAIT_RECEIVE).event(OrderStatusChangeEvent.DELIVERY)
                .and()
                .withExternal().source(OrderStatus.WAIT_RECEIVE).target(OrderStatus.FINISH).event(OrderStatusChangeEvent.RECEIVED);
    }

    /**
     * 持久化配置
     * 实际使用中，可以配合redis等，进行持久化操作
     * @return
     */
    @Bean
    public DefaultStateMachinePersister persister(){
        return new DefaultStateMachinePersister<>(new StateMachinePersist<Object, Object, Order>() {
            @Override
            public void write(StateMachineContext<Object, Object> context, Order order) throws Exception {
                //此处并没有进行持久化操作
            }

            @Override
            public StateMachineContext<Object, Object> read(Order order) throws Exception {
                //此处直接获取order中的状态，其实并没有进行持久化读取操作
                return new DefaultStateMachineContext(order.getStates(), null, null, null);
            }
        });
    }

}
```

- 添加订单状态监听器

```java
@Component("orderStateListener")
@WithStateMachine(name = "orderStateMachine")
public class OrderStateListenerImpl {
    @OnTransition(source = "WAIT_PAYMENT", target = "WAIT_DELIVER")
    public boolean payTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStates(OrderStatus.WAIT_DELIVER);
        System.out.println("支付，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_DELIVER", target = "WAIT_RECEIVE")
    public boolean deliverTransition(Message<OrderStatusChangeEvent> message) {
        Order order = (Order) message.getHeaders().get("order");
        order.setStates(OrderStatus.WAIT_RECEIVE);
        System.out.println("发货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }

    @OnTransition(source = "WAIT_RECEIVE", target = "FINISH")
    public boolean receiveTransition(Message<OrderStatusChangeEvent> message){
        Order order = (Order) message.getHeaders().get("order");
        order.setStates(OrderStatus.FINISH);
        System.out.println("收货，状态机反馈信息：" + message.getHeaders().toString());
        return true;
    }
}
```

- 创建IOrderService 接口

```java
public interface IOrderService {
    // 创建订单
    Order create();
//    发起支付
    Order pay(int id);
//    订单发货
    Order deliver(int id);
//    订单收货
    Order receive(int id);
//    获取所有订单信息
    Map<Integer,Order> getOrders();
}
```

- 创建 OrderServiceImpl

```java
@Service("orderService")
public class OrderServiceImpl implements IOrderService{
    @Autowired
    private StateMachine<OrderStatus, OrderStatusChangeEvent> orderStateMachine;

    @Autowired
    private StateMachinePersister<OrderStatus, OrderStatusChangeEvent, Order> persister;

    private int id = 1;
    private Map<Integer, Order> orders = new HashMap<>();

    public Order create() {
        Order order = new Order();
        order.setStates(OrderStatus.WAIT_PAYMENT);
        order.setId(id++);
        orders.put(order.getId(), order);
        return order;
    }

    public Order pay(int id) {
        Order order = orders.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试支付，订单号：" + id);
        Message message = MessageBuilder.withPayload(OrderStatusChangeEvent.PAYED).setHeader("order", order).build();
        if (!sendEvent(message, order)) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 支付失败, 状态异常，订单号：" + id);
        }
        return orders.get(id);
    }

    public Order deliver(int id) {
        Order order = orders.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试发货，订单号：" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.DELIVERY).setHeader("order", order).build(), orders.get(id))) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 发货失败，状态异常，订单号：" + id);
        }
        return orders.get(id);
    }

    public Order receive(int id) {
        Order order = orders.get(id);
        System.out.println("线程名称：" + Thread.currentThread().getName() + " 尝试收货，订单号：" + id);
        if (!sendEvent(MessageBuilder.withPayload(OrderStatusChangeEvent.RECEIVED).setHeader("order", order).build(), orders.get(id))) {
            System.out.println("线程名称：" + Thread.currentThread().getName() + " 收货失败，状态异常，订单号：" + id);
        }
        return orders.get(id);
    }


    public Map<Integer, Order> getOrders() {
        return orders;
    }


    /**
     * 发送订单状态转换事件
     *
     * @param message
     * @param order
     * @return
     */
    private synchronized boolean sendEvent(Message<OrderStatusChangeEvent> message, Order order) {
        boolean result = false;
        try {
            orderStateMachine.start();
            //尝试恢复状态机状态
            persister.restore(orderStateMachine, order);
            //添加延迟用于线程安全测试
            Thread.sleep(1000);
            result = orderStateMachine.sendEvent(message);
            //持久化状态机状态
            persister.persist(orderStateMachine, order);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            orderStateMachine.stop();
        }
        return result;
    }
}
```

- 添加测试类

```java
@SpringBootApplication
public class Test {
    public static void main(String[] args) {

        Thread.currentThread().setName("主线程");

        ConfigurableApplicationContext context = SpringApplication.run(Test.class,args);

        IOrderService orderService = (IOrderService)context.getBean("orderService");

        orderService.create();
        orderService.create();

        orderService.pay(1);

        new Thread("客户线程"){
            @Override
            public void run() {
                orderService.deliver(1);
                orderService.receive(1);
            }
        }.start();

        orderService.pay(2);
        orderService.deliver(2);
        orderService.receive(2);

        System.out.println("全部订单状态：" + orderService.getOrders());
    }

}
```

### 状态模式相关的设计模式

1、状态 模式与责任链模式

状态模式和责任链模式都能消除 if 分支过多的 问题。但是在某些情况下，状态模式中的状态可以理解为责任，那么这种情况下，两种模式都可以使用。

从定义上看，状态模式强调的是一个对象内在状态的改变，而责任链模式强调的是外部节点对象间的改变。

从其代码上看，他们间最大的区别就是状态模式各个状态对象知道自己下一个要进入的状态对象，而责任链模式并不清楚其下一个节点处理对象，因为链式组装由客户端负责。

2、状态模式与策略模式

状态模式和策略模式的UML类图架构几乎完全一样，但是他们的应用场景不一样的。策略模式算法行为择其一都能满足，彼此之间是独立的，用户可自行更换策略算法，而状态模式各个状态间是存在相互关系的，彼此之间在一定条件下存在自动切换状态效果，且用户无法指定状态，只能设置初始状态。

### 状态模式的优缺点

**优点**

1、结构清晰：将状态独立为类，消除了冗余的 if..else 或者 switch...case 语句，使得代码更加简洁，提高系统可维护性。

2、将状态转换显示化：通常的对象内部都是使用数值类型来定义状态，状态的切换是通过赋值进行表现，不够直观。而使用状态类，在切换状态时，是以不同的类进行表示，转换的目的更加明确。

3、状态类职责明确且具备扩展性

**缺点**

1、类膨胀：如果一个事物具备很多状态，则会造成状态类太多

2、状态模式的结构与实现都较为复杂，如果使用不当讲导致程序结构和代码的混乱。

3、状态模式对开闭原则的支持并不好，否则无法切换到新增状态，而且修改某个状态类的行为也需要修改对应的源代码.

