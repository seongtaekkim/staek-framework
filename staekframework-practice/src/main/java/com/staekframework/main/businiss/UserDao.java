package com.staekframework.main.businiss;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDao {

//    @Autowired
//    DataSource dataSource;

    private  JdbcTemplate jdbccontext;

    public UserDao(DataSource datasource) {
        this.jdbccontext = new JdbcTemplate(datasource);
    }


    private static RowMapper<User> rowMapper = new RowMapper<>() {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new User(rs.getString("id")
                    , rs.getString("name")
                    , rs.getString("password")
                    , rs.getString("price"));
        }

    };

    public void delete(String id) {
        this.jdbccontext.update("delete from users where id = ?", id);
    }

    public void deleteAll() {
        this.jdbccontext.update("delete from users");
    }

    public void insert(User user) {
        this.jdbccontext.update("insert into users(id,name,password,price) values(?,?,?,?)"
                , user.getId(), user.getName(), user.getPassword(), user.getPrice());
    }

    public List<User> selectAll() {
        return this.jdbccontext.query("select id, name, password, price from users", rowMapper);
    }

    public User selectOne(Object... args) {
        return this.jdbccontext.queryForObject("select id, name, password, price from users where id = ? AND password = ?", rowMapper, args);
    }

    public List<User> selectList(Object... args) {
        return this.jdbccontext.query("select id, name, password, price from users where name = ?", rowMapper, args);
    }

    public int count() {
        return this.jdbccontext.queryForObject("select count(*) as count from users", Integer.class);
    }

    public void update(User user) {
        System.out.println(user.getId() + " " + user.getPrice());
        this.jdbccontext.update("update users set price = ? where id = ?"
                , user.getPrice(), user.getId());
    }
}
