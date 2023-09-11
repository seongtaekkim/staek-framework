package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserDaoTest {

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

    @Test
    @Order(1)
    void add() {
        User user = new User("1", "kim", "1111");
        userDao.insert(user);

    }

    @Test
    @Order(2)
    void getAll() {
        List<User> list = userDao.getAll();
        list.stream().forEach(System.out::println);
    }

    @Test
    @Order(3)
    void deleteAll() {
        userDao.deleteAll();
    }

}