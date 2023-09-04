package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * TODO
 */
public class DeleteAllUserDao extends UserDao {

    public DeleteAllUserDao(Datasource datasource) {
        super(datasource);
    }
    @Override
    public PreparedStatement newStatement(Connection conn) throws SQLException {
            return conn.prepareStatement("delete from user");
    }
}
