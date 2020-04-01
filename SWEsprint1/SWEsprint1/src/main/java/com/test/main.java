package com.test;

import com.test.dataBase.DB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication
public class main {
    public static void main(String[] args) throws SQLException {
        String dbUrl = "jdbc:derby:C:\\Users\\Thoraya Hamdy\\Desktop\\SWEsprint1\\demo;create=true";
        Connection conn = DriverManager.getConnection(dbUrl);
        Statement stmt = conn.createStatement();
        if(conn!=null)
        {
            boolean found = false;
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "users".toUpperCase(),null);
            if(rs.next())
            {
                found = true;
            }
            else{
                stmt.executeUpdate("Create table users (userName varchar(30) , email varchar(30) primary key, password varchar(30))");
            }
        }
        SpringApplication.run(main.class, args);
    }
}