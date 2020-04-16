package com.demo.observer.keyboard;

/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:38
 * @version: V1.0
 * @detail: 事件类型
 **/
public interface KeyboardType {
    //E，向上移动
    String ON_E = "up";
    //D,向下移动
    String ON_D = "down";
    //S，向左移动
    String ON_S = "left";
    //F，向右移动
    String ON_F = "right";
    //K，开始发起攻击
    String ON_K = "attack";
}
