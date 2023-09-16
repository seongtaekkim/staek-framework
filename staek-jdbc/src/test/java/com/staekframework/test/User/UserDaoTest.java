package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

/**
 * TODO 전체테스트 이전에 init 메서드 1회 실행
 *      단위테스트 종료 시마다 after 메서드가 실행
 */
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

    @AfterEach
    void after() {
        userDao.deleteAll();
    }

    @Test
    void add() {
        User user = new User("1", "kim", "1111");
        userDao.insert(user);
        List<User> users = userDao.selectAll();
        assertThat(users).contains(user);
    }

    @Test
    void count() {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "kim", "1111"));
        list.add(new User("2", "kim", "1111"));
        list.add(new User("3", "kim", "1111"));
        list.add(new User("4", "kim", "1111"));
        list.add(new User("5", "kim", "1111"));
        for (User user : list) {
            userDao.insert(user);
        }
        assertThat(userDao.count()).isEqualTo(list.size());
    }

    @Test
    void deleteAll() {
        userDao.deleteAll();
        assertThat(userDao.count()).isEqualTo(0);
    }
}
