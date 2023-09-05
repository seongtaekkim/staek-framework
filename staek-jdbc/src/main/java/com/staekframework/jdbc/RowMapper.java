package com.staekframework.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface RowMapper<T> {
    public T row(ResultSet rs) throws SQLException;
}