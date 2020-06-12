package com.demo.memento.blog;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/6/11
 * @update: 7:27
 * @version: V1.0
 * @detail:
 **/
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
