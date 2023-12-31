package com.staekframework.jdbc;

import com.staekframework.jdbc.util.DataAccessUtil;
import java.sql.*;
import java.util.List;

/**
 * TODO PreparedStatement 를 전략패턴을 빼내었기 때문에, 특정 dao 에 의존할 필요가 없어 jdbc 패키지로 옮겼다.
 */
public class JDBCContext {
    private Datasource datasource;

    public JDBCContext(Datasource datasource) {
        this.datasource = datasource;
    }

    /**
     * TODO 같은 로직을 함수마다 반복한다. context 진행 중  PreparedStatementStrategy 만 변경된다.
     *      반복되는 로직을 jdbccontext 라는 이름으로 따로 가져오고, 변경되는 부분을 인자로 받게 해서
     *      dml 함수의 반복되는 부분을 제거하였다.
     */
    public void jdbccontext(PreparedStatementStrategy st) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    /**
     * 테이블 Row 전체 카운트
     */
    public int jdbccontext(PreparedStatementStrategy st, ResultSetStrategy rs) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);

            ResultSet resultSet = ps.executeQuery();
            Object data = rs.getData(resultSet);
            return (int)data;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public <E> E jdbccontextList(PreparedStatementStrategy st, ResultSetStrategy<E> rs) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);

            ResultSet resultSet = ps.executeQuery();
            Object data = rs.getData(resultSet);
            return (E) data;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public <E> E jdbccontextList(PreparedStatementStrategy st, ResultSetStrategy<E> rs, Object... args) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);
            new ArgsPreparedStatementSetter(args).setObjectToRows(ps);

            ResultSet resultSet = ps.executeQuery();
            Object data = rs.getData(resultSet);
            return (E) data;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     *  selectOne
     */
    public <T> T jdbccontext(PreparedStatementStrategy st, ResultSetStrategy<List<T>> rs, Object... args) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;

        try {
            ps = st.newStatement(conn);
            new ArgsPreparedStatementSetter(args).setObjectToRows(ps);
            ResultSet resultSet = ps.executeQuery();
            List<T> data = rs.getData(resultSet);
            return DataAccessUtil.SelectOne(data);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void jdbccontext(PreparedStatementStrategy st, Object... args) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);
            new ArgsPreparedStatementSetter(args).setObjectToRows(ps);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }


    /**
     * 테이블 row 전체 카운트
     */
    public int countSql(String sql) {
        return this.jdbccontext(conn -> conn.prepareStatement(sql)
                , (ResultSetStrategy<Integer>) rs -> {
                    rs.next();
                    return rs.getInt(1);
                });
    }


    /**
     * 가변인자 조건으로 list return
     */
    public <T> List<T> executeSql2(String sql, RowMapper<T> mapper, Object[] args) {
        List<T> list = this.jdbccontextList(conn -> conn.prepareStatement(sql), new RowMapResultSet<T>(mapper), args);
        return list;
    }

    /**
     * selectOne
     */
    public <T> T executeSql(String sql, RowMapper<T> mapper, Object[] args) {
        T data = this.jdbccontext(conn -> conn.prepareStatement(sql), new RowMapResultSet<T>(mapper), args);
        return data;
    }

    /**
     * selectAll
     */
    public <T> List<T> executeSql(String sql, RowMapper<T> mapper) {
        List<T> list = this.jdbccontextList(conn -> conn.prepareStatement(sql), new RowMapResultSet<T>(mapper));
        return list;
    }

    public void updateSql(String sql, Object... args) {
        this.jdbccontext(conn -> conn.prepareStatement(sql), args);
    }

    public void updateSql(String sql) {
        this.jdbccontext(conn -> conn.prepareStatement(sql));
    }

}
