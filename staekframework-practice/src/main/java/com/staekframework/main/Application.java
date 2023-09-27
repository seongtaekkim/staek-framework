package com.staekframework.main;

import com.staekframework.main.businiss.User;
import com.staekframework.main.businiss.UserDao;
import com.staekframework.main.businiss.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args)  {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(run.getBean(DataSource.class));

        UserDao dao = run.getBean(UserDao.class);
        List<User> list = dao.selectAll();
        list.stream().forEach(System.out::println);

        UserService service = run.getBean(UserService.class);
        try {
            service.callwithdrawal();
        } catch (Exception e) {
            System.out.println("fail");
        }

        list = dao.selectAll();
        list.stream().forEach(System.out::println);


    }
}