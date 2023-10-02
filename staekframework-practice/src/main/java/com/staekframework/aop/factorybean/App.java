package com.staekframework.aop.factorybean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * FactoryBean 을 구현한 클래스가 bean에 등록하면, 스프링에서 FactoryBean의 type도 bean에 등록해준다.
 * - MessageFactoryBean을 빈에 등록하면, Message도 bean에 등록된다.
 *
 *  https://www.baeldung.com/spring-factorybean
 */
public class App {
    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MessageFactoryConfig.class);
        MessageFactoryBean bean = context.getBean(MessageFactoryBean.class);

        System.out.println(bean.getObject().getText());
        Object message = context.getBean("message");
        System.out.println(((Message)message).getText());
    }
}
