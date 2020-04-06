package com.demo.decorator.login;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 20:53
 * @version: V1.0
 * @detail:
 **/
public interface loginThirdService extends loginService{
    //QQ登陆
    ResultMsg loginForQQ(String id);
    //微信登陆
    ResultMsg loginForWeChat(String id);
    //手机号登陆
    ResultMsg loginForPhone(String telphone,String code);
    //token 登陆
    ResultMsg loginForToken(String token);
}
