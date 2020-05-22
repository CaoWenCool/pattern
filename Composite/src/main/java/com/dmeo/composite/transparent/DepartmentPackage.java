package com.dmeo.composite.transparent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Maniac Wen
 * @create: 2020/5/22
 * @update: 7:42
 * @version: V1.0
 * @detail:
 **/
public class DepartmentPackage extends CompanyComponent{
    private List<CompanyComponent> companyComponents = new ArrayList<CompanyComponent>();
    private String name;
    private Integer level;
    public DepartmentPackage(String name,Integer level){
        this.name = name;
        this.level = level;
    }
    @Override
    public void addDepartment(CompanyComponent companyComponent) {
        companyComponents.add(companyComponent);
    }
    @Override
    public String getName(CompanyComponent companyComponent) {
        return this.name;
    }
    @Override
    public void removeDepartment(CompanyComponent companyComponent) {
        companyComponents.remove(companyComponent);
    }
    @Override
    public void print() {
       System.out.println(this.name);
       for(CompanyComponent companyComponent:companyComponents){
            if(this.level != null){
                for(int i=0;i<level;i++){
                    System.out.print("  ");
                }
            }
           companyComponent.print();
       }
    }
}
