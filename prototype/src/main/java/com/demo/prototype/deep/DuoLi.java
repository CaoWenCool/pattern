package com.demo.prototype.deep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: Maniac Wen
 * @create: 2020/4/25
 * @update: 10:04
 * @version: V1.0
 * @detail:
 **/
public class DuoLi extends Sheep implements Serializable,Cloneable{
    public SheepTag sheepTag;
    public DuoLi(){
        this.DNA = "xx";
        this.weight = 100;
        this.height = 100;
        this.sheepTag = new SheepTag();
        this.sheepTag.name = "多利";
        List<String> tag = new ArrayList<String>();
        tag.add("编号001");
        tag.add("世界上第一只克隆羊");
        this.sheepTag.tag = tag;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return this.deepClone();
    }

    public Object deepClone(){

        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            DuoLi copy = (DuoLi) objectInputStream.readObject();
            copy.DNA = "xx";
            return copy;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 浅克隆
     * @param propoty
     * @return
     */
    public DuoLi shallowClone(DuoLi propoty){
        DuoLi duoLi1 = new DuoLi();
        duoLi1.DNA = propoty.DNA;
        duoLi1.height = propoty.height;
        duoLi1.weight = propoty.weight;

        duoLi1.sheepTag = propoty.sheepTag;
        return duoLi1;
    }
}
