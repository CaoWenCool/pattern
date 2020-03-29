package com.example.factory.methodfactory;

import com.example.factory.simplefactory.IComputer;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 9:22
 * @version: V1.0
 * @detail:
 **/
public class AppleComputer implements IComputer {
    @Override
    public void selectCpu() {
        System.out.println("选择苹果芯片");
    }
    @Override
    public void installSystem() {
        System.out.println("安装 苹果操作系统");
    }
}
