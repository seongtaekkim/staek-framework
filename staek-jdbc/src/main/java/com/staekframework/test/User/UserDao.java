package com.staekframework.test.User;

import com.staekframework.jdbc.*;

import java.sql.*;
import java.util.List;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * 전략패턴 구현체가 포함된 context 호출 함수를 추출하여 JDBCContext class로 이동시켰다.
 * userDao 는 함수호출로 jdbc 기능을 수행할 수 있다.
 * <p>
 * TODO JDBCContext 객체 의존
 *      rowmapper 작성, query string 작성
 */
public class UserDao {

    private final JDBCContext jdbccontext;
    public final Datasource datasource;

    public UserDao(Datasource datasource) {
        this.datasource = datasource;
        this.jdbccontext = new JDBCContext(datasource);
    }

    private static RowMapper<User> rowMapper = new RowMapper<>() {
        @Override
        public User row(ResultSet rs) throws SQLException {
            return new User(rs.getString("id")
                    , rs.getString("name")
                    , rs.getString("password")
                    , rs.getString("price"));
        }
    };


    public void delete(String id) {
        this.jdbccontext.updateSql("delete from users where id = ?", id);
    }

    public void deleteAll() {
        this.jdbccontext.updateSql("delete from users");
    }

    public void insert(User user) {
        this.jdbccontext.updateSql("insert into users(id,name,password,price) values(?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword(), user.getPrice());
    }

    public List<User> selectAll() {
        return this.jdbccontext.executeSql("select id, name, password, price from users", rowMapper);
    }

    public User selectOne(Object... args) {
        return this.jdbccontext.executeSql("select id, name, password, price from users where id = ? AND password = ?", rowMapper, args);
    }

    public List<User> selectList(Object... args) {
        return this.jdbccontext.executeSql2("select id, name, password, price from users where name = ?", rowMapper, args);
    }

    public int count() {
        return this.jdbccontext.countSql("select count(*) as count from users");
    }


    public void createTable() {
        Datasource datasource = new DaoFactory().getDatasource();
        Connection conn = datasource.newConnection();

        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + " password text NOT NULL, price integer NOT NULL DEFAULT 0 );";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(User user) {
        this.jdbccontext.updateSql("update users set price = ? where id = ?"
                , user.getPrice(), user.getId());
    }
}
