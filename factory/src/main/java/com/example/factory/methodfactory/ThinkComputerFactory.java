package com.example.factory.methodfactory;

import com.example.factory.simplefactory.IComputer;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 9:35
 * @version: V1.0
 * @detail:
 **/
public class ThinkComputerFactory implements IComputerFactory{
    @Override
    public IComputer create() {
        return new ThinkComputer();
    }
}
