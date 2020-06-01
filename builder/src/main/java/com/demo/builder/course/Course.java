package com.demo.builder.course;

import lombok.Data;

/**
 * @author: Maniac Wen
 * @create: 2020/5/27
 * @update: 8:05
 * @version: V1.0
 * @detail:
 **/
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
