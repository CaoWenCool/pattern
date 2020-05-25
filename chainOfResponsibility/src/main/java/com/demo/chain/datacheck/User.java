package com.demo.chain.datacheck;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/5/23
 * @update: 7:27
 * @version: V1.0
 * @detail:
 **/
@Data
public class User {
    public User(String name,String pwd){
        this.name = name;
        this.pwd = pwd;
    }
    //用户名
    private String name;
    //密码
    private String pwd;
    //角色
    private String role;
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
