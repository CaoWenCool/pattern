package com.demo.observer.guava;

import com.google.common.eventbus.EventBus;

/**
 * @author: Maniac Wen
 * @create: 2020/4/17
 * @update: 7:36
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        EventBus eventBus = new EventBus();
        GuavaEventListener guavaEventListener = new GuavaEventListener();
        eventBus.register(guavaEventListener);
        eventBus.post("Hello");
    }
}
