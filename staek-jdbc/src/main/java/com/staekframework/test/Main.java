package com.staekframework.test;

import com.staekframework.jdbc.JDBC;


import java.sql.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Connection conn = JDBC.getConnection();

        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL\n"
                + ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        String id = "1";
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from user where id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        try {
            ps = conn.prepareStatement("insert into user(id,name) values(?,?)");
            User user = new User();
            Random r = new Random();

            user.setId("1");
            user.setName("kim");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
