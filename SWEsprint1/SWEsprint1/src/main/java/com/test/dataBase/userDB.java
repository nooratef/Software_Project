package com.test.dataBase;

import com.test.module.loggedUser;
import com.test.module.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userDB implements iUserDatabase {

    private Statement stmt;
    private Connection conn;
    //-----------------------------------
    public userDB() throws SQLException {
        String dbUrl = "jdbc:derby:C:\\projectdb\\userDB;create=true";
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
    public String login(user user) throws SQLException {
        ResultSet result = this.stmt.executeQuery("SELECT * FROM users where email = '" + user.getEmail() + "'");
        if(result.next()) {
            String userType = result.getString("userType");
            String userName = result.getString("userName");
            user.setUserType(userType);
            user.setUserName(userName);
            loggedUser.setActiveUser(user);
            return "logged in";
        }
        return "this user not found, please register first";
    }
    //----------------------------------------
    public List<user> listAllUsers() throws SQLException {
        List<user> users = new ArrayList<user>();
        if(loggedUser.getActiveUser().getUserType().equals("admin")) {
            ResultSet result = this.stmt.executeQuery("SELECT * FROM users");
            // print out query result
            while (result.next()) {
                String name = result.getString("userName");
                String email = result.getString("email");
                String pass = result.getString("password");
                String userType = result.getString("userType");
                user user = new user(name, email, pass, userType);
                users.add(user);
            }
        }
            return users;
    }

    //-----------------------------------------------
    public String insertUser(user newUser) throws SQLException {
        ResultSet result = this.stmt.executeQuery("SELECT * FROM users where email = '" + newUser.getEmail() + "'");
        if(!result.next()) {
            this.stmt.executeUpdate("insert into users values ('" + newUser.getUserName() + "', '" + newUser.getEmail() + "', '" + newUser.getPassword() + "','" + newUser.getUserType()+ "')");
            return "user added";
        }
        return "user email already exist";
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
                this.stmt.executeUpdate("Create table users (userName varchar(30) , email varchar(30) primary key, password varchar(30),userType varchar(30))");
            }

        }
    }
}