package com.test.controller;

import com.sun.xml.internal.ws.policy.EffectiveAlternativeSelector;
import com.test.dataBase.userDB;
import com.test.module.administrator;
import com.test.module.normalUser;
import com.test.module.storeOwner;
import com.test.module.user;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.List;

@RestController
public class userController {

    @RequestMapping(value = "/register")
    public String insertUser(@RequestParam("userName") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass
    ) throws SQLException {
        userDB userDb = new userDB();
        return userDb.insertUser(new normalUser(name, email, pass, "normalUser"));
    }

    //---------------------------------------------------
    @RequestMapping(value = "/register", params = "userType")
    public String insertUser(@RequestParam("userName") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass,
                             @RequestParam("userType") String userType
    ) throws SQLException {
        userDB userDb = new userDB();
        if(userType.equals("admin"))
            return userDb.insertUser(new administrator(name, email, pass, userType));
        else if(userType.equals("storeOwner"))
            return userDb.insertUser(new storeOwner(name, email, pass, userType));
        return "";
    }
    //--------------------------------------------------
    @RequestMapping("/login")
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String pass
    ) throws SQLException {
        userDB userDb = new userDB();
        return userDb.login(new user("", email, pass, ""));
    }
    //---------------------------------------------------
    //---------------------------------------------------
    @RequestMapping("/list")
    public List<user> showAll() throws SQLException {
        userDB userDb = new userDB();
        return userDb.listAllUsers();
    }
}