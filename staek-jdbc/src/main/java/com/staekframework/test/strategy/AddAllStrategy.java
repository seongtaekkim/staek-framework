package com.staekframework.test.strategy;

import com.staekframework.jdbc.PreparedStatementStrategy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 특정 비지니스 dao 에 종속되지 않고
 * PreparedStatement 생성 업무만 진행한다.
 */
public class AddAllStrategy implements PreparedStatementStrategy {
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
        return conn.prepareStatement("insert into user(id,name) values(?,?)");
    }
}
