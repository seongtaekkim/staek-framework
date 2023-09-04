package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;

public class DaoFactory {

    public UserDao newUserDao() {
        return new UserDao(getDatasource());
    }

    private Datasource getDatasource() {
        return new JDBC();
    }
}
