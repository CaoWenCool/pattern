package com.demo.decorator.login;

import javax.xml.transform.Result;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 20:56
 * @version: V1.0
 * @detail:
 **/
public class loginForThirdServiceImpl implements loginThirdService{
    private loginService loginService;

    public loginForThirdServiceImpl(loginService loginService){
        this.loginService = loginService;
    }

    public ResultMsg regist(String name,String psw){
        return loginService.regist(name,psw);
    }

    public ResultMsg login(String name,String psw){
        return loginService.login(name,psw);
    }

    public ResultMsg loginForQQ(String id) {
        System.out.println("ID：" + "的用户开始进行登录操作！");
        return new ResultMsg(200,"QQ登陆成功",new User());
    }

    public ResultMsg loginForWeChat(String id) {
        return new ResultMsg(200,"微信登陆成功",new User());
    }

    public ResultMsg loginForPhone(String telphone, String code) {
        return new ResultMsg(200,"电话登陆成功",new User());
    }

    public ResultMsg loginForToken(String token) {
        return new ResultMsg(200,"TOKEN 登陆成功",new User());
    }
}
