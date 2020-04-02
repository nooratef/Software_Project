package com.test.module;

import com.test.dataBase.DB;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class user {
    protected String userName;
    protected String email;
    protected String password;

    public user() {
    }

    public user(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String insertUser() throws SQLException {
        Statement stmt = (new DB()).getConn().createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where email = '" + this.email + "'");
        boolean found = false;
        if(!rs.next()) {
            stmt.executeUpdate("insert into users values ('" + this.userName + "', '" + this.email + "', '" + this.password + "')");
            return "user added";
        }
        return "user already existing";
    }

}
