## 组合模式

### 定义

组合模式，又叫部分整体模式，是用于把一组相似的队形当做一个单一的对象。组合模式依据属性 结构来组合对象，用来标识部分以及整天的层次。这种类型的设计模式属于结构型模式，它创建了对象组的树形结构。

我们的国家，由中央政府领导，中央政府管辖着各个省地方政府，省政府又管辖着各个市政府。这就是典型的组合模式。

### 角色

组合关系和聚合关系的区别：

- 组合关系：在古代皇帝三宫六院，贵妃很多，但是每一个贵妃都是属于皇帝的（具有相同的声明周期）。
- 聚合关系：一个老师有很多学生，但是每一个学生有属于多个老师（具有不同的生命周期）

组合模式一般用来模式整体与部门的关系，它将对象组织到树形结构中，最顶层的节点称为根节点，根节点下面可以包含树枝节点和叶子节点，树枝 节下面又可以包含树枝节点和叶子节点.

如下所示：

![1590021645851](C:\Users\ADMINI~1\AppData\Local\Temp\1590021645851.png)

由上图可以看出，其实根节点和树枝节点本质上是同一种数据类型，可以作为容器使用；而叶子节点与树枝节点在语义上不属于同一种类型，但是在组合模式中，会把树枝节点和叶子节点认为是同一种数据类型（用统一接口定义），让他们具备一致行为。这样，在组合模式中，整个属性结构中的对象都是同一种类型，带来的一个好处就是客户无需辨别树枝节点还是叶子节点，而是可以直接进行操作，带来了极大的便利。

组合模式中包含三个角色：

- 抽象根节点:定义系统各层次对象的共有方法和属性，可以预先定义一些默认的行为和属性
- 树枝节点：定树枝节点的行为，存储子节点，组合树枝节点和叶子节点形成一个树形结构
- 叶子节点：叶子节点对象，其下再无分支，是系统层次遍历的最小单位。

组合模式在代码 具体实现上，有两种不同的方式，分别 是透明组合模式和安全组合模式

### 组合模式的应用场景

当子系统与其内各个对象层次呈现树形结构时，可以使用组合模式让子系统内各个对象层次的行为操作具备一致性。客户端使用子系统内任意一个层次对象时，无须进行区分，直接使用通用操作即可，为客户端的使用带来了 便捷。

注意：如果树形结构系统不使用组合模式进行架构，那么按照正常的思维逻辑，对该系统进行职责划分，按照上文树形结构图表示，该系统具备两种对象层次类型：树枝节点和叶子节点。那么我们就需要构造两种对应的类型，然后由于树枝节点具备容器功能，因此树枝节点类内部需要维护多个集合存储其他对象层次（如：List<Composite>,List<Leaf>）。如果当前系统对象层次更复杂时，那么树枝节点内又要增加对应的层次集合，这对树枝节点的构建带来了巨大的复杂性，臃肿性以及不可扩展性。同时客户端访问该系统层次时，还需进行层次区分，这样才能使用对应的行为，给客户端的使用也带来了巨大的复杂性。而使用组合模式构建该系统，由于组合模式抽取了系统各个层次的共性 行为，具体层次只需按需实现所需行为即可，这样子系统各个层次就都属于同一种类型，所以树枝节点只需维护一个集合（List<Component>）即可存储系统所有层次内容，并且客户端也无需区分该系统各个层次对象，对内系统架构简洁优雅，对外接口精简易用。

应用场景总结如下：

- 系统客户端可以忽略组合对象与单个对象的差异时
- 对象层次具备整体和部分，呈树形结构

我们生活中的组合模式：树形的菜单，公司架构等等

### 透明组合模式的写法

透明组合模式是把所有公共方法都定义在Compnent中，这样做的好处是客户端无需分辨是叶子节点和树枝节点，它们具备完全一致的 接口。

例如我们的公司架构，一个公司内部可能有研发部门，人事部分，行政部门等等，每个部门的功能都是不一样的，但是其都有一些共性，都是属于该公司的，都被该公司的规章制度所制约等等，对公司而言这些部门是整体和部分的关系，可以用组合模式来进行设计。

- 先创建一个顶层 的抽象组件：CompanyComponent

```java
public abstract class CompanyComponent {
    public String getName(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持获取名称");
    }
    public int getPeopleNum(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持获取人员数量");
    }
    public void addDepartment(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持添加操作");
    }
    public void removeDepartment(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持移除操作");
    }
    public void print(){
        throw new UnsupportedOperationException("不支持打印");
    }
}

```

把所有可能的方法都定义到这个最顶层的抽象类中，但是不写任何的逻辑处理而是直接抛出异常，这里可能会有人存在疑惑，为什么不用抽象方法？因为，用了抽象方法，其子类就必须实现，这样便体现不出各个子类的细微差异。因此子类继承此抽象后，只需要重写有差异的方法覆盖父类的方法即可。

- 创建部门类：Department

