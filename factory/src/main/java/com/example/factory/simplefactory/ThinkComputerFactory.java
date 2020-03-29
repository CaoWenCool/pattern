package com.example.factory.simplefactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 8:27
 * @version: V1.0
 * @detail:
 **/
public class ThinkComputerFactory implements IComputer{
    @Override
    public void selectCpu() {
        System.out.println("选择酷睿系列的CPU");
    }

    @Override
    public void installSystem() {
        System.out.println("选择 Windows 系列的操作系统");
    }
}
