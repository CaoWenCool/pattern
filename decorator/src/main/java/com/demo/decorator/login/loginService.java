package com.demo.decorator.login;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 20:48
 * @version: V1.0
 * @detail:
 **/
public interface loginService {
    /**
     * 注册接口
     * @param name
     * @param psw
     * @return
     */
    ResultMsg regist(String name,String psw);

    /**
     * 登陆接口
     * @param name
     * @param psw
     * @return
     */
    ResultMsg login(String name,String psw);
}
