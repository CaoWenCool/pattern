package com.demo.facade.shop;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:25
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        Goods goods = new Goods("大力丸",100.0);
        ShopFacade shopFacade = new ShopFacade();
        shopFacade.exchange(goods);
    }
}
