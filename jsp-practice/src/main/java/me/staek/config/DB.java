package me.staek.config;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection getConnection() {

        /**
         * embeded tomcat 에경우 context.xml 설정을하는데,
         * initContext.lookup() 코드가 소스를 찾지못함.. 해결해야함
         */
//        Context initContext = null;
//        try {
//            initContext = new InitialContext();
//            Context envContext  = (Context)initContext.lookup("java:/comp/env");
//            DataSource ds = (DataSource)envContext.lookup("jdbc/TestDB");
//            return ds.getConnection();
//        } catch (NamingException e) {
//            throw new RuntimeException(e);
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        Connection conn;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/blog?serverTimezone=Asia/Seoul", "bloguser", "root");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;

    }
}
