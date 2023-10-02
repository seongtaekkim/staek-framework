package com.staekframework.aop.factorybean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageFactoryConfig {

    @Bean("factorybean")
    public MessageFactoryBean factoryBean() {
        MessageFactoryBean bean = new MessageFactoryBean();
        bean.setText("bbbbbbbb");
        return bean;
    }

    @Bean("message")
    public Message messageBean() throws Exception {
        return factoryBean().getObject();
    }
}
