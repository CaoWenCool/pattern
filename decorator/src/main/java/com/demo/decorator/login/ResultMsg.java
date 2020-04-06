package com.demo.decorator.login;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/4/6
 * @update: 20:46
 * @version: V1.0
 * @detail:
 **/
@Data
public class ResultMsg {
    private int code;
    private String msg;
    private Object data;
    public ResultMsg(int code,String msg,Object data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
