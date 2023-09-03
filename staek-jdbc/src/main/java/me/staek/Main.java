package me.staek;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String className = "org.sqlite.JDBC";

        // org.sqlite.JDBC static 생성자 호출
        Class<?> aClass = Class.forName(className);
        System.out.println(aClass.getName());

        File file = new File(".");
        String url = "jdbc:sqlite:" + file.getAbsolutePath() + "/data/dev.db";


        Connection conn = DriverManager.getConnection(url);
    }
}
