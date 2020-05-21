package com.demo.facade.shop;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:12
 * @version: V1.0
 * @detail:
 **/
public class PayService {
    public boolean pay(Goods goods){
        //支付成功，增加用户积分
        System.out.println("支付成功，商品名称：" + goods.getName() + ",商品价格：" + goods.getPrice() );
        return true;
    }
}
