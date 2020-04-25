

## 原型模式

### 定义

原型模式是指原型实例指定创建对象的种类，并且通过拷贝这些原型创建新的对象。

### 使用场景

1、类初始化消耗的资源较多

2、new 产生的一个对象需要非常繁琐的过程（数据准备、访问权限等）

3、构造函数比较复杂

4、循环体中产生大量的对象时

### 在源码中的原型模式

- 在Spring 中，我们通过scope="prototype"，让我们的bean通过原型模式进行创建
- JSON的parseObject()也是一种原型模式

### 例子1（浅克隆）

一个标准的原型模式代码，应该是这样设计的。先创建原型接口：Prototype

```java
public interface Prototype {
    Prototype clone();
}
```

创建需要克隆的对象

```java
public class CloneA implements Prototype{
    private long id;
    private String name;
    private String dna;
    List<String> tag;

    public Prototype clone() {
        CloneA cloneA = new CloneA();
        cloneA.setId(this.id);
        cloneA.setDna(this.dna);
        cloneA.setName(this.name);
        cloneA.setTag(this.tag);
        return cloneA;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDna() {
        return dna;
    }
    public void setDna(String dna) {
        this.dna = dna;
    }
    public List<String> getTag() {
        return tag;
    }
    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
```

创建client对象

```java
public class Client {
    private Prototype prototype;
    public Client(Prototype prototype){
        this.prototype = prototype;
    }

    public Prototype clone(Prototype prototype){
        return prototype.clone();
    }
}
```

测试：

```java
public class Test {

    public static void main(String[] args) {
        //创建一个需要克隆的对象
        CloneA cloneA = new CloneA();
        cloneA.setDna("XY");
        cloneA.setName("张三");
        cloneA.setId(1);
        List tag = new ArrayList<String>();
        tag.add("学生");
        tag.add("性别男");
        tag.add("爱好女");
        cloneA.setTag(tag);
        System.out.println(cloneA);
        //创建Client 对象，开始准备进行克隆
        Client client = new Client(cloneA);
        CloneA cloneB = (CloneA) client.clone(cloneA);
        System.out.println(cloneB);
        System.out.println("--------------------------------");
        System.out.println("克隆对象中的引用类型地址：" + cloneB.getTag());
        System.out.println("原对象中的引用类型地址：" + cloneA.getTag());
        System.out.println("对象地址的比较：" + (cloneA.getTag() == cloneB.getTag()));
    }
}
```

运行结果：

![1587779493918](C:\Users\ADMINI~1\AppData\Local\Temp\1587779493918.png)

从结果中我们不难看出，tag 的引用地址是相同的，表示我们复制的不是其值而是其引用的地址。这样的话，一旦我们修改其中的任意的一个对象的值，cloneA 和cloneB 的都会随之改变。这就是我们常说的浅克隆。只是完整的复制值类型数据，没有赋值引用对象。所有的引用对象还是指向原来的对象。接下来我们看下深克隆

### 深度克隆

我相信大家都知道克隆羊多利吧，我们的克隆技术其实就是我们原型模式的经典实现。接下来我们将模拟克隆羊多利的诞生。

- 创建Sheep类

```java
public class Sheep {
    public String DNA;
    public int height;
    public int weight;
}
```

- 创建羊的标签类

```java
public class SheepTag implements Serializable{
    public String name;
    public List<String> tag;
}
```

- 创建克隆对象，多利类

```java
public class DuoLi extends Sheep implements Serializable,Cloneable{
    public SheepTag sheepTag;
    public DuoLi(){
        this.DNA = "xx";
        this.weight = 100;
        this.height = 100;
        this.sheepTag = new SheepTag();
        this.sheepTag.name = "多利";
        List<String> tag = new ArrayList<String>();
        tag.add("编号001");
        tag.add("世界上第一只克隆羊");
        this.sheepTag.tag = tag;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public Object deepClone(){

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            DuoLi copy = (DuoLi) objectInputStream.readObject();
            copy.DNA = "xx";
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 浅克隆
     * @param propoty
     * @return
     */
    public DuoLi shallowClone(DuoLi propoty){
        DuoLi duoLi1 = new DuoLi();
        duoLi1.DNA = propoty.DNA;
        duoLi1.height = propoty.height;
        duoLi1.weight = propoty.weight;

        duoLi1.sheepTag = propoty.sheepTag;
        return duoLi1;
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        DuoLi duoLi = new DuoLi();
        try {
            DuoLi clone = (DuoLi) duoLi.clone();
            System.out.println("深克隆：" + (duoLi.sheepTag == clone.sheepTag));
            System.out.println("深克隆：" + (duoLi.sheepTag.tag == clone.sheepTag.tag));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DuoLi duoLi1 = new DuoLi();
        DuoLi shallow = duoLi1.shallowClone(duoLi1);
        System.out.println("浅克隆：" + (duoLi1.sheepTag == shallow.sheepTag));
    }
}
```

- 运行结果

![1587781704907](C:\Users\ADMINI~1\AppData\Local\Temp\1587781704907.png)

### 克隆破坏单例

我们通过以方式实现了深度克隆，但是如果我们克隆的是单例对象，就意味着我们破坏了单例。那么我们又要如何防止我们的单例对象被深克隆所破坏呢？

其实思路很简单，不让他进行深度克隆不久可以了吗。具体实现可以是让我们的单例类不实现Cloneable接口，要么就重写我们的clone()方法，在clone方法中返回我们的单例对象。具体代码如下

```JAVA
@Override
protected Object clone() throws CloneNotSupportedException {
    return INSTANCE;
}
```

### 在源码中的应用

我们使用的ArrayList 就实现了Cloneable接口：

```java
public Object clone() {
    try {
        ArrayList<?> v = (ArrayList<?>) super.clone();
        v.elementData = Arrays.copyOf(elementData, size);
        v.modCount = 0;
        return v;
    } catch (CloneNotSupportedException e) {
        // this shouldn't happen, since we are Cloneable
        throw new InternalError(e);
    }
}
```

```java
@SuppressWarnings("unchecked")
public static <T> T[] copyOf(T[] original, int newLength) {
    return (T[]) copyOf(original, newLength, original.getClass());
}
```

```java
public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
    @SuppressWarnings("unchecked")
    T[] copy = ((Object)newType == (Object)Object[].class)
        ? (T[]) new Object[newLength]
        : (T[]) Array.newInstance(newType.getComponentType(), newLength);
    System.arraycopy(original, 0, copy, 0,
                     Math.min(original.length, newLength));
    return copy;
}
```