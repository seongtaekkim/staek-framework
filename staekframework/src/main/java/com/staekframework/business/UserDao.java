package com.staekframework.business;

import com.staekframework.di.Repository;
import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCContext;
import com.staekframework.jdbc.RowMapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserDao {

    private final JDBCContext jdbccontext;

    public UserDao(Datasource datasource) {
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
