package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Random;

public class UserDao {

    private Datasource datasource;
    public UserDao(Datasource datasource) {
        this.datasource = datasource;
    }

    public void createTable() {

        Connection conn = datasource.newConnection();

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

    }

    public void delete(String id) {

        Connection conn = datasource.newConnection();

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from user where id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void add(User user) {

        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into user(id,name) values(?,?)");

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
