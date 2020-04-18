package com.test.dataBase;
//import com.test.module.*;
import com.test.module.loggedUser;
import com.test.module.user;

import javax.validation.constraints.Null;
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
    public user FindByEmail(user user) throws SQLException {
        user databaseObj=new user();
        ResultSet result = this.stmt.executeQuery("SELECT * FROM users where email = '" + user.getEmail() + "' ");
        if (result.next()) {

            String userType = result.getString("userType");
            String userName = result.getString("userName");
            String password = result.getString("password");
            String email = result.getString("email");

          //  if(password!=user.getPassword()) return "Wrong password ";
            databaseObj.setUserType(userType);
            databaseObj.setUserName(userName);
            databaseObj.setPassword(password);
            databaseObj.setEmail(email);
         //   loggedUser.setActiveUser(user);

           // return "logged in";
            return databaseObj;
        }
       // return "this user not found, please register first";
        return null;
    }

    //----------------------------------------

    public user FindByName(user user) throws SQLException {
        user databaseObj=new user();
        ResultSet Result = this.stmt.executeQuery("SELECT * FROM users where userName = '" + user.getUserName() + "' ");
        if (Result.next()) {

            String userType = Result.getString("userType");
            String userName = Result.getString("userName");
            String password = Result.getString("password");
            String email = Result.getString("email");

            //  if(password!=user.getPassword()) return "Wrong password ";
            databaseObj.setUserType(userType);
            databaseObj.setUserName(userName);
            databaseObj.setPassword(password);
            databaseObj.setEmail(email);
            //   loggedUser.setActiveUser(user);

            // return "logged in";
            return databaseObj;
        }
        // return "this user not found, please register first";
        return null;
    }

    //----------------------------------------
    public List<user> listAllUsers() throws SQLException {
        List<user> users = new ArrayList<user>();
        // ResultSet result = this.stmt.executeQuery("SELECT * FROM users ");

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


           // System.out.println("please login first");
        return users;
    }

    //-----------------------------------------------
    public String insertUser(user newUser) throws SQLException {

      //  ResultSet result = this.stmt.executeQuery("SELECT * FROM users where email = '" + newUser.getEmail() + "'");
        user userByEmail=FindByEmail(newUser);
        user userByName=FindByName(newUser);
        if(userByEmail==null && userByName==null ){
            this.stmt.executeUpdate("insert into users values ('" + newUser.getUserName() + "', '" + newUser.getEmail() + "', '" + newUser.getPassword() + "','" + newUser.getUserType() + "')");
            return "user added";
        }
        if(userByEmail!=null)
        return "user email already exist";
        else
            return "user name already exist";


    }

    //----------------------------------------------------
    public void isTableExist() throws SQLException {
        boolean found = false;
        if (this.conn != null) {
            DatabaseMetaData dbmd = this.conn.getMetaData();
            ResultSet result = dbmd.getTables(null, null, "users".toUpperCase(), null);
            if (result.next()) {
                found = true;
            } else {
                this.stmt.executeUpdate("Create table users (userName varchar(30)  unique, email varchar(30) primary key, password varchar(30),userType varchar(30))");
                this.stmt.executeUpdate("insert into users values ('Admin','Admin@onlineShopping.com','password' ,'admin' )");

            }

        }
    }
}