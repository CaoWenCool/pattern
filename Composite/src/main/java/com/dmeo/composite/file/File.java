package com.dmeo.composite.file;

import com.dmeo.composite.file.Directory;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 8:27
 * @version: V1.0
 * @detail:
 **/
public class File extends Directory{
    public File(String name){
        super(name);
    }
    public void show() {
        System.out.println(this.name);
    }
}
