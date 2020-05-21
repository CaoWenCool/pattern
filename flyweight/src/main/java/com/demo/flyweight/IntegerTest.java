package com.demo.flyweight;

/**
 * @author: Maniac Wen
 * @create: 2020/5/13
 * @update: 8:16
 * @version: V1.0
 * @detail:
 **/
public class IntegerTest {
    public static void main(String[] args) {
        Integer a = Integer.valueOf(100);
        Integer b = 100;

        Integer c = Integer.valueOf(1000);
        Integer d = 1000;

        System.out.println("a == b:" + (a == b));
        System.out.println("d == d:" + (c == d));
    }
}
