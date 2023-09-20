package com.staekframework.business;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCConnection;

public class DaoFactory {

    public UserDao newUserDao() {
        return new UserDao(getDatasource());
    }

    public Datasource getDatasource() {
        return new JDBCConnection();
    }
}
