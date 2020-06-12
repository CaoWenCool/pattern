package com.demo.memento.blog;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/6/11
 * @update: 7:25
 * @version: V1.0
 * @detail:
 **/
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
