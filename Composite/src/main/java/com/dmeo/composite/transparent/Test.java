package com.dmeo.composite.transparent;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 7:52
 * @version: V1.0
 * @detail:
 **/
public class Test {
    public static void main(String[] args) {
        CompanyComponent xiaoshou = new Department("营销中心",14);
        CompanyComponent yanfa = new Department("研发中心",13);
        CompanyComponent company = new DepartmentPackage("***有限公司，一级部门",1);
        company.addDepartment(xiaoshou);
        company.addDepartment(yanfa);
        CompanyComponent renshi = new Department("人事部",9);
        CompanyComponent xingzheng = new Department("行政部",8);
        CompanyComponent houqin = new Department("后勤部",7);
        CompanyComponent company1 = new DepartmentPackage("***有限公司，二级部门",2);
        company1.addDepartment(renshi);
        company1.addDepartment(xingzheng);
        company1.addDepartment(houqin);
        company.print();
        company1.print();
    }
}
