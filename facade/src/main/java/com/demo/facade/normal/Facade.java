package com.demo.facade.normal;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 8:52
 * @version: V1.0
 * @detail:
 **/
public class Facade {
    private SubA a = new SubA();
    private SubB b = new SubB();
    private SubC c = new SubC();
    //对外的接口
    public  void doA(){
        a.doA();
    }
    public  void doB(){
        b.doB();
    }
    public  void doC(){
        c.doC();
    }
}
