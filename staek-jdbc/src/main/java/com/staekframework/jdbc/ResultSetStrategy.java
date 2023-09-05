package com.staekframework.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetStrategy<T> {
    T getData(ResultSet rs) throws SQLException;
}
