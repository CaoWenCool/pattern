package com.demo.iterator.course;

/**
 * @author: Maniac Wen
 * @create: 2020/6/12
 * @update: 8:36
 * @version: V1.0
 * @detail:
 **/
public class Client {
    public static void main(String[] args) {
        Course java = new Course("Java 课程");
        Course C =  new Course("C语言课程");
        Course python = new Course("python 课程");
        Course go = new Course("go 语言课程");

        CourseAggregate courseAggregate = new CourseAggregateImpl();
        courseAggregate.add(java);
        courseAggregate.add(C);
        courseAggregate.add(python);
        courseAggregate.add(go);

        System.out.println("----------- 课程列表 --------------");
        printCourse(courseAggregate);
        courseAggregate.remove(C);
        System.out.println("--------删除之后的课程列表 ---------------");
        printCourse(courseAggregate);
    }

    private static void printCourse(CourseAggregate courseAggregate){
        Iterator<Course> iterator = courseAggregate.iterator();
        while (iterator.hasNext()){
            Course course = iterator.next();
            System.out.println(course.getName());
        }
    }
}
