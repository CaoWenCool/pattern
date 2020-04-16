package com.demo.observer.keyboard;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:34
 * @version: V1.0
 * @detail:
 **/
public class Keyboard extends EventListener{
    public void up() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向上移动");
        this.trigger(KeyboardType.ON_E);
    }
    public void down() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向下移动");
        this.trigger(KeyboardType.ON_D);
    }
    public void right() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始向右移动");
        this.trigger(KeyboardType.ON_F);
    }
    private void attack() throws InvocationTargetException, IllegalAccessException {
        System.out.println("人物开始发起攻击");
        this.trigger(KeyboardType.ON_K);
    }
}
