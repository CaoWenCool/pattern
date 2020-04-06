package com.demo.decorator.ComputerMeal;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 10:18
 * @version: V1.0
 * @detail:
 **/
public class ComputerTest {
    public static void main(String[] args) {
        Computer computer;
        //我要买电脑
        computer = new ComputerMeal();
        //机械硬盘太差了，我要SSD固态
        computer = new ComputerSSDDecorator(computer);
        //内存太小了，我要加内存条
        computer = new ComputerMealMemory(computer);
        //内存还是太小了，我再加跟内存条
        computer = new ComputerMealMemory(computer);
        System.out.println(computer.getMsg() + ",价格为：" + computer.getPrice());
    }
}
