package com.demo.prototype.deep;

/**
 * @author: Maniac Wen
 * @create: 2020/4/25
 * @update: 10:23
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        DuoLi duoLi = new DuoLi();
        try {
            DuoLi clone = (DuoLi) duoLi.clone();
            System.out.println("深克隆：" + (duoLi.sheepTag == clone.sheepTag));
            System.out.println("深克隆：" + (duoLi.sheepTag.tag == clone.sheepTag.tag));
        } catch (Exception e) {
            e.printStackTrace();
        }
        DuoLi duoLi1 = new DuoLi();
        DuoLi shallow = duoLi1.shallowClone(duoLi1);
        System.out.println("浅克隆：" + (duoLi1.sheepTag == shallow.sheepTag));
    }
}
