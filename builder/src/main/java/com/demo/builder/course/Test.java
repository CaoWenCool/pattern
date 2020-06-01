package com.demo.builder.course;

/**
 * @author: Maniac Wen
 * @create: 2020/5/27
 * @update: 8:10
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        CourseBuilder courseBuilder = new CourseBuilder();
        courseBuilder.addName("计算机原理");
        courseBuilder.addHomework("手写计算机结构图");
        courseBuilder.addNote("做好笔记");
        courseBuilder.addPPT("准备好PPT课件");
        courseBuilder.addTeach("讲课");
        System.out.println(courseBuilder.build());
    }
}
