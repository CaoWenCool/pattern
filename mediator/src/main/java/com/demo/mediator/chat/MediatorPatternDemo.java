package com.demo.mediator.chat;

/**
 * @author: Maniac Wen
 * @create: 2020/6/15
 * @update: 8:09
 * @version: V1.0
 * @detail:
 **/
public class MediatorPatternDemo {
    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom();
        User zs = new User("张三",chatRoom);
        User ls = new User("李四",chatRoom);
        zs.sendMessage("My Name is 张三");
        ls.sendMessage("Hello 张三");
    }
}
