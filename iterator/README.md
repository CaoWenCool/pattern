# 迭代器 模式

## 定义

迭代器又称游标模式，他提供一种顺序访问集合/容器对象元素的方法，而又无须暴露内部表示。迭代器模式可以为不同的容器提供一致的遍历行为，而不用关心容器内容元素组成结构，属于行为型模式。

迭代器模式的本质是抽离集合对象迭代行为到迭代器中，提供一致访问接口。

## 应用场景

迭代器模式在我们的生活中应用的也比较广泛，比如物流系统中的传送带，不管传送的是什么物品，都被打包成一个一个的箱子并且有一个统一的二维码。这样我们不需要关心箱子里面是啥，我们在分发时只需要一个一个检查发送的目的地即可。再比如，我们平时乘坐的交通工具，都是统一刷卡或者刷脸进站，而不需要关心是男性还是女性、是残疾人还是正常人等个性化的信息。

我们把对个对象聚在一起形成的总体称之为集合，集合对象是能够包容一组对象的容器对象。不同的集合其内部元素的聚合结构可能不同，而迭代器模式屏蔽了内部元素获取细节，为外部提供一致的元素访问行为，解耦了元素迭代与集合对象间的耦合，并且通过不同的迭代器，可以为同个集合对象提供不同顺序的元素访问行为，扩展了集合对象元素迭代功能，符合开闭原则。迭代器模式适用于以下场景：

- 访问一个集合对象的内容无需暴露它的内部表示
- 为遍历不同的 集合结构提供一个统一的访问接口

## 角色分工

迭代器模式主要包含四种角色：

- 抽象迭代器：抽象迭代器负责定义访问和遍历元素的接口
- 具体迭代器：提供具体的元素遍历行为
- 抽象容器：负责定义提供具体迭代器的接口
- 具体容器：创建具体迭代器

## 例子1：自定义的迭代器

我们以课程为例，我们自己创建一个课程的集合，集合中每一个 元素就是课程对象，然后自己手写一个迭代器，将每一个课程对象读取出来。

- 创建课程类

```java
public class Course {
    private  String  name;

    public Course(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
```

- 自定义迭代器Iterator接口

```java
public interface Iterator<E> {
    E next();
    boolean hasNext();
}
```

- 创建自定义的课程的集合的接口

```java
public interface CourseAggregate {

    void add(Course course);

    void remove(Course course);

    Iterator<Course> iterator();
}
```

- 实现迭代器接口和集合接口，创建实现类

```java
public class IteratorImpl<E> implements Iterator<E> {
    private List<E> list;
    private int cursor;
    private E  element;

    public IteratorImpl(List<E> list) {
        this.list = list;
    }

    public E next() {
        System.out.print("当前位置" + cursor + ":");
        element = list.get(cursor);
        cursor ++;
        return element;
    }

    public boolean hasNext() {
        if(cursor > list.size() -1){
            return false;
        }
        return true;
    }
}
```

- 创建课程结合 CourseAggregateImpl 实现类

```java
public class CourseAggregateImpl implements CourseAggregate{
    private List courseList;

    public CourseAggregateImpl() {
        this.courseList = new ArrayList();
    }

    public void add(Course course) {
        courseList.add(course);
    }

    public void remove(Course course) {
        courseList.remove(course);
    }

    public Iterator<Course> iterator() {
        return new IteratorImpl<Course>(courseList);
    }
}
```

- 创建客户端代码

```java
public class Client {
    public static void main(String[] args) {
        Course java = new Course("Java 课程");
        Course C =  new Course("C语言课程");
        Course python = new Course("python 课程");
        Course go = new Course("go 语言课程");

        CourseAggregate courseAggregate = new CourseAggregateImpl();
        courseAggregate.add(java);
        courseAggregate.add(C);
        courseAggregate.add(python);
        courseAggregate.add(go);

        System.out.println("----------- 课程列表 --------------");
        printCourse(courseAggregate);
        courseAggregate.remove(C);
        System.out.println("--------删除之后的课程列表 ---------------");
        printCourse(courseAggregate);
    }

    private static void printCourse(CourseAggregate courseAggregate){
        Iterator<Course> iterator = courseAggregate.iterator();
        while (iterator.hasNext()){
            Course course = iterator.next();
            System.out.println(course.getName());
        }
    }
}
```

- 运行结果如下：

![1591923071628](C:\Users\ADMINI~1\AppData\Local\Temp\1591923071628.png)

- 类图

![1591924306971](C:\Users\ADMINI~1\AppData\Local\Temp\1591924306971.png)

## 源码中的迭代器模式

- 大家熟悉的Iterator 源码

```java
public interface Iterator<E> {
    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    default void forEachRemaining(Consumer<? super E> action) {
        Objects.requireNonNull(action);
        while (hasNext())
            action.accept(next());
    }
}
```

