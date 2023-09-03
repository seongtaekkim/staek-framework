package me.staek;

import java.io.File;
import java.sql.*;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String className = "org.sqlite.JDBC";

        // org.sqlite.JDBC static 생성자 호출
        Class<?> aClass = null;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println(aClass.getName());

        File file = new File(".");
        String url = "jdbc:sqlite:" + file.getAbsolutePath() + "/data/dev.db";


        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL\n"
                + ");";

        try (Connection con = DriverManager.getConnection(url);
             Statement stmt = con.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("insert into user(id,name) values(?,?)");
            User user = new User();
            Random r = new Random();

            user.setId("2");
            user.setName("kim");
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());

            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
