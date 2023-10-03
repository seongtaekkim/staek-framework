package com.staekframework.main;

import com.staekframework.main.aop.factorybean.TxFactoryBean;
import com.staekframework.main.aop.factorybean.TxFactoryConfig;
import com.staekframework.main.businiss.User;
import com.staekframework.main.businiss.UserDao;
import com.staekframework.main.businiss.UserService;
import com.staekframework.main.businiss.UserServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext run = SpringApplication.run(Application.class, args);
        System.out.println(run.getBean(DataSource.class));
        UserDao dao = run.getBean(UserDao.class);
        List<User> list = dao.selectAll();
        list.stream().forEach(System.out::println);

    }
}
