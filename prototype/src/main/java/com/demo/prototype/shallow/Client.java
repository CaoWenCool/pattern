package com.demo.prototype.shallow;

/**
 * @author: Maniac Wen
 * @create: 2020/4/25
 * @update: 9:31
 * @version: V1.0
 * @detail:
 **/
public class Client {
    private Prototype prototype;
    public Client(Prototype prototype){
        this.prototype = prototype;
    }

    public Prototype clone(Prototype prototype){
        return prototype.clone();
    }
}
