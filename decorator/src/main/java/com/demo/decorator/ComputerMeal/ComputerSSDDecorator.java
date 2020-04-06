package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:15
 * @version: V1.0
 * @detail:
 **/
public class ComputerSSDDecorator extends ComputerMealDecorator{
    public ComputerSSDDecorator(Computer computer) {
        super(computer);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "1个SSD固态硬盘";
    }

    @Override
    protected int getPrice() {
        return super.getPrice() + 1000;
    }
}
