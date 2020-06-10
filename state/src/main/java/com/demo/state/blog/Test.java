package com.demo.state.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/3
 * @update: 8:41
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        AppContext appContext = new AppContext();
        appContext.favorite();
        appContext.comment("这咋写得这么好呢？");
    }
}
