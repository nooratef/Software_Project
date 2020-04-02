package com.test.dataBase;

import com.test.module.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB {

    private Statement stmt;
    private Connection conn;
    //-----------------------------------
    public DB() throws SQLException {
        String dbUrl = "jdbc:derby:C:\\projectdb\\DB;create=true";
        this.conn = DriverManager.getConnection(dbUrl);
        this.stmt = conn.createStatement();
        this.isTableExist();
    }
    //-------------------------------------

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return conn;
    }
    //----------------------------------------
    public List<user> listAllUsers() throws SQLException {
        ResultSet result = this.stmt.executeQuery("SELECT * FROM users");
        // print out query result
        List<user> users = new ArrayList<user>();
        while (result.next()) {
           String name = result.getString("userName");
           String email = result.getString("email");
           String pass = result.getString("password");
            user user = new user(name,email,pass);
            users.add(user);
        }
        return users;
    }
    //-----------------------------------------------
    public String insertUser(user newUser) throws SQLException {
        ResultSet result = this.stmt.executeQuery("SELECT * FROM users where email = '" + newUser.getEmail() + "'");
        if(!result.next()) {
            this.stmt.executeUpdate("insert into users values ('" + newUser.getUserName() + "', '" + newUser.getEmail() + "', '" + newUser.getPassword() + "')");
            return "user added";
        }
        return "user already existing";
    }
    //----------------------------------------------------
    public void isTableExist() throws SQLException {
        boolean found = false;
        if(this.conn!=null)
        {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            ResultSet result = dbmd.getTables(null, null, "users".toUpperCase(),null);
            if(result.next()) {
                found = true;
            }
            else {
                this.stmt.executeUpdate("Create table users (userName varchar(30) , email varchar(30) primary key, password varchar(30))");
            }

        }
    }
}