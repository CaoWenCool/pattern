package com.demo.iterator.course;

import java.util.ArrayList;
import java.util.List;
/**
 * @author: Maniac Wen
 * @create: 2020/6/12
 * @update: 8:34
 * @version: V1.0
 * @detail:
 **/
public class CourseAggregateImpl implements CourseAggregate{
    private List courseList;

    public CourseAggregateImpl() {
        this.courseList = new ArrayList();
    }

    public void add(Course course) {
        courseList.add(course);
    }

    public void remove(Course course) {
        courseList.remove(course);
    }

    public Iterator<Course> iterator() {
        return new IteratorImpl<Course>(courseList);
    }
}
