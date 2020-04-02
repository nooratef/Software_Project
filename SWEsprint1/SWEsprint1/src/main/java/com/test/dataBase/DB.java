package com.test.dataBase;

import com.test.module.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {
    private Statement stmt;
    private Connection conn;
    public DB() throws SQLException {
        String dbUrl = "jdbc:derby:C:\\Users\\Thoraya Hamdy\\Desktop\\SWEsprint1\\demo;create=true";
        conn = DriverManager.getConnection(dbUrl);
       // Statement stmt = conn.createStatement();

    }

    public List<user> listAllUsers() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        // print out query result
        List<user> users = new ArrayList<user>();
        while (rs.next()) {
           String name = rs.getString("userName");
           String email = rs.getString("email");
           String pass = rs.getString("password");
            user user = new user(name,email,pass);
            users.add(user);
        }
        return users;
    }

    public Connection getConn() {
        return conn;
    }
    public void isTableExist(Statement stmt) throws SQLException {
        boolean found = false;
        if(conn!=null)
        {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, "users".toUpperCase(),null);
            if(rs.next())
            {
                found = true;
            }
            else {

                stmt.executeUpdate("Create table users (userName varchar(30) , email varchar(30) primary key, password varchar(30))");
            }

        }
    }
}