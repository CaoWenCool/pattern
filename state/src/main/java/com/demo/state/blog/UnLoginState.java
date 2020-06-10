package com.demo.state.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/2
 * @update: 8:12
 * @version: V1.0
 * @detail:
 **/
public class UnLoginState extends UserState{
    public void favorite() {
        System.out.println("跳转到登陆界面");
        this.appContext.setState(this.appContext.STATE_LOGIN);
        this.appContext.getCurrentState().favorite();

    }
    public void comment(String comment) {
        System.out.println("跳转到登陆界面");
        this.appContext.setState(this.appContext.STATE_LOGIN);
        this.appContext.getCurrentState().comment(comment);
    }
}
