package com.staekframework.business;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCConnection;
import com.staekframework.tx.DefaultTxManager;
import com.staekframework.tx.TxFactoryProxy;
import com.staekframework.tx.TxHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    static Datasource datasource;
    static UserDao userDao;

    @BeforeAll
    static void init() {
        datasource = new DaoFactory().getDatasource();
        Connection conn = datasource.newConnection();
        Assertions.assertNotNull(conn);

        userDao = new DaoFactory().newUserDao();
        Assertions.assertNotNull(userDao);

        userDao.createTable();
    }

    @AfterEach
    void after() {
        userDao.deleteAll();
    }

    @Test
    void add() {
        User user = new User("1", "kim", "1111", "10000");
        UserService service = new UserServiceImpl(userDao);
        try {
            service.createUser(user);
        } catch (Exception e) {
            System.out.println("fail");
        }
        List<User> users = userDao.selectAll();
        assertThat(users).contains(user);
    }

    @Test
    void 무결성검사_유저추가() {
        User user = new User("1", "kim", "1111", "10");
        UserService service = new UserServiceImpl(userDao);
        TxHandler txHandler = new TxHandler();
        txHandler.setTarget(service);
        txHandler.setTxManager(new DefaultTxManager(JDBCConnection.conn));

        UserService o = (UserService) Proxy.newProxyInstance(getClass().getClassLoader()
                , new Class[]{UserService.class}
                , txHandler);
        try {
            o.createUser(user);
        } catch (Exception e) {
            System.out.println("fail");
        }
        List<User> users = userDao.selectAll();
        assertThat(users.size()).isEqualTo(0);
    }

    @Test
    void count() {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "kim", "1111", "10000"));
        list.add(new User("2", "kim", "1111", "15000"));
        list.add(new User("3", "kim", "1111", "15000"));
        list.add(new User("4", "kim", "1111", "20000"));
        list.add(new User("5", "kim", "1111", "20000"));
        UserService service = new UserServiceImpl(userDao);
        for (User user : list) {
            try {
                service.createUser(user);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        assertThat(userDao.count()).isEqualTo(list.size());
    }

    @Test
    void deleteAll() {
        userDao.deleteAll();
        assertThat(userDao.count()).isEqualTo(0);
    }

    @Test
    void update_price() {
        User user = new User("1", "kim", "1111", "10000");
        UserService service = new UserServiceImpl(userDao);
        try {
            service.createUser(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        user = new User("1", "kim", "1111", "15000");
        userDao.update(user);
        User vo = userDao.selectOne("1", "1111");
        assertThat(vo.getPrice()).isEqualTo("15000");

    }

    @Test
    void 무결성오류_검사() throws SQLException {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "kim", "1111", "50000"));
        list.add(new User("2", "kim2", "1111", "45000"));
        list.add(new User("3", "kim3", "1111", "35000"));
        list.add(new User("4", "kim4", "1111", "20000"));
        list.add(new User("5", "kim5", "1111", "10000"));
        for (User user : list) {
            userDao.insert(user);
        }
        List<User> users = userDao.selectAll();
        users.stream().forEach(System.out::println);

        UserService service = new UserServiceImpl(userDao);
        TxHandler txHandler = new TxHandler();
        txHandler.setTarget(service);

        txHandler.setTxManager(new DefaultTxManager(JDBCConnection.conn));
        /**
         * 다이나믹 프록시 생성 코드
         * 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더, 구현할 인터페이스, 타겟 클래스를 담은 InvocationHandler 구현체
         */
        UserService o = (UserService) Proxy.newProxyInstance(getClass().getClassLoader()
                , new Class[]{UserService.class}
                , txHandler);
        try {
            o.callwithdrawal_program();
        } catch (Exception e) {
            System.out.println("fail");
        }

        List<User> users2 = userDao.selectAll();
        users2.stream().forEach(System.out::println);
        assertThat(users).isEqualTo(users2);
    }

    @Test
    void 무결성오류_검사_FactoryProxy() throws Exception {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "kim", "1111", "50000"));
        list.add(new User("2", "kim2", "1111", "45000"));
        list.add(new User("3", "kim3", "1111", "35000"));
        list.add(new User("4", "kim4", "1111", "20000"));
        list.add(new User("5", "kim5", "1111", "10000"));
        for (User user : list) {
            userDao.insert(user);
        }
        List<User> users = userDao.selectAll();
        users.stream().forEach(System.out::println);

        UserService service = new UserServiceImpl(userDao);
        TxFactoryProxy txFactoryProxy = new TxFactoryProxy();
        txFactoryProxy.setServiceInterface(UserService.class);
        txFactoryProxy.setTarget(service);
        txFactoryProxy.setTxManager(new DefaultTxManager(JDBCConnection.conn));

        /**
         * 다이나믹 프록시 생성 코드
         * 동적으로 생성되는 다이내믹 프록시 클래스의 로딩에 사용할 클래스 로더, 구현할 인터페이스, 타겟 클래스를 담은 InvocationHandler 구현체
         */
        UserService o = (UserService) txFactoryProxy.getObject();
        try {
            o.callwithdrawal_program();
        } catch (Exception e) {
            System.out.println("fail");
        }

        List<User> users2 = userDao.selectAll();
        users2.stream().forEach(System.out::println);
        assertThat(users).isEqualTo(users2);
    }
}
