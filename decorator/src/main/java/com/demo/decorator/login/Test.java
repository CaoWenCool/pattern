package com.demo.decorator.login;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 21:06
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        loginThirdService loginThirdService = new loginForThirdServiceImpl(new loginServiceImpl());
        ResultMsg resultMsg = loginThirdService.loginForQQ("123123");
        System.out.println(resultMsg.getMsg());
    }
}
