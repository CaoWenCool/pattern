package com.dmeo.composite.file;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 8:28
 * @version: V1.0
 * @detail:
 **/
public class Folder extends Directory{
    private List<Directory> dirs;
    private Integer level;
    public Folder(String name,Integer level){
        super(name);
        this.level = level;
        this.name = name;
        this.dirs = new ArrayList<Directory>();
    }
    public void show() {
        System.out.println(this.name);
        for(Directory dir:this.dirs) {
            if (this.level != null) {
                for (int i = 0; i < this.level; i++) {
                    System.out.print("  ");
                }
            }
            dir.show();
        }
    }
    public boolean add(Directory directory){
        return this.dirs.add(directory);
    }
    public boolean remove(Directory directory){
        return this.dirs.remove(directory);
    }
    public Directory get(int index){
        return this.dirs.get(index);
    }
    public void list(){
        for(Directory dir:dirs){
            System.out.println(dir.name);
        }
    }
}
