package com.demo.delegate.army;

/**
 * @author: Maniac Wen
 * @create: 2020/4/28
 * @update: 7:10
 * @version: V1.0
 * @detail:
 **/
public class SpecialSoldiers implements ISoldiers{
    protected String weapon = "狙击步枪";
    protected String name = "特种兵";
    protected String work = "枪毙敌方首脑";

    public void doing(String task) {
        System.out.println("我是：" + name + ",我的武器是：" +  weapon + ",我的职责是：" + work);
    }
}
