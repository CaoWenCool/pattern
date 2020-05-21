package com.demo.facade.shop;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:09
 * @version: V1.0
 * @detail:
 **/
@Data
public class Goods {
    private String name;
    private double price;
    public Goods(String name,double price){
        this.name = name;
        this.price = price;
    }
}
