package com.demo.chain.datacheck;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:44
 * @version: V1.0
 * @detail:
 **/
public class AuthHandler extends Handler{
    public void doHandler(User user) {
        //校验角色的权限
        if(!"超级管理员".equals(user.getRole())){
            System.out.println("您不是超级管理员，没有权限操作");
            return;
        }
        System.out.println("获取您的操作列表，允许操作");
    }
}
