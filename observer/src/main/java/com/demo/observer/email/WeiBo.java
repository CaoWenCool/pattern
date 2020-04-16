package com.demo.observer.email;

import java.util.Observable;

/**
 * @author: Maniac Wen
 * @create: 2020/4/12
 * @update: 20:00
 * @version: V1.0
 * @detail:
 **/
public class WeiBo extends Observable{
    private String name = "微博自动提醒";
    private  static WeiBo weiBo = null;
    private WeiBo(){

    }
    public static WeiBo getInstance(){
        if(null == weiBo){
            return new WeiBo();
        }
        return weiBo;
    }
    public String getName(){
        return name;
    }
    public void publish(WeiBo weiBo,Message message){
        System.out.println(weiBo.name + "通知您，:"+ message.getName()+"给您发来了一个消息，请注意查收");
        setChanged();
        notifyObservers(message);
    }
}
