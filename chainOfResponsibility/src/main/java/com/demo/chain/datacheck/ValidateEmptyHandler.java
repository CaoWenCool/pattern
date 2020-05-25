package com.demo.chain.datacheck;

import org.springframework.util.StringUtils;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:40
 * @version: V1.0
 * @detail:
 **/
public class ValidateEmptyHandler extends Handler{
    public void doHandler(User user) {
        if(StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPwd())){
            System.out.println("账号或者密码为空");
            return;
        }
        System.out.println("为空校验通过，继续执行校验程序");
        chainHandler.doHandler(user);
    }
}
