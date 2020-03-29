package com.example.factory.methodfactory;

import com.example.factory.simplefactory.IComputer;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 9:23
 * @version: V1.0
 * @detail:
 **/
public class HuaWeiComputer implements IComputer {
    @Override
    public void selectCpu() {
        System.out.println("选择麒麟海思的CPU");
    }

    @Override
    public void installSystem() {
        System.out.println("选择鸿蒙操作系统");
    }
}
