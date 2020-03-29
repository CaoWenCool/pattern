package com.example.factory.abstractfactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 11:12
 * @version: V1.0
 * @detail:
 **/
public class AbstractFactoryTest {

    public static void main(String[] args) {
        AppleFactory factory = new AppleFactory();
        factory.createCPU().developCPU();
        factory.createParts().importParts();
    }
}
