package com.demo.flyweight.house;

import sun.security.krb5.internal.Ticket;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
/**
 * @author: Maniac Wen
 * @create: 2020/5/12
 * @update: 9:04
 * @version: V1.0
 * @detail:
 **/
public class HouseFactory {
    private static Map<String,IHouse> housePool = new ConcurrentHashMap<String,IHouse>();
    public static IHouse queryHouse(String address,String pattern){
        String key = address + ":" + pattern;
        if(HouseFactory.housePool.containsKey(key)){
            System.out.println("使用缓存对象" + key);
            return HouseFactory.housePool.get(key);
        }
        System.out.println("首次查询，创建对象：" + key);
        IHouse iHouse = new House(address,pattern);
        HouseFactory.housePool.put(key,iHouse);
        return iHouse;
    }
}
