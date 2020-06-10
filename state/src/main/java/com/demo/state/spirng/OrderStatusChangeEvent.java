package com.demo.state.spirng;

/**
 * @author: Maniac Wen
 * @create: 2020/6/3
 * @update: 8:54
 * @version: V1.0
 * @detail:
 **/
public enum OrderStatusChangeEvent {
    //支付
    PAYED,
    //发货
    DELIVERY,
    //确认收货
    RECEIVED;
}
