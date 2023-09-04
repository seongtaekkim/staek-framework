package com.staekframework.test.strategy;

import com.staekframework.jdbc.PreparedStatementStrategy;
import com.staekframework.test.User.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 특정 비지니스 dao 에 종속되지 않고
 * PreparedStatement 생성 업무만 진행한다.
 */
public class AddAllStrategy implements PreparedStatementStrategy {
    private User user;
    public AddAllStrategy(User user) {
        this.user = user;
    }
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
        PreparedStatement ps = conn.prepareStatement("insert into user(id,name) values(?,?)");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());
        return ps;
    }
}
