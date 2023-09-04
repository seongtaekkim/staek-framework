package com.staekframework.test;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.test.User.DaoFactory;
import com.staekframework.test.User.User;
import com.staekframework.test.User.UserDao;


import java.sql.*;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new DaoFactory().newUserDao();
        userDao.createTable();
        userDao.delete("1");

        User user = new User();
        user.setId("1");
        user.setName("kim");
        userDao.add(user);

    }
}
