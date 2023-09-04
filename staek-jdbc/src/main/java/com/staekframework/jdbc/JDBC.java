package com.staekframework.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {
    private static String className = "org.sqlite.JDBC";


    private static String getUrl() {
        File file = new File(".");
        String url = "jdbc:sqlite:" + file.getAbsolutePath() + "/data/dev.db";
        return url;
    }

    public static Connection newConnection() {

        Class<?> aClass = null;
        try {
            // org.sqlite.JDBC static 생성자 호출
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(getUrl());
            return (conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
