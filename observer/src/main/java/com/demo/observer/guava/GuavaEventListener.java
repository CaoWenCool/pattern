package com.demo.observer.guava;

import com.google.common.eventbus.Subscribe;

/**
 * @author: Maniac Wen
 * @create: 2020/4/17
 * @update: 7:29
 * @version: V1.0
 * @detail:
 **/
public class GuavaEventListener {
    @Subscribe
    public void subscribe(String str){
        System.out.println("开始执行 subscribe 方法，传入的参数是：" + str);
    }
}
