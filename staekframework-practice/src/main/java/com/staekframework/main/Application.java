package com.staekframework.main;

import com.staekframework.main.businiss.User;
import com.staekframework.main.businiss.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class Application {


    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(run.getBean(DataSource.class));

        DataSource dataSource = run.getBean(DataSource.class);
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        UserDao dao = run.getBean(UserDao.class);
        List<User> list = dao.selectAll();
        list.stream().forEach(System.out::println);

    }
}