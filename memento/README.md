# 备忘录模式

## 定义

备忘录模式又称快照模式或者令牌模式，是指在不破坏封装的前提下，捕获一个 对象的内部状态，并在对象之外保存这个状态。这样以后就可以将该对象恢复到原先保存的状态，属于行为型模式。

在软件系统中，备忘录模式可以为我们提供一种后悔药的机制，它通过存储系统各个历史状态的快照，使得我们可以在任一时刻将系统回滚到某一个历史状态。

备忘录模式本质是从实体类隔离存储功能，降低实体类的职责。同时，由于存储信息独立，且存储信息的实体交由管理类管理，则可以通过管理类扩展额外的功能对存储信息进行扩展操作

## 备忘录模式的应用场景

对于我们来说，可能天天都在使用备忘录模式，比如我们每天使用的Git、SVN都可以提供一种代码版本撤回的功能，还有一个比较贴切的现实场景应该是游戏的存档功能，通过将游戏当前进度存储到本地文件系统或者数据库中，使得下次继续游戏时，玩家可以从之前的位置继续进行。

备忘录模式适用于以下应用 场景：

1、需要保存历史快照的场景

2、希望在对象之外保存状态，且除了自己其它类对象无法访问状态保存具体内容。

## 角色分工

备忘录模式包含三种角色：

- 发起人角色

负责创建一个备忘录，记录自身需要保存的状态，具备状态回滚功能。

- 备忘录角色

用于存储Originator 的内部状态，且可以防止Originator 以外的对象进行访问。

- 备忘录管理员角色

负责存储，提供管理备忘录，无法对备忘录内容进行操作和访问。

## 利用压栈管理落地备忘录模式

在网页中的富文本编辑中通常会附带草稿箱、撤销等这样的操作。下面我们用一段代码来实现这样的功能。

- 发起人角色编辑器

```java
@Data
public class Editor {
    private String  title;
    private String content;
    private String imgs;

    public Editor(String title, String content, String imgs) {
        this.title = title;
        this.content = content;
        this.imgs = imgs;
    }

    public ArticleMemento saveToMemento(){
        ArticleMemento articleMemento = new ArticleMemento(this.title,this.content,this.imgs);
        return articleMemento;
    }

    public void undoFromMemento(ArticleMemento articleMemento){
        this.title = articleMemento.getTitle();
        this.content = articleMemento.getContent();
        this.imgs = articleMemento.getImgs();
    }

    @Override
    public String toString() {
        return "Editor{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }
}
```

- 创建备忘录角色

```java
@Data
public class ArticleMemento {
    private String title;
    private String content;
    private String imgs;

    @Override
    public String toString() {
        return "AriticleMemento{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", imgs='" + imgs + '\'' +
                '}';
    }

    public ArticleMemento(String title, String content, String imgs) {
        this.title = title;
        this.content = content;
        this.imgs = imgs;
    }
}
```

- 最后创建备忘录 管理角色草稿箱

```java
public class DraftsBox {
    private  final Stack<ArticleMemento>  STACK = new Stack<ArticleMemento>();
    public ArticleMemento getMemento(){
        ArticleMemento articleMemento  = STACK.pop();
        return articleMemento;
    }
    public void  addMemento(ArticleMemento articleMemento){
        STACK.push(articleMemento);
    }
}
```

草稿箱中定义的Stack类是Vector的一个子类，它实现了一个标准的后进先出的栈，主要定义了如下方法：

| 定义方法                    | 方法描述                                       |
| --------------------------- | ---------------------------------------------- |
| boolean  empty()            | 测试堆栈是否为空                               |
| Object peek（）             | 查看堆栈顶部的对象，但不从堆栈中移除它。       |
| Object pop()                | 移除堆栈顶部的对象，并作为此函数的值返回该对象 |
| Object push(Object element) | 把对象压入堆栈顶部                             |
| int search(Object element)  | 返回对象在堆栈中的位置，以1为基数              |

- 测试代码：

```java
public class Test {
    public static void main(String[] args) {
        DraftsBox draftsBox = new DraftsBox();
        Editor editor =  new Editor("设计模式，java 工程师的必备技能","本文主要记录了设计模式中的备忘录模式","test.png");
        ArticleMemento articleMemento = editor.saveToMemento();
        draftsBox.addMemento(articleMemento);
        System.out.println(editor.toString());
        System.out.println("================第一次修改文章========================");
        editor.setTitle("【设计模式】，Java工程师的必备技能");
        editor.setContent("本文主要记录了设计模式中的备忘录模式，学好设计模式的前提是要有一个好的基础");
        System.out.println(editor.toString());
        System.out.println("================第一次修改完成========================");
        articleMemento = editor.saveToMemento();
        draftsBox.addMemento(articleMemento);
        System.out.println("================保存到草稿箱========================");
        System.out.println("================第二次修改文章========================");
        editor.setTitle("【设计模式】，Java工程师的必备技能。一个好的Java工程师一定要学会设计自己的代码");
        editor.setContent("本文主要记录了设计模式中的备忘录模式，学好设计模式的前提是要有扎实Java基础与平常的多加练习");
        System.out.println(editor.toString());
        System.out.println("================第二次修改完成========================");
        System.out.println("================第一次撤销文章========================");
        articleMemento = draftsBox.getMemento();
        editor.undoFromMemento(articleMemento);
        System.out.println(editor.toString());
        System.out.println("================第一次撤销文章完成========================");
        System.out.println("================第二次撤销文章========================");
        articleMemento = draftsBox.getMemento();
        editor.undoFromMemento(articleMemento);
        System.out.println(editor.toString());
        System.out.println("================第二次撤销文章完成========================");
    }
}
```

- 运行结果

![1591834055182](C:\Users\ADMINI~1\AppData\Local\Temp\1591834055182.png)

## 备忘录模式的优缺点

**优点**

1、简化发起人实体类Originator的职责，隔离状态存储与获取，实现了信息的封装，客户端无需关心状态的保存细节。

2、提供状态的回滚功能

**缺点**

1、消耗资源，如果需要保存的状态过多时，每一次保存都会消耗很多内存。

