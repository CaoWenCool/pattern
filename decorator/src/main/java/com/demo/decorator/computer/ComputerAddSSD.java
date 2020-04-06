package com.demo.decorator.computer;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 9:46
 * @version: V1.0
 * @detail:
 **/
public class ComputerAddSSD extends Computer{
    //商品信息中增加了SSD固态
    @Override
    protected String getMsg() {
        return super.getMsg() + "SSD固态硬盘";
    }
    //整体价格需要加上1000元
    @Override
    public int getPrice() {
        return super.getPrice() + 1000;
    }
}
