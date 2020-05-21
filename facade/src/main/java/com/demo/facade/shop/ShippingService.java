package com.demo.facade.shop;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:15
 * @version: V1.0
 * @detail:
 **/
public class ShippingService {
    public boolean delivery(Goods goods){
        System.out.println("您的：" + goods.getName() + "正在快马加鞭的赶来");
        return true;
    }
}
