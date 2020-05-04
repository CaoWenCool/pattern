package com.demo.delegate.army;

/**
 * @author: Maniac Wen
 * @create: 2020/4/28
 * @update: 7:19
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        new BossSoldiers().command("查看敌军守备情况",new LeaderSoldiers());
        new BossSoldiers().command("刺杀地方首脑",new LeaderSoldiers());
        new BossSoldiers().command("进行火力压制",new LeaderSoldiers());
    }
}
