package com.staekframework.test;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.test.User.*;


import java.sql.*;

public class Main {

    public static void main(String[] args) {

//        UserDao userDao = new DaoFactory().newUserDao();
        DeleteAllUserDao deleteAllUserDao = new DaoFactory().newDeleteAllUserDao();
//        userDao.createTable();
//        userDao.delete("1");
        deleteAllUserDao.deleteAll();

        AddUserDao addUserDao = new DaoFactory().newAddUserDao();
        User user = new User();
        user.setId("1");
        user.setName("kim");
        addUserDao.add(user);

        GetUserDao getUserDao = new DaoFactory().newGetUserDao();
        User user1 = getUserDao.get("1");
        System.out.println("get result ---  id:" + user1.getId() + " name:" + user1.getName() );

    }
}
