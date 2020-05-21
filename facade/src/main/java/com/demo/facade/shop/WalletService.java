package com.demo.facade.shop;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:15
 * @version: V1.0
 * @detail:
 **/
public class WalletService {
    private double money = 200.0;
    //检查余额
    public boolean checkMoney(Goods goods){
        if(money >= goods.getPrice()){
            System.out.println("余额充足，可以进行支付");
            return true;
        }
        System.out.println("余额不足");
        return false;
    }
    public boolean updateMoney(Goods goods){
        System.out.println("修改余额，扣减:" + goods.getPrice() +",剩余：" + (money-goods.getPrice()));
        return true;
    }
}
