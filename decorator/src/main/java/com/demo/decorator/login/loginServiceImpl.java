package com.demo.decorator.login;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 20:51
 * @version: V1.0
 * @detail:
 **/
public class loginServiceImpl implements loginService{
    public ResultMsg regist(String name, String psw) {
        return new ResultMsg(200,"注册成功",new User());
    }

    public ResultMsg login(String name, String psw) {
        return null;
    }
}