```java
public class Department extends CompanyComponent{
    private String name;
    private int num;
    public Department(String name,int num){
        this.name = name;
        this.num = num;
    }
    @Override
    public String getName(CompanyComponent companyComponent) {
        return this.name;
    }
    @Override
    public int getPeopleNum(CompanyComponent companyComponent) {
        return this.num;
    }
    @Override
    public void print() {
        System.out.println("部门名称:" + name + ",该部门人数：" + num);
    }
}
```

- 创建DepartmentPackage类

```java
public class DepartmentPackage extends CompanyComponent{
    private List<CompanyComponent> companyComponents = new ArrayList<CompanyComponent>();
    private String name;
    private Integer level;
    public DepartmentPackage(String name,Integer level){
        this.name = name;
        this.level = level;
    }
    @Override
    public void addDepartment(CompanyComponent companyComponent) {
        companyComponents.add(companyComponent);
    }
    @Override
    public String getName(CompanyComponent companyComponent) {
        return this.name;
    }
    @Override
    public void removeDepartment(CompanyComponent companyComponent) {
        companyComponents.remove(companyComponent);
    }
    @Override
    public void print() {
       System.out.println(this.name);
       for(CompanyComponent companyComponent:companyComponents){
            if(this.level != null){
                for(int i=0;i<level;i++){
                    System.out.print("  ");
                }
            }
           companyComponent.print();
       }
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        CompanyComponent xiaoshou = new Department("营销中心",14);
        CompanyComponent yanfa = new Department("研发中心",13);
        CompanyComponent company = new DepartmentPackage("***有限公司，一级部门",1);
        company.addDepartment(xiaoshou);
        company.addDepartment(yanfa);
        CompanyComponent renshi = new Department("人事部",9);
        CompanyComponent xingzheng = new Department("行政部",8);
        CompanyComponent houqin = new Department("后勤部",7);
        CompanyComponent company1 = new DepartmentPackage("***有限公司，二级部门",2);
        company1.addDepartment(renshi);
        company1.addDepartment(xingzheng);
        company1.addDepartment(houqin);
        company.print();
        company1.print();
    }
}
```

- 运行结果

![1590106699811](C:\Users\ADMINI~1\AppData\Local\Temp\1590106699811.png)

透明组合模式把所有公共方法都定义在Component中，这样做的好处是客户端无需分辨是叶子节点和树枝节点，他们具备完全一致的接口，缺点是叶子节点会继承 得到一些他不需要的方法，这与设计模式接口隔离原则相违背。

### 安全组合模式

安全组合模式是只规定系统各个层次的最基础的一致行为，而把组合(树节点)本身的方法（管理子类对象的添加，删除等）放到自身当中.

我们的电脑文件其实就是一个典型的树形结构，目录包含文件夹和文件，文件件里面又可以包含文件夹和文件。。。下面我们就用代码来实现一个目录系统

文件系统有两个大的层次：文件夹和文件。其中，文件夹能容纳其他层次，为树枝节点；文件为最小单位为叶子节点。由于目录系统层次较少，且树枝节点结构相对稳定，而文件其实可以有很多类型，所有这里我们选择使用安全组合模式来实现目录系统，可以避免为叶子类型（文件）引入冗余方法。

- 抽象组件Directory

```java
public abstract class Directory {
    protected String name;
    public Directory(String name){
        this.name = name;
    }
    public abstract void show();
}
```

- 文件类File和文件夹类Folder

```java
public class File extends Directory{
    public File(String name){
        super(name);
    }
    public void show() {
        System.out.println(this.name);
    }
}
```

```java
public class Folder extends Directory{
    private List<Directory> dirs;
    private Integer level;
    public Folder(String name,Integer level){
        super(name);
        this.level = level;
        this.name = name;
        this.dirs = new ArrayList<Directory>();
    }
    public void show() {
        System.out.println(this.name);
        for(Directory dir:this.dirs) {
            if (this.level != null) {
                for (int i = 0; i < this.level; i++) {
                    System.out.print("  ");
                }
            }
            dir.show();
        }
    }
    public boolean add(Directory directory){
        return this.dirs.add(directory);
    }
    public boolean remove(Directory directory){
        return this.dirs.remove(directory);
    }
    public Directory get(int index){
        return this.dirs.get(index);
    }
    public void list(){
        for(Directory dir:dirs){
            System.out.println(dir.name);
        }
    }
}
```

- 测试类

```java
public class Test {
    public static void main(String[] args) {
        File java = new File("java.exe");

        File idea  = new File("IDEA.exe");
        File eclipse = new File("eclipse.exe");
        Folder editor = new Folder("编辑器",2);
        editor.add(idea);
        editor.add(eclipse);

        File word  = new File("Word.exe");
        File excel = new File("Excel.exe");
        File ppt = new File("Ppt.exe");
        Folder office = new Folder("办公软件",3);
        office.add(word);
        office.add(excel);
        office.add(ppt);

        Folder root = new Folder("根目录",1);
        root.add(editor);
        root.add(office);
        root.add(java);

        root.show();
        System.out.println("----------------------------------------");
        root.list();
    }
}
```

