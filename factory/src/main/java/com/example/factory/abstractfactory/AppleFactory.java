package com.example.factory.abstractfactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 11:10
 * @version: V1.0
 * @detail:
 **/
public class AppleFactory implements ComputerFactory{
    @Override
    public developmentCPU createCPU() {
        return new AppleCPU();
    }

    @Override
    public ImportParts createParts() {
        return new AppleParts();
    }
}
