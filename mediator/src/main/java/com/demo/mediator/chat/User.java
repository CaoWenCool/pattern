package com.demo.mediator.chat;

/**
 * @author: Maniac Wen
 * @create: 2020/6/15
 * @update: 8:05
 * @version: V1.0
 * @detail:
 **/
public class User {
    private String name;
    private ChatRoom chatRoom;

    public User(String name, ChatRoom chatRoom) {
        this.name = name;
        this.chatRoom = chatRoom;
    }

    public void sendMessage(String msg){
        this.chatRoom.showMsg(this,msg);
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}
