package com.test.controller;

import com.test.dataBase.DB;
import com.test.module.user;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
public class userController {

    @RequestMapping("/register")
    public String insertUser(@RequestParam("userName") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass
    ) throws SQLException {
        DB db = new DB();
        Statement stmt = (db).getConn().createStatement();
        db.isTableExist(stmt);
        user newUser = new user(name, email, pass);
        return newUser.insertUser();
    }

    @RequestMapping("/list")
    public List<user> showAll() throws SQLException {
        DB db = new DB();
        Statement stmt = (db).getConn().createStatement();
        db.isTableExist(stmt);
        return db.listAllUsers();
    }



}
