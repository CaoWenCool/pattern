package com.demo.decorator.computer;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 9:53
 * @version: V1.0
 * @detail:
 **/
public class ComputerTest {
    public static void main(String[] args) {
        Computer computer = new Computer();
        System.out.println(computer.getMsg() + ",价格：" + computer.getPrice());
        ComputerAddSSD computerAddSSD = new ComputerAddSSD();
        System.out.println(computerAddSSD.getMsg() + ",价格：" + computerAddSSD.getPrice());
        ComputerAddSSDAndMemory computerAddSSDAndMemory = new ComputerAddSSDAndMemory();
        System.out.println(computerAddSSDAndMemory.getMsg() + ",价格：" + computerAddSSDAndMemory.getPrice());
    }
}