上面的代码中，我们看到remove()方法其实是组合模式。迭代器模式和组合模式，两者似乎存在一定的相似性。组合模式解决的是统一树形结构层次访问接口，迭代器模式解决的是统一各个集合对象元素遍历的接口。虽然他们的适配场景不同，但是核心理念是想通的。

接下来我们看一下Iterator的实现类，我们常用的ArrayList 中有一个内部实现类 Itr，他就实现了 Iterator 接口。

```java
private class Itr implements Iterator<E> {
    int cursor;       // index of next element to return
    int lastRet = -1; // index of last element returned; -1 if no such
    int expectedModCount = modCount;

    Itr() {}

    public boolean hasNext() {
        return cursor != size;
    }

    @SuppressWarnings("unchecked")
    public E next() {
        checkForComodification();
        int i = cursor;
        if (i >= size)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i + 1;
        return (E) elementData[lastRet = i];
    }

    public void remove() {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.remove(lastRet);
            cursor = lastRet;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void forEachRemaining(Consumer<? super E> consumer) {
        Objects.requireNonNull(consumer);
        final int size = ArrayList.this.size;
        int i = cursor;
        if (i >= size) {
            return;
        }
        final Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length) {
            throw new ConcurrentModificationException();
        }
        while (i != size && modCount == expectedModCount) {
            consumer.accept((E) elementData[i++]);
        }
        // update once at end of iteration to reduce heap write traffic
        cursor = i;
        lastRet = i - 1;
        checkForComodification();
    }

    final void checkForComodification() {
        if (modCount != expectedModCount)
            throw new ConcurrentModificationException();
    }
}
```

我们继续往下看，在ArrayList 内部还有几个迭代器对Itr进行扩展，首先看一下 ListItr：

```java
private class ListItr extends Itr implements ListIterator<E> {
    ListItr(int index) {
        super();
        cursor = index;
    }

    public boolean hasPrevious() {
        return cursor != 0;
    }

    public int nextIndex() {
        return cursor;
    }

    public int previousIndex() {
        return cursor - 1;
    }

    @SuppressWarnings("unchecked")
    public E previous() {
        checkForComodification();
        int i = cursor - 1;
        if (i < 0)
            throw new NoSuchElementException();
        Object[] elementData = ArrayList.this.elementData;
        if (i >= elementData.length)
            throw new ConcurrentModificationException();
        cursor = i;
        return (E) elementData[lastRet = i];
    }

    public void set(E e) {
        if (lastRet < 0)
            throw new IllegalStateException();
        checkForComodification();

        try {
            ArrayList.this.set(lastRet, e);
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }

    public void add(E e) {
        checkForComodification();

        try {
            int i = cursor;
            ArrayList.this.add(i, e);
            cursor = i + 1;
            lastRet = -1;
            expectedModCount = modCount;
        } catch (IndexOutOfBoundsException ex) {
            throw new ConcurrentModificationException();
        }
    }
}
```

还有SubList 对子集合的迭代处理：

