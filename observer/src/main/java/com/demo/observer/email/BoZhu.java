package com.demo.observer.email;

import lombok.Data;

import java.util.Observable;
import java.util.Observer;

/**
 * @author: Maniac Wen
 * @create: 2020/4/12
 * @update: 20:07
 * @version: V1.0
 * @detail:
 **/
@Data
public class BoZhu implements Observer{
    private String name;
    public BoZhu(String name){
        this.name = name;
    }

    public void update(Observable o, Object arg) {
        WeiBo weiBo = (WeiBo) o;
        Message message = (Message) arg;
        System.out.println("----------------------------------------");
        System.out.println(name + "您好，你有一个新的消息请注意查收");
        System.out.println(weiBo.getName() + "你好，您收到了一个来自粉丝:"+ message.getName() +"的来信，内容如下：" + message.getContent() );
    }
}
