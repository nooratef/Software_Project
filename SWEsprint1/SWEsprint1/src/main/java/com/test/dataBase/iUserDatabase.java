package com.test.dataBase;

import com.test.module.user;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

interface iUserDatabase {

    public Statement getStmt();
    public void setStmt(Statement stmt);
    public Connection getConn();
    public void setConn(Connection conn);

    public List<user> listAllUsers() throws SQLException;
    public String insertUser(user newUser) throws SQLException;
    public void isTableExist() throws SQLException;
}
