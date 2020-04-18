package com.test.authentication;

import com.test.dataBase.userDB;
import com.test.module.user;

import java.sql.SQLException;

public class authenticator {

    public user authenticate(String userOrEmail, String password) throws SQLException {
        userDB userDb = new userDB();
        user userDatabase = null;
        if (password != "") {
            if (userOrEmail != "")
                userDatabase = userDb.FindByEmail(new user("", userOrEmail, password, ""));
            if (userDatabase==null)
                userDatabase = userDb.FindByName(new user(userOrEmail, "", password, ""));
        }
        //user userDatabase =userDb.FindbyEmail(new user(name, email, password, ""));
        if (userDatabase == null) return null;
        if (password.equals(userDatabase.getPassword()))
            return userDatabase;
        return null;
    }
}
