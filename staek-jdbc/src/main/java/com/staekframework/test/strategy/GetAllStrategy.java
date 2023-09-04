package com.staekframework.test.strategy;

import com.staekframework.jdbc.PreparedStatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GetAllStrategy implements PreparedStatementStrategy {
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("select id, name from user where id = ?");
    }
}
