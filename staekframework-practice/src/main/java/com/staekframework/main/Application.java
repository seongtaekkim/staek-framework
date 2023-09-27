package com.staekframework.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(run.getBean(DataSource.class));

        DataSource dataSource = run.getBean(DataSource.class);


        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("select id, name from users;");
            ResultSet rs = ps.executeQuery();
            String city = null;
            while (rs.next()) {
                System.out.println(rs.getString(2));;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}