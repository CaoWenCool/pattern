package com.demo.prototype.shallow;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Maniac Wen
 * @create: 2020/4/25
 * @update: 9:32
 * @version: V1.0
 * @detail:
 **/
public class Test {

    public static void main(String[] args) {
        //创建一个需要克隆的对象
        CloneA cloneA = new CloneA();
        cloneA.setDna("XY");
        cloneA.setName("张三");
        cloneA.setId(1);
        List tag = new ArrayList<String>();
        tag.add("学生");
        tag.add("性别男");
        tag.add("爱好女");
        cloneA.setTag(tag);
        System.out.println(cloneA);
        //创建Client 对象，开始准备进行克隆
        Client client = new Client(cloneA);
        CloneA cloneB = (CloneA) client.clone(cloneA);
        System.out.println(cloneB);
        System.out.println("--------------------------------");
        System.out.println("克隆对象中的引用类型地址：" + cloneB.getTag());
        System.out.println("原对象中的引用类型地址：" + cloneA.getTag());
        System.out.println("对象地址的比较：" + (cloneA.getTag() == cloneB.getTag()));
    }
}
