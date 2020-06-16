package com.demo.mediator.chat;

/**
 * @author: Maniac Wen
 * @create: 2020/6/15
 * @update: 8:07
 * @version: V1.0
 * @detail:
 **/
public class ChatRoom {
    public void showMsg(User user,String msg){
        System.out.println("[" + user.getName() + "]" + msg);
    }
}
