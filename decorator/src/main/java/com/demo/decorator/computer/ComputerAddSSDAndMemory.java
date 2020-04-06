package com.demo.decorator.computer;

/**
 * @author: Maniac Wen
 * @create: 2020/3/30
 * @update: 9:51
 * @version: V1.0
 * @detail:
 **/
public class ComputerAddSSDAndMemory extends ComputerAddSSD{
    //选择电脑不仅加了SSD还加了内存条
    @Override
    protected String getMsg() {
        return super.getMsg() + "加了一个16G的内存条";
    }
    //价格又加了500元
    @Override
    public int getPrice() {
        return super.getPrice() + 500;
    }
}
