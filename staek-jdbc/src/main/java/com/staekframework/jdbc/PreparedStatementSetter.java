package com.staekframework.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparedStatementSetter {
    void setObjectToRows(PreparedStatement ps) throws SQLException;
}
