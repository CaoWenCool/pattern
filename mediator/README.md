# 中介者模式

## 定义

中介者模式（Mediator Pattern）是用来降低多个对象和类之间的通信复杂性。这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护。中介者模式属于行为型模式。

其意图：用一个中介对象来封装一系列的对象交互，中介者使各对象不需要显式地相互引用，从而使其耦合松散，而且可以独立地改变它们之间的交互。

其主要解决的问题：对象与对象之间存在大量的关联关系，这样势必会导致系统的结构变得很复杂，同时若一个对象发生改变，我们也需要跟踪与之相关联的对象，同时做出相应的处理。

使用时机：多个类相互耦合，形成了网状结构。

解决办法：将上述网状结构分离为星型结构。

## 应用场景

在现实生活中，我们一般接触最多的就是房产中介了，设想一下，如果没有房产中介，那么房主在第一时间发布了房源信息之后，可能就会遇到大批的租房者与其进行沟通，这样无疑会加重房主的工作量。房产中介通过整合房源信息与租房者信息，降低了房主与租房者之家的依赖关系，降低了他们之间的耦合度，从而使关系更加清晰。

![1592178339652](C:\Users\ADMINI~1\AppData\Local\Temp\1592178339652.png)

从上图可以发现，每个对象之间过度耦合，这样的既不利于信息的复用也不利于扩展。如果引入了中介者模式，那么对象之间的关系将变成星型结构，采用中介者模式之后，会形成如下的结构：

![1592178584074](C:\Users\ADMINI~1\AppData\Local\Temp\1592178584074.png)

从上图可以看出，使用中介者模式之后，任何一个类的变化，只会影响中介者和类本身，不想之前的设计，任何一个类的变化都会引起其关联所有类的变化。这样的设计大大减少了系统的耦合度。

其实我们日常生活中每天在刷的朋友圈，就是一个中介者。还有我们所见的交易平台也是中介者模式的体现。

中介者模式是用来降低多个对象和类之间的通信复杂性。这种模式通过提供一个中介类，将系统各个层次对象间的多对多关系变成一对多关系，中介者对象可以将复杂的网状结构变成以调停者为中心的星型结构，达到降低系统的复杂性，提高可扩展性的作用。

若系统各个层次对象之间存在大量的关联关系，即层次对象呈复杂的网状结构，如果直接让他们紧耦合通信，会造成系统结构变得异常复杂，且其中某个层次对象发生改变，则与其紧耦合的相应层次对象也许进行修改，系统很难进行维护。而通过为系统增加一个中介者层次对象，让其他层次需对外通信的行为统统交由中介者进行转发，系统呈现以中介者为中心进行通讯的星型结构，系统的复杂性大大降低。

简单的说就是多个类相互耦合，行程了网状结构，则考虑使用中介者模式进行优化。总结中介者模式适用于以下场景：

1、系统中对象之间存在复杂的引用关系，产生的相互依赖关系结构混乱且难以理解。

2、交互的公共行为，如果需要改变行为则可以增加新的中介者类。

## 相关角色

中介者主要包含4个角色 :

1、抽象中介者：定义统一的接口，用于各同事角色之间的通信。

2、具体中介者：从具体的同事对象接收消息，向具体同事对象发出命令，协调各同事之间的协作

3、抽象同事类：每一个同事对象均需要依赖中介者角色，与其他同事间通信时，交由中介者进行转发协作。

4、具体同事类：负责实现自发行为，转发依赖对象方法交由中介者进行协调。

## 例1：使用中介者模式设计群聊场景

假设我们要构建一个聊天室系统，用户可以向聊天室发送消息，聊天室会所有的用户显示消息。实际上就是用户发信息与聊天室显示的通信过程，不过用户无法直接将信息发送给聊天室，而是需要将信息先发送到服务器上，然后服务器再将该信息发给聊天室进行显示。

![1592181211485](C:\Users\ADMINI~1\AppData\Local\Temp\1592181211485.png)

- 创建User类

```java
public class User {
    private String name;
    private ChatRoom chatRoom;

    public User(String name, ChatRoom chatRoom) {
        this.name = name;
        this.chatRoom = chatRoom;
    }

    public void sendMessage(String msg){
        this.chatRoom.showMsg(this,msg);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
```

- 创建ChatRoom

```java
public class ChatRoom {
    public void showMsg(User user,String msg){
        System.out.println("[" + user.getName() + "]" + msg);
    }
}
```

- 客户端测试类

```java
public class MediatorPatternDemo {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        User zs = new User("张三",chatRoom);
        User ls = new User("李四",chatRoom);
        zs.sendMessage("My Name is 张三");
        ls.sendMessage("Hello 张三");
    }
}

```

- 运行结果

![1592179979140](C:\Users\ADMINI~1\AppData\Local\Temp\1592179979140.png)

## 中介者模式在源码中的体现

- 首先我们看一下JDK中的Timer 类

打开Timer 的结构我们发现Timer 类中有很多的schedule()重载方法。如下图：

![1592180389412](C:\Users\ADMINI~1\AppData\Local\Temp\1592180389412.png)

我们发现所有方法最终都是调用了私有的sched()方法，其源码如下：

```java
private void sched(TimerTask task, long time, long period) {
    if (time < 0)
        throw new IllegalArgumentException("Illegal execution time.");

    // Constrain value of period sufficiently to prevent numeric
    // overflow while still being effectively infinitely large.
    if (Math.abs(period) > (Long.MAX_VALUE >> 1))
        period >>= 1;

    synchronized(queue) {
        if (!thread.newTasksMayBeScheduled)
            throw new IllegalStateException("Timer already cancelled.");

        synchronized(task.lock) {
            if (task.state != TimerTask.VIRGIN)
                throw new IllegalStateException(
                    "Task already scheduled or cancelled");
            task.nextExecutionTime = time;
            task.period = period;
            task.state = TimerTask.SCHEDULED;
        }

        queue.add(task);
        if (queue.getMin() == task)
            queue.notify();
    }
}
```

我们发现，不管是什么样的任务都被加入到一个队列中顺序执行，我们把这个队列中的所有对象称之为同事，同事之间通信时通过Timer 来协调完成的，Timer 就承担了 中介者的角色。

## 中介者模式的优缺点

**优点**

1、减少类间依赖，将多对多依赖转化成了一对多，降低了类间耦合，

2、类间各司其职，符合迪米特法则。

**缺点**

中介者模式将原本多个对象直接的相互依赖变成了中介者和多个同事类的依赖关系。当同事类越多时，中介者就会越臃肿，变得复杂且难以维护。

