package com.demo.observer.keyboard;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:12
 * @version: V1.0
 * @detail:
 **/
@Data
public class Event {
    //事件源
    private Object source;
    //通知对象
    private Object target;
    //回调
    private Method callBack;
    //事件名称
    private String trigger;
    //触发时间
    private long time;
    public Event(Object target,Method callBack){
        this.callBack = callBack;
        this.target = target;
    }
    public Event setSource(Object source){
        this.source  = source;
        return this;
    }
    public Event setTime(long time){
        this.time = time;
        return this;
    }
    public Event setTrigger(String trigger){
        this.trigger = trigger;
        return this;
    }
    @Override
    public String toString (){
        return "Event{" + "\n" +
                "\tsource" + source.getClass() + ",\n" +
                "\ttarget" + target.getClass() + ",\n" +
                "\tcallBack" + callBack.getClass() + ",\n" +
                "\ttrigger" + trigger.getClass() + ",\n" +
                "\ttime" + time + ",\n"
                ;
    }
}
