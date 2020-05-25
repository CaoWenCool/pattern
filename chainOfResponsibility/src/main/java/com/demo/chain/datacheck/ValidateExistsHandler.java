package com.demo.chain.datacheck;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:42
 * @version: V1.0
 * @detail:
 **/
public class ValidateExistsHandler extends Handler{
    public void doHandler(User user) {
        //主要校验用户是否存在,校验存在，并且添加用户的角色信息
        user.setRole("系统管理员");
        chainHandler.doHandler(user);
        System.out.println("用户存在，并且添加了用户角色信息，登陆成功");
    }
}
