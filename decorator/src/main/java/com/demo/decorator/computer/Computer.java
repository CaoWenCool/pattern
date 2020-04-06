package com.demo.decorator.computer;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 9:45
 * @version: V1.0
 * @detail:
 **/
public class Computer {
    //获得商品信息
    protected String getMsg(){
        return "电脑";
    }
    //获得商品价格
    public int getPrice(){
        return 3888;
    }
}
