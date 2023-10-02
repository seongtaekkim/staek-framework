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

        UserService service = run.getBean(UserServiceImpl.class);
        try {
            service.callwithdrawal_program();
//            service.callwithdrawal_declare();
//            service.callwithdrawal();
        } catch (Exception e) {
            System.out.println("fail");
        }

        list = dao.selectAll();
        list.stream().forEach(System.out::println);


        /**
         * FactoryBean 을 이용한 트랜잭션 테스트
         */
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TxFactoryConfig.class);
        TxFactoryBean bean = context.getBean(TxFactoryBean.class);
        bean.setTransactionManager(run.getBean(PlatformTransactionManager.class));
        bean.setTarget(service);
        try {
            ((UserService) bean.getObject()).callwithdrawal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        list = dao.selectAll();
        list.stream().forEach(System.out::println);
    }
}
