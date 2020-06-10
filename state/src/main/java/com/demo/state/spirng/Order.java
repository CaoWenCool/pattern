package com.demo.state.spirng;

/**
 * @author: Maniac Wen
 * @create: 2020/6/3
 * @update: 8:50
 * @version: V1.0
 * @detail:
 **/
public class Order {
    private int id;
    private OrderStatus states;
    public  void setStates(OrderStatus states){
        this.states = states;
    }
    public OrderStatus getStates() {
        return states;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", states=" + states +
                '}';
    }
}
