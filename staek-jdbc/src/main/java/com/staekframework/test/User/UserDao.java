package com.staekframework.test.User;

import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBC;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Random;

/**
 * TODO User 관련 업무에 대한 DAO 이다.
 * dao 로직 전개 중에 변경되는 Statement 만 함수로 추출하였다.
 * 추출한 함수를 구현체를 함수개수만큼 만들어야 한다.(템플릿 메서드 패턴)
 */
public abstract class UserDao {

    protected Datasource datasource;
    public UserDao(Datasource datasource) {
        this.datasource = datasource;
    }

    public abstract PreparedStatement newStatement(Connection conn) throws SQLException;


    public void add(User user) {

        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = newStatement(conn);
            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.executeUpdate();

            ps.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User get(String id) {
        Connection connection = datasource.newConnection();

        ResultSet resultSet = null;
        PreparedStatement ps = null;
        User user = new User();
        try {
            ps = newStatement(connection);
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


    public void deleteAll() {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = newStatement(conn);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                ps.close();
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
