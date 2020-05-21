package com.demo.flyweight.house;

import lombok.Data;

import java.util.Random;

/**
 * @author: Maniac Wen
 * @create: 2020/5/12
 * @update: 8:57
 * @version: V1.0
 * @detail:
 **/
@Data
public class House implements IHouse{
    private String address;
    private String  pattern;
    private int price;

    public House(String address,String pattern){
        this.address = address;
        this.pattern = pattern;
    }
    public void showHouse() {
        this.price = new Random().nextInt(500000000);
        System.out.println(String.format("地址：%s，格局:%s，格：%s:元，",this.address,this.pattern,this.price));
    }
}
