package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddUserDao extends UserDao {

    public AddUserDao(Datasource datasource) {
        super(datasource);
    }
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
            return conn.prepareStatement("insert into user(id,name) values(?,?)");
    }
}
