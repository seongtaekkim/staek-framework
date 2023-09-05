package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;
import com.staekframework.jdbc.JDBCContext;
import com.staekframework.jdbc.PreparedStatementStrategy;

import com.staekframework.test.strategy.GetAllStrategy;

import java.sql.*;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * 함수별 context 중간에 변경되는 부분을 UserDao 구현체가 처리하도록 했었는데, 너무많은 클래스를 생산해야 했기에
 * 전략패턴으로 분리시켰다.
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
        jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement("delete from user");
            }
        });
    }

    // User 는 final 을 암시하기에 변경 불가
    public void add(User user) {
        jdbccontext.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement("insert into user(id,name) values(?,?)");

                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                return ps;
            }
        });

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
