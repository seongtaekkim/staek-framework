package com.staekframework.main.businiss;


import com.staekframework.main.aop.factorybean.TxFactoryBean;
import com.staekframework.main.aop.factorybean.TxFactoryConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    UserDao userdao;

    @Autowired
    UserServiceImpl service;

    @Test
    void 프로그래밍적_트랜잭션() {
        List<User> list = userdao.selectAll();
        try {
            service.callwithdrawal_program();
        } catch (Exception e) {
            System.out.println("fail");
        }
        List<User> list2 = userdao.selectAll();
        assertThat(list).isEqualTo(list2);

    }

    @Autowired
    PlatformTransactionManager transactionManager;

    @Test
    void 팩토리빈_프록시_트랜잭션() {
        List<User> list = userdao.selectAll();
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(TxFactoryConfig.class);
        TxFactoryBean bean = context.getBean(TxFactoryBean.class);
        bean.setTransactionManager(transactionManager);
        bean.setTarget(service);
        try {
            ((UserService) bean.getObject()).callwithdrawal();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        List<User> list2 = userdao.selectAll();
        assertThat(list).isEqualTo(list2);
    }
}
