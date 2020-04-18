package com.test.controller;

import com.test.dataBase.userDB;
import com.test.authentication.authenticator;
import com.test.module.*;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@RestController
public class userController {

    @PostMapping(value = "/register")
    public String insertUser(@RequestParam("userName") String name,
                             @RequestParam("email") String email,
                             @RequestParam("password") String pass
    ) throws SQLException {
        userDB userDb = new userDB();
        return userDb.insertUser(new user(name, email, pass, "normalUser"));
    }

    //---------------------------------------------------
    @PostMapping(value = "/registerStoreOwner")
    public String insertStoreOwner(@RequestParam("userName") String name,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String pass
    ) throws SQLException {

        userDB userDb = new userDB();
        return userDb.insertUser(new user(name, email, pass, "storeOwner"));

    }
    //--------------------------------------------------

    @PostMapping(value = "/registerAdmin")
    public String insertAdmin(@RequestParam("userName") String name,
                              @RequestParam("email") String email,
                              @RequestParam("password") String pass

    ) throws SQLException {
        userDB userDb = new userDB();
        if (loggedUser.getActiveUser() != null) {
            if (loggedUser.getActiveUser().getUserType().equals("admin"))
                return userDb.insertUser(new user(name, email, pass, "admin"));
        } else if (loggedUser.getActiveUser() == null)
            return "No user logged in";
        return "Admin Registration error, admin access only ";
    }

    //--------------------------------------------------
    @PostMapping("/login")
    public String login(
            @RequestParam("userName or email") String nameOrEmail,
            @RequestParam("password") String password
    ) throws SQLException {

        authenticator auth = new authenticator();
        user authenticatedUser = auth.authenticate(nameOrEmail, password);
        if (authenticatedUser == null)
            return "this user not found,check credentials ";
        else {
            loggedUser.setActiveUser(authenticatedUser);
            return "logged in";
        }
    }

    //---------------------------------------------------
    @RequestMapping("/list")
    public List<user> showAll() throws SQLException {
        userDB userDb = new userDB();
        //check ther is a logged user in the system
        if (loggedUser.getActiveUser() != null) {
            //check the authorization for accessing List
            if (loggedUser.getActiveUser().getUserType().equals("admin")) {
                return userDb.listAllUsers();
            }
        }
        System.out.println("Error to access, admins only ");
        return Collections.emptyList();
    }
}