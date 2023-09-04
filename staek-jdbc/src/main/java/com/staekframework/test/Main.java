package com.staekframework.test;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.test.User.*;


import java.sql.*;

public class Main {

    public static void main(String[] args) {

        /**
         * 전략패턴 구현으로,
         * dao factory 에서 다시 UserDao 객체만 생성해도 모든 dml 을 호출할 수 있게 되었다.
         */
        UserDao userDao = new DaoFactory().newUserDao();

//        userDao.createTable();
//        userDao.delete("1");
        userDao.deleteAll();


        User user = new User();
        user.setId("1");
        user.setName("kim");
        userDao.add(user);

        User user1 = userDao.get("1");
        System.out.println("get result ---  id:" + user1.getId() + " name:" + user1.getName() );

    }
}
