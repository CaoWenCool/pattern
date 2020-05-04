package com.demo.delegate.army;

import java.util.Map;

import java.util.HashMap;

/**
 * @author: Maniac Wen
 * @create: 2020/4/28
 * @update: 7:13
 * @version: V1.0
 * @detail:
 **/
public class LeaderSoldiers implements ISoldiers{
    private Map<String,ISoldiers> emploeeMap = new HashMap<String,ISoldiers>();
    public LeaderSoldiers(){
        emploeeMap.put("查看敌军守备情况",new ScoutSoldiers());
        emploeeMap.put("刺杀地方首脑",new SpecialSoldiers());
    }
    public void doing(String task) {
        if (!emploeeMap.containsKey(task)){
            System.out.println("报告！这个任务：" + task + "超出我的能力范围，请求支援！");
            return;
        }
        emploeeMap.get(task).doing(task);
    }
}
