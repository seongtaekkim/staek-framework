package com.staekframework.test;

import com.staekframework.jdbc.JDBC;
import com.staekframework.test.User.User;
import com.staekframework.test.User.UserDao;


import java.sql.*;

public class Main {

    public static void main(String[] args) {

        try (Connection conn = JDBC.newConnection()) {
            UserDao dao = new UserDao(conn);
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
