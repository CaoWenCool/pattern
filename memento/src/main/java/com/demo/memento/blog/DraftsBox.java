package com.demo.memento.blog;

import java.util.Stack;

/**
 * @author: Maniac Wen
 * @create: 2020/6/11
 * @update: 7:32
 * @version: V1.0
 * @detail:
 **/
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
