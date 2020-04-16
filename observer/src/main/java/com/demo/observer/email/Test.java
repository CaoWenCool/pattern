package com.demo.observer.email;

/**
 * @author: Maniac Wen
 * @create: 2020/4/12
 * @update: 20:27
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        WeiBo weiBo = WeiBo.getInstance();
        BoZhu liuyifei = new BoZhu("刘亦菲");
        BoZhu liyifeng = new BoZhu("李易峰");

        weiBo.addObserver(liuyifei);
        weiBo.addObserver(liyifeng);

        Message message = new Message();
        message.setName("张三");
        message.setContent("我很喜欢你们最近的电影");

        weiBo.publish(weiBo,message);
    }
}
