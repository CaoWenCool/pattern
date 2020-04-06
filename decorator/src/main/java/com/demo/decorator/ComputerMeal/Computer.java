package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:02
 * @version: V1.0
 * @detail:
 **/
public abstract class Computer {
    //获得商品信息
    protected abstract String getMsg();
    //获得商品价格
    protected abstract int getPrice();
}

