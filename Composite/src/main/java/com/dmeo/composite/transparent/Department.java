package com.dmeo.composite.transparent;

/**
 * @author: Maniac Wen
 * @create: 2020/5/21
 * @update: 9:19
 * @version: V1.0
 * @detail:
 **/
public class Department extends CompanyComponent{
    private String name;
    private int num;
    public Department(String name,int num){
        this.name = name;
        this.num = num;
    }
    @Override
    public String getName(CompanyComponent companyComponent) {
        return this.name;
    }
    @Override
    public int getPeopleNum(CompanyComponent companyComponent) {
        return this.num;
    }
    @Override
    public void print() {
        System.out.println("部门名称:" + name + ",该部门人数：" + num);
    }
}
