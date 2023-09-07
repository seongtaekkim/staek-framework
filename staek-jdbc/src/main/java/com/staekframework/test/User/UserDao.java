package com.staekframework.test.User;

import com.staekframework.jdbc.*;

import java.sql.*;
import java.util.List;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * 전략패턴 구현체가 포함된 context 호출 함수를 추출하여 JDBCContext class로 이동시켰다.
 * userDao 는 함수호출로 jdbc 기능을 수행할 수 있다.
 *
 * TODO JDBCContext 객체 의존
 *      rowmapper 작성, query string 작성
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


    public void delete(String id) {
        this.jdbccontext.updateSql("delete from users where id = ?", id);
    }

    public void deleteAll() {
        this.jdbccontext.updateSql("delete from users");
    }

    public void insert(User user) {
        this.jdbccontext.updateSql("insert into users(id,name,password) values(?,?,?)", user.getId(), user.getName(), user.getPassword());
    }

    public List<User> getAll() {
        return this.jdbccontext.executeSql("select id, name, password from users", rowMapper);
    }

    public User getOne(Object... args) {
         return this.jdbccontext.executeSql("select id, name, password from users where id = ? AND password = ?", rowMapper, args);
    }

    public List<User> getList(Object... args) {
        return this.jdbccontext.executeSql2("select id, name, password from users where name = ?", rowMapper, args);
    }

    public int getCount() {
        return this.jdbccontext.countSql("select count(*) as count from users");
    }

}
