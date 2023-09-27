# transaction



## 시스템 기술 출처

- spring-tx:6.0.11



### 데이터소스는 어디서 얻어오는가

~~~properties
# application.properties
database=h2
spring.sql.init.schema-locations=classpath*:db/${database}/schema.sql
spring.sql.init.data-locations=classpath*:db/${database}/data.sql
~~~



## 프로그래밍적 트랜잭션

- PlatformTransactionManager 구현체를 사용

- TransactionDefinition, TransactionStatus을 사용해서 롤백 커밋할 수 있다.

```java
@Autowired
PlatformTransactionManager transactionManager;

public void callwithdrawal_program() throws Exception {

    TransactionStatus status = this.transactionManager
            .getTransaction(new DefaultTransactionDefinition());

    List<User> users = userDao.selectAll();
    for (User user : users) {
        try {
            if (checkPrice(user)) {
                User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                        , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
                userDao.update(uptVo);
            }
        } catch (Exception e) {
            this.transactionManager.rollback(status);
            throw e;
        }
    }
    this.transactionManager.commit(status);
}
```

 

 

## 선언적 트랜잭션

- 클래스, 메서드 위에 @Transactional 선언해서 사용하면 트랜잭션 기능이 적용된 프록시 객체가 생성된다.
- 선언만으로 해당 scope 에 트랜잭션이 적용된다.
- @Transactional 포함된 메서드가 호출될 경우 Spring은 해당 메서드에 대한 프록시(프록시패턴 디자인 패턴 중 하나)를 만드는데, PlatformTransactionManager를 사용해 트랜잭션을 시작하고, 정상 여부에 따라 커밋 또는 롤백을 알아서 해준다.

 

### @Transactional

- 클래스, 메서드 에 사용 가능

~~~java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Reflective
public @interface Transactional {
  ...
~~~



- 서비스 로직이 중간에 실패하면 롤백한다.
- @Transactional이 없으면 개별로 성공한 commit은 그대로 적용된다.

~~~java
@Transactional
public void callwithdrawal() throws Exception {

    List<User> users = userDao.selectAll();
    for (User user : users) {
        try {
            if (checkPrice(user)) {
                User uptVo = new User(user.getId(), user.getName(), user.getPassword()
                        , Integer.toString(Integer.parseInt(user.getPrice()) - 10000));
                userDao.update(uptVo);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
~~~



### 문제점

- 다음에