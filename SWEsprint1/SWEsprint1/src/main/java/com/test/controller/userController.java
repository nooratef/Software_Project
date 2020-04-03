package com.test.controller;

import com.test.dataBase.userDB;
import com.test.module.normalUser;
import com.test.module.user;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class userController {

    @RequestMapping(value="/register",method= RequestMethod.POST)
    public String insertUser(@RequestParam("userName") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass
    ) throws SQLException {
        userDB userDb = new userDB();
        return userDb.insertUser(new normalUser(name, email, pass));
    }

    //---------------------------------------------------
    @RequestMapping("/list")
    public List<user> showAll() throws SQLException {
        userDB userDb = new userDB();
        return userDb.listAllUsers();
    }
}