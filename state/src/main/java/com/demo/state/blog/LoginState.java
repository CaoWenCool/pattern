package com.demo.state.blog;

/**
 * @author: Maniac Wen
 * @create: 2020/6/2
 * @update: 8:10
 * @version: V1.0
 * @detail:
 **/
public class LoginState extends UserState{
    public void favorite() {
        System.out.println("收藏成功！！！");
    }

    public void comment(String comment) {
        System.out.println("评论：" + comment);
    }
}