- 运行结果：

![1590108343673](C:\Users\ADMINI~1\AppData\Local\Temp\1590108343673.png)

安全组合模式的好处是接口定义职责清晰，符合设计模式单一职责原则和接口隔离原则；缺点是客户需要区分树枝节点和叶子节点，这样才能正确处理各个层次的操作，客户端无法依赖抽象，违背了设计模式依赖倒置原则。

透明组合模式将公共接口封装到抽象根节点中，那么系统所有节点就具备一致行为，所以如果当系统绝大多数层次具备相同的公共行为时，采用透明组合模式也许会更好。

如果当系统各个层次差异行为较多或者树节点层次相对稳定时，采用安全组合模式

### 组合模式在源码中的应用

- HashMap 的putAll()方法

```java
public void putAll(Map<? extends K, ? extends V> m) {
    putMapEntries(m, true);
}
```

```java
final void putMapEntries(Map<? extends K, ? extends V> m, boolean evict) {
    int s = m.size();
    if (s > 0) {
        if (table == null) { // pre-size
            float ft = ((float)s / loadFactor) + 1.0F;
            int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                     (int)ft : MAXIMUM_CAPACITY);
            if (t > threshold)
                threshold = tableSizeFor(t);
        }
        else if (s > threshold)
            resize();
        for (Map.Entry<? extends K, ? extends V> e : m.entrySet()) {
            K key = e.getKey();
            V value = e.getValue();
            putVal(hash(key), key, value, false, evict);
        }
    }
}
```

我们看到putAll()方法传入的对象是Map对象，Map就是一个抽象的构件，而HashMap 是一个中间构件，HashMap 中的Node 节点就是叶子节点。说道中间构件就会有规定的存储方式。HashMap 中的存储方式是一个静态内部类的数组Node<K,V>[] tab.其源码如下：

```java
static class Node<K,V> implements Map.Entry<K,V> {
    final int hash;
    final K key;
    V value;
    Node<K,V> next;

    Node(int hash, K key, V value, Node<K,V> next) {
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
```

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
               boolean evict) {
    Node<K,V>[] tab; Node<K,V> p; int n, i;
    if ((tab = table) == null || (n = tab.length) == 0)
        n = (tab = resize()).length;
    if ((p = tab[i = (n - 1) & hash]) == null)
        tab[i] = newNode(hash, key, value, null);
    else {
        Node<K,V> e; K k;
        if (p.hash == hash &&
            ((k = p.key) == key || (key != null && key.equals(k))))
            e = p;
        else if (p instanceof TreeNode)
            e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
        else {
            for (int binCount = 0; ; ++binCount) {
                if ((e = p.next) == null) {
                    p.next = newNode(hash, key, value, null);
                    if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                        treeifyBin(tab, hash);
                    break;
                }
                if (e.hash == hash &&
                    ((k = e.key) == key || (key != null && key.equals(k))))
                    break;
                p = e;
            }
        }
        if (e != null) { // existing mapping for key
            V oldValue = e.value;
            if (!onlyIfAbsent || oldValue == null)
                e.value = value;
            afterNodeAccess(e);
            return oldValue;
        }
    }
    ++modCount;
    if (++size > threshold)
        resize();
    afterNodeInsertion(evict);
    return null;
}
```

- 我们常用的ArrayList 对象也有addAll（）方法，其参数也是ArrayList 的父类Collection，来看源码：

```java
public class ArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Cloneable, java.io.Serializable
{
```

```java
public boolean addAll(Collection<? extends E> c) {
    Object[] a = c.toArray();
    int numNew = a.length;
    ensureCapacityInternal(size + numNew);  // Increments modCount
    System.arraycopy(a, 0, elementData, size, numNew);
    size += numNew;
    return numNew != 0;
}
```

- MyBatis 解析各种Mapping 文件的SQL，设计了一个非常关键的类叫做SqlNode，xml中的每一个Node 都会解析为一个SqlNode对象，最后把所有的SqlNode 拼装到一起就成了一条完整的SQL语句

```java
public interface SqlNode {
  boolean apply(DynamicContext context);
}
```

apply方法会根据传入的参数content ，参数解析 该SqlNode所记录的SQL片段，并调用DynamicContext.appendSql（）方法 将解析后的SQL片段追加到DynamicContext 的sqlBuilder 中保存。当SQL节点下的所有 SqlNode 完成解析后，可以通过DynamicContext.getSql()获取一条完成的SQL语句。

类图如下：

![1590109489234](C:\Users\ADMINI~1\AppData\Local\Temp\1590109489234.png)

### 组合模式的优缺点

**优点**

- 清除地定义分层次的复杂对象，表示对象的全部或者部分层次
- 让客户端忽略了层次的 差异，方便对整个层次结构进行控制
- 简化客户端代码
- 符合开闭原则

**缺点**

- 限制类型是会较为复杂
- 使得设计变得更加抽象

