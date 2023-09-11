package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCConnection;

/**
 * TODO data access object를 생성하는 factory class
 * - 템플릿 메서드 패턴 구현으로 dao로직마다 함수를 추가해야 한다.
 */
public class DaoFactory {

    public UserDao newUserDao() {
        return new UserDao(getDatasource());
    }

    public Datasource getDatasource() {
        return new JDBCConnection();
    }
}
