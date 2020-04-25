package com.demo.prototype.shallow;

import java.util.List;

/**
 * @author: Maniac Wen
 * @create: 2020/4/23
 * @update: 7:54
 * @version: V1.0
 * @detail: 创建具体的克隆对象A
 **/
public class CloneA implements Prototype{
    private long id;
    private String name;
    private String dna;
    List<String> tag;

    public Prototype clone() {
        CloneA cloneA = new CloneA();
        cloneA.setId(this.id);
        cloneA.setDna(this.dna);
        cloneA.setName(this.name);
        cloneA.setTag(this.tag);
        return cloneA;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDna() {
        return dna;
    }
    public void setDna(String dna) {
        this.dna = dna;
    }
    public List<String> getTag() {
        return tag;
    }
    public void setTag(List<String> tag) {
        this.tag = tag;
    }
}
