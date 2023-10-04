package com.staekframework.aop.proxy;

/**
 * 구현체를 @Inject하면 getObject 메서드를 통해 지정한 타입의 인스턴스를 가져오도록 한다.
 */
public interface FactoryProxyInstance<T> {
        T getObject() throws Exception;
        Class<?> getObjectType();
}
