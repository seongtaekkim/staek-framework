package com.staekframework.test.User;

import com.staekframework.jdbc.*;

import java.sql.*;
import java.util.List;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * 전략패턴 구현체가 포함된 context 호출 함수를 추출하여 JDBCContext class로 이동시켰다.
 * userDao 는 함수호출로 jdbc 기능을 수행할 수 있다.
 */
public class UserDao {

    protected Datasource datasource;
    public UserDao(Datasource datasource) {
        this.datasource = datasource;
    }

    private JDBCContext jdbccontext;
    public void setContext(JDBCContext context) {
        this.jdbccontext = context;
    }

    public void deleteAll() {
        this.jdbccontext.executeSql("delete from user");
    }

    public void add(User user) {
        this.jdbccontext.executeSql("insert into user(id,name,passwird) values(?,?,?)", user);
    }

    public List<User> getAll() {
        Connection connection = datasource.newConnection();
        List<User> list = null;

        RowMapper<User> rowMapper = rs -> {
            User user = new User();
            user.setId(rs.getString("ID"));
            user.setName(rs.getString("NAME"));
            user.setPassword(rs.getString("PASSWORD"));
            return user;
        };

        List list1 = this.jdbccontext.jdbccontextList(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user");
            }
        }, new RowMapResultSet(rowMapper));
        return list1;
    }


    public User getOne(Object... args) {
        Connection connection = datasource.newConnection();
        RowMapper<User> rowMapper = new RowMapper<>() {
            @Override
            public User row(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        };
        User object = this.jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user where id = ? AND password = ?");
            }
        }, new RowMapResultSet(rowMapper), args);
        return object;
    }

    public int getCount() {
        Connection connection = datasource.newConnection();
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


    public void createTable() {

        Connection conn = datasource.newConnection();

        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + " password text NOT NULL\n"
                + ");";

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(String id) {

        Connection conn = datasource.newConnection();

//        this.jdbccontext.jdbccontext(new PreparedStatementStrategy() {
//            @Override
//            public PreparedStatement newStatement(Connection conn) throws SQLException {
//                return conn.prepareStatement("delete from user where id = ?");
//            }
//        });
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from user where id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getList(String name) {
        Connection connection = datasource.newConnection();

        RowMapper<User> rowMapper = new RowMapper<>() {
            @Override
            public User row(ResultSet rs) throws SQLException {
                User user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        };
        List<User> users = this.jdbccontext.jdbccontextList(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("select id, name, password from user where name = ?");
            }
        }, new RowMapResultSet<User>(rowMapper), name);
        return users;
    }
}
