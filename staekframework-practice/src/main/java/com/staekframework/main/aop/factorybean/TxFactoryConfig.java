package com.staekframework.main.aop.factorybean;

import com.staekframework.main.businiss.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * factorybean 빈 등록 class
 */
@Component
public class TxFactoryConfig {

    // autowired 가 안되어서 생략.. 왜안되지
//    PlatformTransactionManager transactionManager;

//    UserServiceImpl service;

    @Bean
    public TxFactoryBean factoryBean() {
        TxFactoryBean bean = new TxFactoryBean();
        bean.setPattern("");
        bean.setServiceInterface(UserService.class);
//        bean.setTarget(service);
//        bean.setTransactionManager(transactionManager);
        return bean;
    }
}
