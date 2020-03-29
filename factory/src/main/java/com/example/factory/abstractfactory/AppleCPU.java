package com.example.factory.abstractfactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 11:08
 * @version: V1.0
 * @detail:
 **/
public class AppleCPU implements developmentCPU{
    @Override
    public void developCPU() {
        System.out.println("研发苹果的CPU");
    }
}
