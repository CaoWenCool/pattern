package com.demo.memento.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/11
 * @update: 7:34
 * @version: V1.0
 * @detail:
 **/
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
