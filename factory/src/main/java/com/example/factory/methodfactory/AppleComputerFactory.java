package com.example.factory.methodfactory;

import com.example.factory.simplefactory.IComputer;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 9:21
 * @version: V1.0
 * @detail:
 **/
public class AppleComputerFactory implements IComputerFactory{
    @Override
    public IComputer create() {
        return new AppleComputer();
    }
}
