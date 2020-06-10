package com.demo.state.spirng;

import java.util.Map;
/**
 * @author: Maniac Wen
 * @create: 2020/6/8
 * @update: 8:44
 * @version: V1.0
 * @detail:
 **/
public interface IOrderService {
    // 创建订单
    Order create();
//    发起支付
    Order pay(int id);
//    订单发货
    Order deliver(int id);
//    订单收货
    Order receive(int id);
//    获取所有订单信息
    Map<Integer,Order> getOrders();
}
