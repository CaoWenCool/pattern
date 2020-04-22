## 观察者模式

### 定义

观察者模式定义了对象之间的一对多的依赖，让多个观察者对象同时监听一个主体对象，当主体对象发生变化时，它的所有依赖者（观察者）都会受到通知并更新，属于行为型模式。当我们需要增加一个具体的观察者的时候，不需要修改原有的代码，而是只需要继承之前定义的观察者接口即可。观察者模式有时也叫作发布订阅模式。观察者模式在现实生活中也是应用的非常广泛。比如微信的消息通知，邮件通知，APP动态通知，微博博主更新设置的自动提醒等等。

### Observer接口的使用

- java 中的Obseraable 类 和Observer接口实现了观察者模式

**Observer接口**

```java
//在java.util.Observer 接口中只声明一个方法，它充当抽象观察者，其方法声明如下
public interface Observer {
	
    void update(Observable o, Object arg);
}
```

当观察目标发生变化时，该方法将会被调用，在Observer的子类中将实现update 方法，即具体观察者可以根据需要具有不同的更新行为。当调用观察目标类Observable 的notifyObservers()方法时，将执行观察者类中的update()方法。

**Observable类**

java.util.Observable 类充当观察目标类，在Observable 中定义了一个向量 Vector 类存储观察者对象。

```java
public class Observable {
    private boolean changed = false;
    private Vector<Observer> obs;
```

具体的方法如下：

| 方法名                                          | 方法描述                                                     |
| ----------------------------------------------- | ------------------------------------------------------------ |
| Observable                                      | 构造方法，实例化Vector向量                                   |
| addObserver(Observer o)                         | 用于注册新的观察者对象到向量中                               |
| deleteObserver(Observer o)                      | 用于删除向量中的某一个观察者对象                             |
| notifyObservers() 和notifyObservers(Object arg) | 通知方法，用于在方法内部循环调用向量中每一个观察者的update方法 |
| deleteObservers()                               | 用于清空向量，即删除向量中所有观察者对象                     |
| setChanged()                                    | 该方法被调用后会设置一个boolean类型的内部标记变量changed的值为true，表示观察目标对象的状态发生了变化 |
| clearChanged()                                  | 用于将changed变量的值设为false，表示对象状态不再发生或者已经通知了所有的观察者对象，调用了他们的update()方法。 |
| hasChanged()                                    | 用于测试对象状态是否改变                                     |
| countObservers()                                | 用于返回向量中观察者的数量                                   |

- MVC （Model-View-Controller）架构

MVC是一种架构模式，它也应用了观察者模式，它包含三个角色：模型，视图和控制器。其中模型可对应于观察者模式中的观察目标，视图对应于观察者，控制器可充当两者之间的中介者。当模型层的数据发生改变时，视图层将自动改变其显示内容。

### 例子（微博消息自动提醒）

- 创建微博类

```java
public class WeiBo extends Observable{
    private String name = "微博自动提醒";
    private  static WeiBo weiBo = null;
    private WeiBo(){

    }
    public static WeiBo getInstance(){
        if(null == weiBo){
            return new WeiBo();
        }
        return weiBo;
    }
    public String getName(){
        return name;
    }
    public void publish(WeiBo weiBo,Message message){
        System.out.println(weiBo.name + "通知您，:"+ message.getName()+"给您发来了一个消息，请注意查收");
        setChanged();
        notifyObservers(message);
    }
}
```

- 创建消息类

```java
@Data
public class Message {
    private String name;
    private String content;
}
```

- 创建微博博主类

```java
@Data
public class BoZhu implements Observer{
    private String name;
    public BoZhu(String name){
        this.name = name;
    }

    public void update(Observable o, Object arg) {
        WeiBo weiBo = (WeiBo) o;
        Message message = (Message) arg;
        System.out.println("----------------------------------------");
        System.out.println(name + "您好，你有一个新的消息请注意查收");
        System.out.println(weiBo.getName() + "你好，您收到了一个来自粉丝:"+ message.getName() +"的来信，内容如下：" + message.getContent() );
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        WeiBo weiBo = WeiBo.getInstance();
        BoZhu liuyifei = new BoZhu("刘亦菲");
        BoZhu liyifeng = new BoZhu("李易峰");

        weiBo.addObserver(liuyifei);
        weiBo.addObserver(liyifeng);

        Message message = new Message();
        message.setName("张三");
        message.setContent("我很喜欢你们最近的电影");

        weiBo.publish(weiBo,message);
    }
}
```

- 运行结果图

![1586695708026](C:\Users\ADMINI~1\AppData\Local\Temp\1586695708026.png)

### 例子2（添加键盘的点击事件）

我想大家应该都玩过网络小游戏吧，网络小游戏通过给不同的按键通过对任务的控制，以实现游戏人物的不同的动作。接下来我们就来模拟通过不同的按键模拟不同的动作。

- 事件声明类

```java
@Data
public class Event {
    //事件源
    private Object source;
    //通知对象
    private Object target;
    //回调
    private Method callBack;
    //事件名称
    private String trigger;
    //触发时间
    private long time;
    public Event(Object target,Method callBack){
        this.callBack = callBack;
        this.target = target;
    }
    public Event setSource(Object source){
        this.source  = source;
        return this;
    }
    public Event setTime(long time){
        this.time = time;
        return this;
    }
    public Event setTrigger(String trigger){
        this.trigger = trigger;
        return this;
    }
    @Override
    public String toString (){
        return "Event{" + "\n" +
                "\tsource" + source.getClass() + ",\n" +
                "\ttarget" + target.getClass() + ",\n" +
                "\tcallBack" + callBack.getClass() + ",\n" +
                "\ttrigger" + trigger.getClass() + ",\n" +
                "\ttime" + time + ",\n"
                ;
    }
}
```

