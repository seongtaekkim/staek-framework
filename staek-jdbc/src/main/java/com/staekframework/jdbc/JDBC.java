package com.staekframework.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC implements Datasource {

    private static String className = "org.sqlite.JDBC";


    private static String getUrl() {
        File file = new File(".");
        String url = "jdbc:sqlite:" + file.getAbsolutePath() + "/data/dev.db";
//        String url = "jdbc:h2:" + file.getAbsolutePath() + "/data/dev2.db";
        return url;
    }

    @Override
    public Connection newConnection() {
//
//        try {
//            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
//            System.out.println("new ::::::" + conn);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

//        Class<?> aClass = null;
//        try {
//            // org.sqlite.JDBC static 생성자 호출
//            aClass = Class.forName(className);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(getUrl());
//            conn = DriverManager.getConnection(getUrl(),"sa","");
            return (conn);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
