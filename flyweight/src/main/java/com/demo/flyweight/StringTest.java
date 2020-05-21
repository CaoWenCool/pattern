package com.demo.flyweight;

/**
 * @author: Maniac Wen
 * @create: 2020/5/13
 * @update: 7:57
 * @version: V1.0
 * @detail:
 **/
public class StringTest {
    public static void main(String[] args) {
        String s1 = "ab";
        String s2 = "ab";
        String s3 = "a" + "b";
        String s4 = "a" + new String("b");
        String s5 = new String("ab");
        String s6 = s5.intern();
        String s7 = "a";
        String s8 = "b";
        String s9 = s7 + s8;
        System.out.println(s1 == s2);
        System.out.println(s1 == s3);
        System.out.println(s1 == s4);
        System.out.println(s1 == s9);
        System.out.println(s4 == s5);
        System.out.println(s1 == s6);
    }
}
