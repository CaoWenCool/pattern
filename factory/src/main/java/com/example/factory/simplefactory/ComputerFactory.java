package com.example.factory.simplefactory;

/**
 * @author: 曹文
 * @create: 2020/3/19
 * @update: 8:36
 * @version: V1.0
 * @detail:
 **/
public class ComputerFactory {
//
//    public IComputer create(String name){
//        if("Apple".equals(name)){
//            return new AppleComputerFactory();
//        }else if("HuaWei".equals(name)){
//            return new HuaWeiComputerFactory();
//        }else if("ThinkPad".equals(name)){
//            return new ThinkComputerFactory();
//        }else{
//            return null;
//        }
//    }
//
//    public IComputer create1(String className){
//        if(!(null == className || "".equals(className))){
//            try {
//                return (IComputer) Class.forName(className).newInstance();
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

    public IComputer create2(Class<? extends IComputer> clazz){
        if(null != clazz){
            try {
                return clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
