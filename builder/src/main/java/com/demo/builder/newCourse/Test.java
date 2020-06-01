package com.demo.builder.newCourse;

/**
 * @author: Maniac Wen
 * @create: 2020/5/27
 * @update: 8:25
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        CourseBuilder courseBuilder = new CourseBuilder()
                .addHomework("手写计算机结构图")
                .addName("计算机原理")
                .addPPT("准备好PPT课件")
                .addNote("做好笔记")
                .addTeach("讲课");
        System.out.println(courseBuilder.build());
    }
}
