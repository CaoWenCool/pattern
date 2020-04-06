package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:17
 * @version: V1.0
 * @detail:
 **/
public class ComputerMealMemory extends ComputerMealDecorator{

    public ComputerMealMemory(Computer computer) {
        super(computer);
    }

    @Override
    protected String getMsg() {
        return super.getMsg() + "1个16G的内存条";
    }
    @Override
    protected int getPrice() {
        return super.getPrice() + 500;
    }
}
