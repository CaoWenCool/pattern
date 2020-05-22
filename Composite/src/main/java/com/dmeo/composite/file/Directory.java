package com.dmeo.composite.file;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 8:26
 * @version: V1.0
 * @detail:
 **/
public abstract class Directory {
    protected String name;
    public Directory(String name){
        this.name = name;
    }
    public abstract void show();
}
