package com.demo.builder.newCourse;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/5/27
 * @update: 8:20
 * @version: V1.0
 * @detail:
 **/
public class CourseBuilder {
    private Course course = new Course();
    public CourseBuilder  addName(String name){
        course.setName(name);
        return this;
    }
    public CourseBuilder  addPPT(String ppt){
        course.setPpt(ppt);
        return this;
    }
    public  CourseBuilder addHomework(String homework){
        course.setHomework(homework);
        return this;
    }
    public CourseBuilder addNote(String note){
        course.setNote(note);
        return this;
    }
    public CourseBuilder addTeach(String teach){
        course.setTeach(teach);
        return this;
    }
    public Course build(){
        return course;
    }

    @Data
    public class Course {
        private String name;
        private String ppt;
        private String homework;
        private String note;
        private String teach;

        @Override
        public String toString() {
            return "Course{" +
                    "name='" + name + '\'' +
                    ", ppt='" + ppt + '\'' +
                    ", homework='" + homework + '\'' +
                    ", note='" + note + '\'' +
                    ", teach='" + teach + '\'' +
                    '}';
        }
    }

}
