## 委派模式

### 定义

委派模式又叫委托模式，是一种面向对象的设计模式，允许对象组合实现与继承相同的代码重用。它的基本作用就是负责任务的调用和分配任务，是一种特殊的静态代理，可以理解为全权代理，但是代理模式注重过程，而委派模式注重结果。委派模式属于行为型模式，不属于GOF 23中设计模式中。

### 应用场景

现实生活中也是常有委派模式的发生，例如老板给项目经理下达任务，项目经理会根据每个员工的工种以及职责派发工作任务，待员工把工作任务完成之后，再由项目经理向老板汇报项目进度。

或者两会期间，我们的中央，会针对经济发展确定一个发展布标，发布一下任务。而各个省市在理解中央的文件之后，就会根据任务的特点，将任务分发给各个单位进行完成。这也是一种典型的委派模式。

或者，战争期间，军队领导发布命令要占领A高地，会委派一个团去占领，但是委派的这个团在具体的实施当中，这个团的可能会让各个兵种完成自己的工作任务，例如先让侦察兵勘探敌情，让空军空中火力支援，让炮兵远距离打击，让步兵去近身肉搏等等。

### 例子1

我们接下来用代码来模拟下委派模式。

- 创建兵种的接口

```java
public interface ISoldiers {
    void doing(String task);
}
```

- 创建侦察兵类

```java
public class ScoutSoldiers implements ISoldiers{
    protected String weapon = "小米加步枪";
    protected String name = "侦察兵";
    protected String work = "勘探敌情";

    public void doing(String task) {
        System.out.println("我是：" + name + ",我的武器是：" +  weapon + ",我的职责是：" + work);
    }
}
```

- 创建特种兵类

```java
public class SpecialSoldiers implements ISoldiers{
    protected String weapon = "狙击步枪";
    protected String name = "特种兵";
    protected String work = "枪毙敌方首脑";

    public void doing(String task) {
        System.out.println("我是：" + name + ",我的武器是：" +  weapon + ",我的职责是：" + work);
    }
}
```

- 创建团长类，由于团长也是士兵，所以也需要进程ISoldiers类，

```java
public class LeaderSoldiers implements ISoldiers{
    private Map<String,ISoldiers> emploeeMap = new HashMap<String,ISoldiers>();
    public LeaderSoldiers(){
        emploeeMap.put("查看敌军守备情况",new ScoutSoldiers());
        emploeeMap.put("刺杀地方首脑",new SpecialSoldiers());
    }
    public void doing(String task) {
        if (!emploeeMap.containsKey(task)){
            System.out.println("报告！这个任务：" + task + "超出我的能力范围，请求支援！");
            return;
        }
        emploeeMap.get(task).doing(task);
    }
}
```

- 创建军队BOSS类，主要是职责是创建命令

```java
public class BossSoldiers {
    public void command(String task,LeaderSoldiers leaderSoldiers){
        leaderSoldiers.doing(task);
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        new BossSoldiers().command("查看敌军守备情况",new LeaderSoldiers());
        new BossSoldiers().command("刺杀地方首脑",new LeaderSoldiers());
        new BossSoldiers().command("进行火力压制",new LeaderSoldiers());
    }
}
```

- 运行结果

![1588029981295](C:\Users\ADMINI~1\AppData\Local\Temp\1588029981295.png)

- 类图

![1588030097511](C:\Users\ADMINI~1\AppData\Local\Temp\1588030097511.png)

### 在源码中的实现

JDK中的双亲委派模型，就是典型的委派模式。

双亲委派模型：一个类加载器在加载类的时候，先把这个请求委派给自己的父类加载器去执行，如果父类加载器还存在父类加载器，就继续向上委派，直到顶层的启动类加载器。如果父类加载器能够完成加载，就成功返回，如果父类加载器无法完成加载，那么子加载器才会尝试自己去加载。从定义中可以看到双亲加载模型一个类加载器加载时，首先不是自己加载，而是委派给父加载器。

我们来看下loadClass()方法的源码，此方法在ClassLoader中。

```java
protected Class<?> loadClass(String name, boolean resolve)
    throws ClassNotFoundException
{
    synchronized (getClassLoadingLock(name)) {
        // First, check if the class has already been loaded
        Class<?> c = findLoadedClass(name);
        if (c == null) {
            long t0 = System.nanoTime();
            try {
                if (parent != null) {
                    c = parent.loadClass(name, false);
                } else {
                    c = findBootstrapClassOrNull(name);
                }
            } catch (ClassNotFoundException e) {
                // ClassNotFoundException thrown if class not found
                // from the non-null parent class loader
            }

            if (c == null) {
                // If still not found, then invoke findClass in order
                // to find the class.
                long t1 = System.nanoTime();
                c = findClass(name);

                // this is the defining class loader; record the stats
                sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                sun.misc.PerfCounter.getFindClasses().increment();
            }
        }
        if (resolve) {
            resolveClass(c);
        }
        return c;
    }
}
```