- 事件监听器

```java
public class EventListener {
    protected Map<String,Event> events = new HashMap<String,Event>();
    //事件名称和一个目标对象来触发
    public void addListener(String eventType,Object target) throws NoSuchMethodException {
        this.addListener(eventType,target,target.getClass().getMethod("on" +
            toUpperFirstCase(eventType),Event.class));
    }

    public void addListener(String eventType, Object target, Method callBack){
        events.put(eventType,new Event(target,callBack));
    }
    //根据事件触发
    private void trigger(Event event) throws InvocationTargetException, IllegalAccessException {
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        if(event.getCallBack() != null){
            //利用反射调用他的回调函数
            event.getCallBack().invoke(event.getTarget(),event);
        }
    }
    //根据名称触发
    protected void trigger(String trigger) throws InvocationTargetException, IllegalAccessException {
        if(!this.events.containsKey(trigger)){
            return;
        }
        trigger(this.events.get(trigger).setTrigger(trigger));
    }
    //将字符串首字母大写
    private String toUpperFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
```

- 动作类型声明

```java
public interface KeyboardType {
    //E，向上移动
    String ON_E = "up";
    //D,向下移动
    String ON_D = "down";
    //S，向左移动
    String ON_S = "left";
    //F，向右移动
    String ON_F = "right";
    //K，开始发起攻击
    String ON_K = "attack";
}
```

- 模拟动作的实现

```java
public class Keyboard extends EventListener{
    public void up() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向上移动");
        this.trigger(KeyboardType.ON_E);
    }
    public void down() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向下移动");
        this.trigger(KeyboardType.ON_D);
    }
    public void right() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向右移动");
        this.trigger(KeyboardType.ON_F);
    }
    private void attack() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始发起攻击");
        this.trigger(KeyboardType.ON_K);
    }
}
```

- 动作的回调

```java
public class KeyboardEventCallBack {
    public void onUp(Event event){
        System.out.println("------------触发按键 e 的事件 --------------------" + "\n" + event);
    }
    public void onDown(Event event){
        System.out.println("------------触发按键 d 的事件 --------------------"+ "\n" + event);
    }
    public void onLeft(Event event){
        System.out.println("------------触发按键 s 的事件 --------------------"+ "\n" + event);
    }
    public void onRight(Event event){
        System.out.println("------------触发按键 f 的事件 --------------------"+ "\n" + event);
    }
    public void onAttack(Event event){
        System.out.println("------------触发按键 k 的事件 --------------------"+ "\n" + event);
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        KeyboardEventCallBack callBack = new KeyboardEventCallBack();
        //注册事件
        Keyboard keyboard = new Keyboard();
        keyboard.addListener(KeyboardType.ON_D,callBack);
        keyboard.addListener(KeyboardType.ON_E,callBack);
        keyboard.addListener(KeyboardType.ON_S,callBack);
        keyboard.addListener(KeyboardType.ON_F,callBack);
        keyboard.addListener(KeyboardType.ON_K,callBack);

        //向上移动
        keyboard.up();
        //向下移动
        keyboard.down();
    }
}
```

- 运行结果

![1587078668232](https://github.com/CaoWenCool/pattern/blob/master/observer/image/keyboard.jpg)

### 例子3 （基于GuauaAPI 实现观察者模式)

- 导入依赖

```xml
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>20.0</version>
</dependency>
```

- 创建事件监听器

```java
public class GuavaEventListener {
    @Subscribe
    public void subscribe(String str){
        System.out.println("开始执行 subscribe 方法，传入的参数是：" + str);
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        GuavaEventListener guavaEventListener = new GuavaEventListener();
        eventBus.register(guavaEventListener);
        eventBus.post("Hello");
    }
}
```

- 运行结果

![1587080404666](https://github.com/CaoWenCool/pattern/blob/master/observer/image/guava.jpg)



### 在源码中的应用

- Spring 中的ContextLoaderListener 实现了 ServletContextListener接口，ServletContextListener 接口又继承了EventLisener，在JDK 中EventListener 有非常广泛的应用。源码如下：

**ContextLoaderListener**

```java
public class ContextLoaderListener extends ContextLoader implements ServletContextListener {
    public ContextLoaderListener() {
    }

    public ContextLoaderListener(WebApplicationContext context) {
        super(context);
    }

    public void contextInitialized(ServletContextEvent event) {
        this.initWebApplicationContext(event.getServletContext());
    }

    public void contextDestroyed(ServletContextEvent event) {
        this.closeWebApplicationContext(event.getServletContext());
        ContextCleanupListener.cleanupAttributes(event.getServletContext());
    }
}
```

**ServerContextListener**

```java
public interface ServletContextListener extends EventListener {
    default void contextInitialized(ServletContextEvent sce) {
    }

    default void contextDestroyed(ServletContextEvent sce) {
    }
}
```

**EventListener**

```java
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventListener {
    @AliasFor("classes")
    Class<?>[] value() default {};

    @AliasFor("value")
    Class<?>[] classes() default {};

    String condition() default "";
}
```

### 优缺点

**优点**

1、观察者和被观察者之间建立了一个抽象的耦合关系

2、观察者模式支持广播通信

**缺点**

1、观察者之间有过多的细节依赖，提高时间消耗及程序的复杂度

2、需要避免循环调用

