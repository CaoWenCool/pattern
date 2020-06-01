package com.demo.builder.course;

/**
 * @author: Maniac Wen
 * @create: 2020/5/27
 * @update: 8:07
 * @version: V1.0
 * @detail:
 **/
public class CourseBuilder {
    private Course course = new Course();
    public void  addName(String name){
        course.setName(name);
    }
    public void  addPPT(String ppt){
        course.setPpt(ppt);
    }
    public  void addHomework(String homework){
        course.setHomework(homework);
    }
    public void addNote(String note){
        course.setNote(note);
    }
    public void addTeach(String teach){
        course.setTeach(teach);
    }
    public Course build(){
        return course;
    }
}
