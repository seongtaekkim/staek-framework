package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.jdbc.JDBCContext;
import com.staekframework.jdbc.PreparedStatementStrategy;

import com.staekframework.test.strategy.GetAllStrategy;

import java.sql.*;

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
        this.jdbccontext.executeSql("insert into user(id,name) values(?,?)", user);

    }

    public User get(String id) {

        Connection connection = datasource.newConnection();

        ResultSet resultSet = null;
        PreparedStatement ps = null;
        User user = new User();
        try {

            ps = new GetAllStrategy().newStatement(connection);
            ps.setString(1, id);
            resultSet = ps.executeQuery();
            resultSet.next();
            user.setId(resultSet.getString("id"));
            user.setName(resultSet.getString("name"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void createTable() {

        Connection conn = datasource.newConnection();

        String sql = "CREATE TABLE IF NOT EXISTS user (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL\n"
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

        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("delete from user where id = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
