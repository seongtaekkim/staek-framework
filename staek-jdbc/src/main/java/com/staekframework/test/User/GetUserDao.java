package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetUserDao extends UserDao {

    public GetUserDao(Datasource datasource) {
        super(datasource);
    }
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
            return conn.prepareStatement("select id, name from user where id = ?");
    }
}