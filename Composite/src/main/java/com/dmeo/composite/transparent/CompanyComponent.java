package com.dmeo.composite.transparent;

/**
 * @author: Maniac Wen
 * @create: 2020/5/21
 * @update: 9:09
 * @version: V1.0
 * @detail:
 **/
public abstract class CompanyComponent {
    public String getName(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持获取名称");
    }
    public int getPeopleNum(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持获取人员数量");
    }
    public void addDepartment(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持添加操作");
    }
    public void removeDepartment(CompanyComponent companyComponent){
        throw new UnsupportedOperationException("不支持移除操作");
    }
    public void print(){
        throw new UnsupportedOperationException("不支持打印");
    }
}
