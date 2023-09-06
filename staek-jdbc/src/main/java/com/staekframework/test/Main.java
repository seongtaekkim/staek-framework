package com.staekframework.test;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.jdbc.JDBCContext;
import com.staekframework.test.User.*;


import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    /**
     *
     * getOne(args ...)
     * getAll()
     * getCount()
     *
     * delete(String)
     * deleteAll()
     *
     *
     */
    public static void main(String[] args) {

        /**
         * 전략패턴 구현으로,
         * dao factory 에서 다시 UserDao 객체만 생성해도 모든 dml 을 호출할 수 있게 되었다.
         */
        UserDao userDao = new DaoFactory().newUserDao();
        JDBCContext context = new JDBCContext(new DaoFactory().getDatasource());
        userDao.setContext(context);
//        userDao.createTable();
//        userDao.delete("1");
        context.executeSql("delete from user");

        User user = new User();
        user.setId("1");
        user.setName("kim");
        context.executeSql("insert into user(id,name) values(?,?)", user);

        user.setId("2");
        user.setName("spring");
        context.executeSql("insert into user(id,name) values(?,?)", user);


        User user1 = userDao.get("1");
        System.out.println("get result ---  id:" + user1.getId() + " name:" + user1.getName() );

        System.out.println("count: " + userDao.getCount());


        System.out.println("getALL==============================");
        List<User> list = userDao.getAll();
        Arrays.stream(list.toArray()).forEach(System.out::println);

        System.out.println("getOne =============================");
        User user2 = userDao.get("2");
        System.out.println(user2.toString());


    }
}
