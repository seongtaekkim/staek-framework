package com.staekframework.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArgsPreparedStatementSetter implements PreparedStatementSetter {

    private final Object[] args;

    public ArgsPreparedStatementSetter(Object[] args) {
        this.args = args;
    }

    @Override
    public void setObjectToRows(PreparedStatement ps) throws SQLException {
        if (args != null) {
            for (int i = 0 ; i < args.length ; i++)
                ps.setString(i+1, args[i].toString());
        }
    }
}
