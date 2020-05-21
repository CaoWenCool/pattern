package com.demo.facade.shop;

/**
 * @author: Maniac Wen
 * @create: 2020/5/6
 * @update: 9:21
 * @version: V1.0
 * @detail:
 **/
public class ShopFacade {
    private PayService payService = new PayService();
    private WalletService walletService = new WalletService();
    private ShippingService shippingService = new ShippingService();
    public boolean exchange(Goods goods){
        //检查余额
        if(!walletService.checkMoney(goods)){
            return false;
        }
        //进行支付
        if(!payService.pay(goods)){
            return false;
        }
        //扣减余额
        if(!walletService.updateMoney(goods)){
            return false;
        }
        //物流运送
        if(!shippingService.delivery(goods)){
            return false;
        }
        return true;
    }
}
