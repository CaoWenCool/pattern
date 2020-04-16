package com.demo.observer.keyboard;

/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:50
 * @version: V1.0
 * @detail:
 **/
public class KeyboardEventCallBack {
    public void onUp(Event event){
        System.out.println("------------触发按键 e 的事件 --------------------" + "\n" + event);
    }
    public void onDown(Event event){
        System.out.println("------------触发按键 d 的事件 --------------------"+ "\n" + event);
    }
    public void onLeft(Event event){
        System.out.println("------------触发按键 s 的事件 --------------------"+ "\n" + event);
    }
    public void onRight(Event event){
        System.out.println("------------触发按键 f 的事件 --------------------"+ "\n" + event);
    }
    public void onAttack(Event event){
        System.out.println("------------触发按键 k 的事件 --------------------"+ "\n" + event);
    }
}
