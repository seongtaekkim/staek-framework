package com.staekframework.business;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCConnection;

/**
 * @deprecated staek-di 으로 인스턴스 주입함.
 */
public class DaoFactory {

    public UserDao newUserDao() {
        return new UserDao(getDatasource());
    }

    public Datasource getDatasource() {
        return new JDBCConnection();
    }
}
