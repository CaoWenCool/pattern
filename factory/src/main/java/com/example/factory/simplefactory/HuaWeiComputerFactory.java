package com.example.factory.simplefactory;


/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 8:25
 * @version: V1.0
 * @detail:
 **/
public class HuaWeiComputerFactory implements IComputer{
    @Override
    public void selectCpu() {
        System.out.println("选择麒麟海思的CPU");
    }

    @Override
    public void installSystem() {
        System.out.println("选择鸿蒙操作系统");
    }
}
