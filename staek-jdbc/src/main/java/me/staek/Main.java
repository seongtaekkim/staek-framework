package me.staek;

import java.io.File;
import java.sql.*;

public class Main {


    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        String className = "org.sqlite.JDBC";

        // org.sqlite.JDBC static 생성자 호출
        Class<?> aClass = Class.forName(className);
        System.out.println(aClass.getName());

        File file = new File(".");
        String url = "jdbc:sqlite:" + file.getAbsolutePath() + "/data/dev.db";


        Connection conn = DriverManager.getConnection(url);


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


        PreparedStatement ps = conn.prepareStatement("insert into user(id,name) values(?,?)");
        User user = new User();
        user.setId("2");
        user.setName("kim");
        ps.setString(1, user.getId());
        ps.setString(2, user.getName());

        ps.executeUpdate();

        ps.close();
        conn.close();
    }
}
