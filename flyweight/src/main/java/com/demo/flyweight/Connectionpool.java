package com.demo.flyweight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Vector;

/**
 * @author: Maniac Wen
 * @create: 2020/5/13
 * @update: 7:47
 * @version: V1.0
 * @detail:
 **/
public class Connectionpool {
    private Vector<Connection> pool;

    private String url = "jdbc:mysql://localhost:3306/test";
    private String username = "root";
    private String password = "root";
    private String driverClassName = "com.mysql.jdbc.Driver";
    private int poolSize = 100;
    public Connectionpool(){
        pool = new Vector<Connection>(poolSize);
        try {
            Class.forName(driverClassName);
            for(int i=0;i<poolSize;i++){
                Connection connection = DriverManager.getConnection(url,username,password);
                pool.add(connection);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public synchronized Connection getConnection() {
        if (pool.size() > 0) {
            Connection connection = pool.get(0);
            pool.remove(connection);
            return connection;
        }
        return null;
    }
    public  synchronized void release(Connection connection){
        pool.add(connection);
    }
}
