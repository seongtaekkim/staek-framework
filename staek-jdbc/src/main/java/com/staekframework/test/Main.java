package com.staekframework.test;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.test.User.User;
import com.staekframework.test.User.UserDao;


import java.sql.*;

public class Main {

    public static void main(String[] args) {

        JDBC jdbc = new JDBC();
        try (Connection conn = jdbc.newConnection()) {
            Datasource ds = new JDBC();
            UserDao dao = new UserDao(ds);
            dao.createTable();
            dao.delete("1");

            User user = new User();
            user.setId("1");
            user.setName("kim");
            dao.add(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
