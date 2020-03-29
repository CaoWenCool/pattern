package com.example.factory.abstractfactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 11:09
 * @version: V1.0
 * @detail:
 **/
public class AppleParts implements ImportParts{
    @Override
    public void importParts() {
        System.out.println("苹果工厂开始进口零件");
    }
}