- 在Method类里我们常用代理执行方法invoke()也存在类似的机制。

```java
@CallerSensitive
public Object invoke(Object var1, Object... var2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
    if (!this.override && !Reflection.quickCheckMemberAccess(this.clazz, this.modifiers)) {
        Class var3 = Reflection.getCallerClass();
        this.checkAccess(var3, this.clazz, var1, this.modifiers);
    }

    MethodAccessor var4 = this.methodAccessor;
    if (var4 == null) {
        var4 = this.acquireMethodAccessor();
    }

    return var4.invoke(var1, var2);
}
```

- 委派模式在Spring中的应用，在SpringIOC中，在调用doRegisterBeanDefinitions()方法时即BeanDefinition进行注册的过程中，会设置BeanDefinitionParserDelegate类型的Delegate对象传给this.delegate ，并将这个对象作为一个参数传给：parseBeanDefinitions(root,this.delegate)中，然后主要解析的工作就通过delegate座位主要角色来完成的。源码如下：

```java
protected void parseBeanDefinitions(Element root, BeanDefinitionParserDelegate delegate) {
    if (delegate.isDefaultNamespace(root)) {
        NodeList nl = root.getChildNodes();

        for(int i = 0; i < nl.getLength(); ++i) {
            Node node = nl.item(i);
            if (node instanceof Element) {
                Element ele = (Element)node;
                if (delegate.isDefaultNamespace(ele)) {
                    this.parseDefaultElement(ele, delegate);
                } else {
                    delegate.parseCustomElement(ele);
                }
            }
        }
    } else {
        delegate.parseCustomElement(root);
    }

}
```

其中最终会进入到parseDefaultElement（ele，delegate）中，然后针对不同的节点类型，针对bean的节点进行真正的注册操作，而在这个过程中，delegate回对element进行parseBeanDefinitionElement，得到了一个BeanDefinitionHolder类型的对象，之后通过这个对象完成真正的注册到Factory的操作。接下来我们将模拟SpirngMVC中的DispatcharServlet 实现委派模式

### 模拟SpringMVC的DispatcherServlet 实现委派模式。

- MemberController

```java
public class MemberController {
    public void getMemberById(String id){
    }
}
```

- OrderController

```java
public class OrderController {
    public void getOrderById(String mid){

    }
}
```

- SystemController

```java
public class SystemController {
    public void logout(){

    }
}
```

- Dispatcher

```java
public class DispatcherServlet extends HttpServlet {
    private Map<String,Method> handlerMapping = new HashMap<String,Method>();
    @Override
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doDispatch(req,res);
    }
    @Override
    public void init() throws ServletException {
        try {
            handlerMapping.put("/web/getMemberById.json",MemberController.class.getMethod("getMemberById",new Class[]{String.class}));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    private void doDispatch(HttpServletRequest req, HttpServletResponse res){
        String url = req.getRequestURI();
        Method method = handlerMapping.get(url);
    }
}
```

- web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/j2ee" xmlns:javaee="http://java.sun.com/xml/ns/javaee"
         xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         xsi:schemaLocation="http://java.sun.com/xml/ns/j2ee http://java.sun.com/xml/ns/j2ee/web-app_2_4.xsd"
         version="2.4">
    <display-name>COPY SPRING-MVC</display-name>

    <servlet>
        <servlet-name>delegate</servlet-name>
        <servlet-class>com.demo.delegate.copySpringMVC.DispatcherServlet</servlet-class>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>delegate</servlet-name>
        <url-pattern>/*</url-pattern>
    </servlet-mapping>
</web-app>
```

在Spring中运用委派模式的不仅于此，还有很多。在Spring源码中，主要以Delegate结尾的都是实现了委派模式。例如：BeanDefinitionParserDelegate 根据不同类型委派不同的逻辑解析BeanDefinition

### 优缺点比较

**优点**

通过任务委派能够将一个大型的任务细化，然后通过统一管理这些子任务的完成情况实现任务的跟进，能够加快任务的执行的效率

**缺点**

任务委派方式需要根据任务的复杂程度进行不同的改变，在任务比较复杂的情况下可能需要进行多重委派，容易造成紊乱。

