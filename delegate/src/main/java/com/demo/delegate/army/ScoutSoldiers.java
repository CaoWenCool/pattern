package com.demo.delegate.army;

/**
 * @author: Maniac Wen
 * @create: 2020/4/28
 * @update: 7:04
 * @version: V1.0
 * @detail:
 **/
public class ScoutSoldiers implements ISoldiers{
    protected String weapon = "小米加步枪";
    protected String name = "侦察兵";
    protected String work = "勘探敌情";

    public void doing(String task) {
        System.out.println("我是：" + name + ",我的武器是：" +  weapon + ",我的职责是：" + work);
    }
}
