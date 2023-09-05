package com.staekframework.jdbc;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RowMapResultSet<T> implements ResultSetStrategy<List<T>> {

    private final RowMapper<T> mapper;
    public RowMapResultSet(RowMapper<T> mapper) {
        this.mapper = mapper;
    }
    @Override
    public List<T> getData(ResultSet rs) throws SQLException {
        List<T> list = new ArrayList<>();

        while (rs.next())
            list.add(mapper.row(rs));
        return null;
    }
}
