package com.demo.delegate.army;

/**
 * @author: Maniac Wen
 * @create: 2020/4/28
 * @update: 7:17
 * @version: V1.0
 * @detail:
 **/
public class BossSoldiers {
    public void command(String task,LeaderSoldiers leaderSoldiers){
        leaderSoldiers.doing(task);
    }
}
