package com.demo.observer.keyboard;

import java.lang.reflect.InvocationTargetException;

/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:53
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        KeyboardEventCallBack callBack = new KeyboardEventCallBack();
        //注册事件
        Keyboard keyboard = new Keyboard();
        keyboard.addListener(KeyboardType.ON_D,callBack);
        keyboard.addListener(KeyboardType.ON_E,callBack);
        keyboard.addListener(KeyboardType.ON_S,callBack);
        keyboard.addListener(KeyboardType.ON_F,callBack);
        keyboard.addListener(KeyboardType.ON_K,callBack);

        //向上移动
        keyboard.up();
        //向下移动
        keyboard.down();
    }
}