```java
private class SubList extends AbstractList<E> implements RandomAccess {
    private final AbstractList<E> parent;
    private final int parentOffset;
    private final int offset;
    int size;

    SubList(AbstractList<E> parent,
            int offset, int fromIndex, int toIndex) {
        this.parent = parent;
        this.parentOffset = fromIndex;
        this.offset = offset + fromIndex;
        this.size = toIndex - fromIndex;
        this.modCount = ArrayList.this.modCount;
    }

    public E set(int index, E e) {
        rangeCheck(index);
        checkForComodification();
        E oldValue = ArrayList.this.elementData(offset + index);
        ArrayList.this.elementData[offset + index] = e;
        return oldValue;
    }

    public E get(int index) {
        rangeCheck(index);
        checkForComodification();
        return ArrayList.this.elementData(offset + index);
    }

    public int size() {
        checkForComodification();
        return this.size;
    }

    public void add(int index, E e) {
        rangeCheckForAdd(index);
        checkForComodification();
        parent.add(parentOffset + index, e);
        this.modCount = parent.modCount;
        this.size++;
    }

    public E remove(int index) {
        rangeCheck(index);
        checkForComodification();
        E result = parent.remove(parentOffset + index);
        this.modCount = parent.modCount;
        this.size--;
        return result;
    }

    protected void removeRange(int fromIndex, int toIndex) {
        checkForComodification();
        parent.removeRange(parentOffset + fromIndex,
                           parentOffset + toIndex);
        this.modCount = parent.modCount;
        this.size -= toIndex - fromIndex;
    }

    public boolean addAll(Collection<? extends E> c) {
        return addAll(this.size, c);
    }

    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        int cSize = c.size();
        if (cSize==0)
            return false;

        checkForComodification();
        parent.addAll(parentOffset + index, c);
        this.modCount = parent.modCount;
        this.size += cSize;
        return true;
    }

    public Iterator<E> iterator() {
        return listIterator();
    }

    public ListIterator<E> listIterator(final int index) {
        checkForComodification();
        rangeCheckForAdd(index);
        final int offset = this.offset;

        return new ListIterator<E>() {
            int cursor = index;
            int lastRet = -1;
            int expectedModCount = ArrayList.this.modCount;

            public boolean hasNext() {
                return cursor != SubList.this.size;
            }

            @SuppressWarnings("unchecked")
            public E next() {
                checkForComodification();
                int i = cursor;
                if (i >= SubList.this.size)
                    throw new NoSuchElementException();
                Object[] elementData = ArrayList.this.elementData;
                if (offset + i >= elementData.length)
                    throw new ConcurrentModificationException();
                cursor = i + 1;
                return (E) elementData[offset + (lastRet = i)];
            }

            public boolean hasPrevious() {
                return cursor != 0;
            }

            @SuppressWarnings("unchecked")
            public E previous() {
                checkForComodification();
                int i = cursor - 1;
                if (i < 0)
                    throw new NoSuchElementException();
                Object[] elementData = ArrayList.this.elementData;
                if (offset + i >= elementData.length)
                    throw new ConcurrentModificationException();
                cursor = i;
                return (E) elementData[offset + (lastRet = i)];
            }

            @SuppressWarnings("unchecked")
            public void forEachRemaining(Consumer<? super E> consumer) {
                Objects.requireNonNull(consumer);
                final int size = SubList.this.size;
                int i = cursor;
                if (i >= size) {
                    return;
                }
                final Object[] elementData = ArrayList.this.elementData;
                if (offset + i >= elementData.length) {
                    throw new ConcurrentModificationException();
                }
                while (i != size && modCount == expectedModCount) {
                    consumer.accept((E) elementData[offset + (i++)]);
                }
                // update once at end of iteration to reduce heap write traffic
                lastRet = cursor = i;
                checkForComodification();
            }

            public int nextIndex() {
                return cursor;
            }

            public int previousIndex() {
                return cursor - 1;
            }

            public void remove() {
                if (lastRet < 0)
                    throw new IllegalStateException();
                checkForComodification();

                try {
                    SubList.this.remove(lastRet);
                    cursor = lastRet;
                    lastRet = -1;
                    expectedModCount = ArrayList.this.modCount;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            public void set(E e) {
                if (lastRet < 0)
                    throw new IllegalStateException();
                checkForComodification();

                try {
                    ArrayList.this.set(offset + lastRet, e);
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            public void add(E e) {
                checkForComodification();

                try {
                    int i = cursor;
                    SubList.this.add(i, e);
                    cursor = i + 1;
                    lastRet = -1;
                    expectedModCount = ArrayList.this.modCount;
                } catch (IndexOutOfBoundsException ex) {
                    throw new ConcurrentModificationException();
                }
            }

            final void checkForComodification() {
                if (expectedModCount != ArrayList.this.modCount)
                    throw new ConcurrentModificationException();
            }
        };
    }

    public List<E> subList(int fromIndex, int toIndex) {
        subListRangeCheck(fromIndex, toIndex, size);
        return new SubList(this, offset, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+this.size;
    }

    private void checkForComodification() {
        if (ArrayList.this.modCount != this.modCount)
            throw new ConcurrentModificationException();
    }

    public Spliterator<E> spliterator() {
        checkForComodification();
        return new ArrayListSpliterator<E>(ArrayList.this, offset,
                                           offset + this.size, this.modCount);
    }
}
```

- 接下来我们看一下Mybatis 中的DefaultCursor 类：

```java
private final CursorIterator cursorIterator = new CursorIterator();
```

它实现了Cursor接口，而且定义了一个成员 变量cursorIterator,继续查看CursorIterator 的源代码发现，他是DefaultCursor 的一个内部类，并且实现了JDK中Iterator 接口。

## 迭代器模式的优缺点

**优点**

1、多态迭代：为不同的聚合结构提供一致的遍历接口，即一个迭代接口可以访问不同的集合对象。

2、简化集合对象接口：迭代器模式将集合对象本身应该提供的 元素迭代接口抽取到了迭代器中，是集合对象无须关心具体迭代行为。

3、元素迭代功能多样化：每个集合对象都可以提供一个或者多个不同的迭代器，使得同种元素聚合结构可以有不同的 迭代行为

4、解耦迭代与集合：迭代器模式封装了具体的迭代算法，迭代算法的变化，不会影响到集合对象的架构。

**缺点**

1、对于比较简单的遍历，像数组或者有序列表，使用迭代器方式遍历较为繁琐。

