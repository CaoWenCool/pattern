package com.demo.flyweight.house;

/**
 * @author: Maniac Wen
 * @create: 2020/5/12
 * @update: 9:05
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        IHouse house = HouseFactory.queryHouse("A社区","两室一厅");
        house.showHouse();
        IHouse house1 = HouseFactory.queryHouse("A社区","两室一厅");
        house1.showHouse();
        IHouse house2 = HouseFactory.queryHouse("A社区","两室一厅");
        house2.showHouse();
        IHouse house3 = HouseFactory.queryHouse("A社区","两室一厅");
        house3.showHouse();
    }
}
