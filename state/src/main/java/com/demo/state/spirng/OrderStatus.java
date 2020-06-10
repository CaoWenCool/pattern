package com.demo.state.spirng;

/**
 * @author: Maniac Wen
 * @create: 2020/6/3
 * @update: 8:52
 * @version: V1.0
 * @detail:
 **/
public enum OrderStatus {
    // 待支付，待发货，待收货，订单结束
    WAIT_PAYMENT, WAIT_DELIVER, WAIT_RECEIVE, FINISH;
}
