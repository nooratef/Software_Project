package com.test;

import com.test.dataBase.userDB;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.*;

@SpringBootApplication

public class main {
    public static void main(String[] args) throws SQLException {
        SpringApplication.run(main.class, args);
    }
}