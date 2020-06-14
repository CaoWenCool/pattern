package com.demo.iterator.course;

/**
 * @author: Maniac Wen
 * @create: 2020/6/12
 * @update: 8:31
 * @version: V1.0
 * @detail:
 **/
public interface CourseAggregate {

    void add(Course course);

    void remove(Course course);

    Iterator<Course> iterator();
}
