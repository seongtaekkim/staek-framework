package com.staekframework.test.User;

import com.staekframework.jdbc.*;

import java.sql.*;
import java.util.List;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * 전략패턴 구현체가 포함된 context 호출 함수를 추출하여 JDBCContext class로 이동시켰다.
 * userDao 는 함수호출로 jdbc 기능을 수행할 수 있다.
 *
 * TODO rowmapper 작성, query string 작성
 */
public class UserDao {

    private final JDBCContext jdbccontext;
    public UserDao(Datasource datasource) {
        this.jdbccontext = new JDBCContext(datasource);
    }


    private static RowMapper<User> rowMapper = new RowMapper<>() {
        @Override
        public User row(ResultSet rs) throws SQLException {
            User user = new User();
            user.setId(rs.getString("id"));
            user.setName(rs.getString("name"));
            user.setPassword(rs.getString("password"));
            return user;
        }
    };

    public void deleteAll() {
        this.jdbccontext.executeSql("delete from user");
    }

    public void add(User user) {
        this.jdbccontext.executeSql("insert into user(id,name,password) values(?,?,?)", user);
    }

    public List<User> getAll() {
        List<User> list = null;

        List list1 = this.jdbccontext.jdbccontextList(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user");
            }
        }, new RowMapResultSet(rowMapper));
        return list1;
    }


    public User getOne(Object... args) {

        User object = this.jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user where id = ? AND password = ?");
            }
        }, new RowMapResultSet<User>(rowMapper), args);
        return object;
    }

    public int getCount() {
        int count = 0;

        return this.jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select count(*) as count from user");
            }
        }, new ResultSetStrategy<Integer>() {
            @Override
            public Integer getData(ResultSet rs) throws SQLException {
                rs.next();
                int anInt = rs.getInt(1);
                return anInt;
            }
        });
    }

    public void delete(String id) {

        this.jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from user where id = ?");
            }
        }, id);
    }

    public List<User> getList(String name) {
        List<User> users = this.jdbccontext.jdbccontextList(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user where name = ?");
            }
        }, new RowMapResultSet<User>(rowMapper), name);
        return users;
    }
}
