package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:03
 * @version: V1.0
 * @detail:
 **/
public class ComputerMeal extends Computer{
    protected String getMsg() {
        return "电脑";
    }
    protected int getPrice() {
        return 3888;
    }
}
