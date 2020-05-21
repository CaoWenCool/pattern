## 门面模式

### 定义

门面模式又叫外观模式，提供了一个统一的接口，用来访问子系统中的一群接口。其主要特征是定义了一个高层接口，让子系统更加容易使用，属于结构型模式

其要求一个子系统的外部必须与其内部的通信必须通过一个统一的对象进行。

其实，在日常的工作中，我们都会大量使用门面模式，但凡只要高层模块需要调度多个子系统，我们都会不自觉的创建一个新的类来进行封装这些子系统，提供更加精简的接口，让高层模块更加容易间接调用这些子系统的功能。尤其是现阶段各种第三方的SDK，各种开源类库，很大概率都会使用门面模式。

一般门面模式主要包含两种角色：

外观角色：也成门面角色，系统对外的统一接口

子系统角色：可以同时有一个或者多个SubSystem，每个SubSystem都不是一个单独的类，而是一个类的集合。SubSystem并不知道Facade的存在，对于SubSystem而言，Facade只是另一个客户端而已（即Facade 对SubSystem透明）。

### 应用场景

1、子系统越来越复杂，增加门面模式提供简单的接口

2、构建多层系统结构，利用门面对象作为每层的入口，简化层间调用

### 应用举例

- 子系统A

```java
public class SubA {
    public void doA(){
        System.out.println("这是A接口");
    }
}
```

- 子系统B

```java
public class SubB {
    public void doB(){
        System.out.println("这是B 接口");
    }
}
```

- 子系统C

```java
public class SubC {
    public void doC(){
        System.out.println("这是C接口");
    }
}
```

- 门面系统Facade

```java
public class Facade {
    private SubA a = new SubA();
    private SubB b = new SubB();
    private SubC c = new SubC();
    //对外的接口
    public  void doA(){
        a.doA();
    }
    public  void doB(){
        b.doB();
    }
    public  void doC(){
        c.doC();
    }
}
```

- 测试

```java
public class Test {
    public static void main(String[] args) {
        Facade facade = new Facade();
        facade.doA();
        facade.doB();
        facade.doC();
    }
}
```

- 运行结果

![1588726877144](C:\Users\ADMINI~1\AppData\Local\Temp\1588726877144.png)

- 类图

![1588726941944](C:\Users\ADMINI~1\AppData\Local\Temp\1588726941944.png)

### 门面模式的业务举例

我想大家应该都做过或者研究过商城系统吧。在商城系统中，我们的每个子系统之间的对接其实就是应用的门面模式。

这些子系统可能涉及到支付系统、积分系统、物流系统等等。如果所有的接口调用全部由前端发送网络请求去调用现有接口的话，一则会增加前端开发人员的难度，二则会增加一些网络请求影响页面性能。这个时候就可以发挥门面模式的优势了，将所有线程的网络接口全部整合到一个类中，由后端提供统一的接口给前端调用，这样前端开发人员就不需要关心各个接口的业务关系，只需要把精力集中在页面交互上。

接下来我们借来描述这个过程：

- 商品类

```java
@Data
public class Goods {
    private String name;
    private double price;
    public Goods(String name,double price){
        this.name = name;
        this.price = price;
    }
}
```

- 支付系统

```java
public class PayService {
    public boolean pay(Goods goods){
        //支付成功，增加用户积分
        System.out.println("支付成功，商品名称：" + goods.getName() + ",商品价格：" + goods.getPrice() );
        return true;
    }
}
```

- 钱包系统

```java
public class WalletService {
    private double money = 200.0;
    //检查余额
    public boolean checkMoney(Goods goods){
        if(money >= goods.getPrice()){
            System.out.println("余额充足，可以进行支付");
            return true;
        }
        System.out.println("余额不足");
        return false;
    }
    public boolean updateMoney(Goods goods){
        System.out.println("修改余额，扣减:" + goods.getPrice() +",剩余：" + (money-goods.getPrice()));
        return true;
    }
}
```

- 物流系统

```java
public class ShippingService {
    public boolean delivery(Goods goods){
        System.out.println("您的：" + goods.getName() + "正在快马加鞭的赶来");
        return true;
    }
}
```

- 商城系统门面

```java
public class ShopFacade {
    private PayService payService = new PayService();
    private WalletService walletService = new WalletService();
    private ShippingService shippingService = new ShippingService();
    public boolean exchange(Goods goods){
        //检查余额
        if(!walletService.checkMoney(goods)){
            return false;
        }
        //进行支付
        if(!payService.pay(goods)){
            return false;
        }
        //扣减余额
        if(!walletService.updateMoney(goods)){
            return false;
        }
        //物流运送
        if(!shippingService.delivery(goods)){
            return false;
        }
        return true;
    }
}
```

- 测试

