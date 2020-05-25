package com.demo.chain.datacheck;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:48
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        UserService userService = new UserService();
        userService.login("张三","123456");
    }
}
