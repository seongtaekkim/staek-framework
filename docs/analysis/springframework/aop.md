# AOP (**Aspect Oriented Programming**)



## ProxyFactoryBean

- 구현체를 빈으로 등록하면, getObject 메서드를 통해 타겟, 부가기능 등을 갖고 있는 프록시객체를 가져올 수 있게 정의할 수 있다

~~~java
public interface FactoryBean<T> {
    String OBJECT_TYPE_ATTRIBUTE = "factoryBeanObjectType";

    @Nullable
    T getObject() throws Exception;

    @Nullable
    Class<?> getObjectType();

    default boolean isSingleton() {
        return true;
    }
}
~~~

- 구현체 Proxy 생성 로직

~~~java
	public Object getObject() throws Exception {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(target);
		txHandler.setTransactionManager(transactionManager);
		txHandler.setPattern(pattern);	
		return Proxy.newProxyInstance(
				getClass().getClassLoader(),new Class[] { serviceInterface }, txHandler);
	}
~~~











