```java
public class Test {
    public static void main(String[] args) {
        Goods goods = new Goods("大力丸",100.0);
        ShopFacade shopFacade = new ShopFacade();
        shopFacade.exchange(goods);
    }
}
```

- 运行结果

![1588728581026](C:\Users\ADMINI~1\AppData\Local\Temp\1588728581026.png)

- 类图

![1588728624144](C:\Users\ADMINI~1\AppData\Local\Temp\1588728624144.png)

### 门面模式在源码中的应用

- Spring JDBC 模块下的JdbcUtils类，它封装了和JDBC 相关的所有操作

```java
public abstract class JdbcUtils {
    public static final int TYPE_UNKNOWN = -2147483648;
    private static final Log logger = LogFactory.getLog(JdbcUtils.class);

    public JdbcUtils() {
    }

    public static void closeConnection(@Nullable Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException var2) {
                logger.debug("Could not close JDBC Connection", var2);
            } catch (Throwable var3) {
                logger.debug("Unexpected exception on closing JDBC Connection", var3);
            }
        }

    }

    public static void closeStatement(@Nullable Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException var2) {
                logger.trace("Could not close JDBC Statement", var2);
            } catch (Throwable var3) {
                logger.trace("Unexpected exception on closing JDBC Statement", var3);
            }
        }

    }

    public static void closeResultSet(@Nullable ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException var2) {
                logger.trace("Could not close JDBC ResultSet", var2);
            } catch (Throwable var3) {
                logger.trace("Unexpected exception on closing JDBC ResultSet", var3);
            }
        }

    }
    ..........
```

![1588812196759](C:\Users\ADMINI~1\AppData\Local\Temp\1588812196759.png)

- MyBatis中的Configuration类，它其中有很多是以new开头的方法

```java
public MetaObject newMetaObject(Object object) {
    return MetaObject.forObject(object, this.objectFactory, this.objectWrapperFactory, this.reflectorFactory);
}

public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object parameterObject, BoundSql boundSql) {
    ParameterHandler parameterHandler = mappedStatement.getLang().createParameterHandler(mappedStatement, parameterObject, boundSql);
    parameterHandler = (ParameterHandler)this.interceptorChain.pluginAll(parameterHandler);
    return parameterHandler;
}

public ResultSetHandler newResultSetHandler(Executor executor, MappedStatement mappedStatement, RowBounds rowBounds, ParameterHandler parameterHandler, ResultHandler resultHandler, BoundSql boundSql) {
    ResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, mappedStatement, parameterHandler, resultHandler, boundSql, rowBounds);
    ResultSetHandler resultSetHandler = (ResultSetHandler)this.interceptorChain.pluginAll(resultSetHandler);
    return resultSetHandler;
}

public StatementHandler newStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameterObject, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
    StatementHandler statementHandler = new RoutingStatementHandler(executor, mappedStatement, parameterObject, rowBounds, resultHandler, boundSql);
    StatementHandler statementHandler = (StatementHandler)this.interceptorChain.pluginAll(statementHandler);
    return statementHandler;
}

public Executor newExecutor(Transaction transaction) {
    return this.newExecutor(transaction, this.defaultExecutorType);
}
。。。。。。。
```

- Tomcat 源码中的RequestFacade

```java
public class RequestFacade implements HttpServletRequest {
	。。。。
        private final class GetRequestDispatcherPrivilegedAction
            implements PrivilegedAction<RequestDispatcher> {

        private final String path;

        public GetRequestDispatcherPrivilegedAction(String path){
            this.path = path;
        }

        @Override
        public RequestDispatcher run() {
            return request.getRequestDispatcher(path);
        }
    }


    private final class GetParameterPrivilegedAction
            implements PrivilegedAction<String> {

        public String name;

        public GetParameterPrivilegedAction(String name){
            this.name = name;
        }

        @Override
        public String run() {
            return request.getParameter(name);
        }
    }


    private final class GetParameterNamesPrivilegedAction
            implements PrivilegedAction<Enumeration<String>> {

        @Override
        public Enumeration<String> run() {
            return request.getParameterNames();
        }
    }
    。。。。。。。
}
```

光看名字我们就知道其用了门面模式，它封装了非常多的request操作，也整合了很多servlet-api以外的内容，给用户使用提供了很大的便捷，同样，tomcat 对response 和session也封装了RequestFacade和StandardSessionFacade类。

### 门面模式的有缺点

**优点**

1、简化了调用过程，无需深入了解子系统，以防给子系统带来风险

2、减少系统依赖，松散耦合

3、更好地划分访问层次，提高了安全性

4、遵循迪米特法则，即最少知道原则

**缺点**

1、当增加子系统和扩展子系统行为时，可能容易带来未知风险

2、不符合开闭原则

3、某些情况下可能违背单一职责原则

