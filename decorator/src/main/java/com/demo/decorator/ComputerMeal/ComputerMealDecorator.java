package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:06
 * @version: V1.0
 * @detail:
 **/
public class ComputerMealDecorator extends Computer{
    private Computer computer;
    public ComputerMealDecorator(Computer computer){
        this.computer  = computer;
    }
    protected String getMsg() {
        return this.computer.getMsg();
    }

    protected int getPrice() {
        return this.computer.getPrice();
    }
}
