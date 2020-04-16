package com.demo.observer.keyboard;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
/**
 * @author: Maniac Wen
 * @create: 2020/4/16
 * @update: 7:23
 * @version: V1.0
 * @detail: 时间监听器
 **/
public class EventListener {
    protected Map<String,Event> events = new HashMap<String,Event>();
    //事件名称和一个目标对象来触发
    public void addListener(String eventType,Object target) throws NoSuchMethodException {
        this.addListener(eventType,target,target.getClass().getMethod("on" +
            toUpperFirstCase(eventType),Event.class));
    }

    public void addListener(String eventType, Object target, Method callBack){
        events.put(eventType,new Event(target,callBack));
    }
    //根据事件触发
    private void trigger(Event event) throws InvocationTargetException, IllegalAccessException {
        event.setSource(this);
        event.setTime(System.currentTimeMillis());
        if(event.getCallBack() != null){
            //利用反射调用他的回调函数
            event.getCallBack().invoke(event.getTarget(),event);
        }
    }
    //根据名称触发
    protected void trigger(String trigger) throws InvocationTargetException, IllegalAccessException {
        if(!this.events.containsKey(trigger)){
            return;
        }
        trigger(this.events.get(trigger).setTrigger(trigger));
    }
    //将字符串首字母大写
    private String toUpperFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] -= 32;
        return String.valueOf(chars);
    }
}
