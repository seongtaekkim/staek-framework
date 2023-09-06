package com.staekframework.jdbc;

import com.staekframework.jdbc.util.DataAccessUtil;
import com.staekframework.test.User.User;

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

    public <E> List<E> jdbccontextList(PreparedStatementStrategy st, ResultSetStrategy<E> rs) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);

            ResultSet resultSet = ps.executeQuery();
            Object data = rs.getData(resultSet);
            return (List)data;

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

    public <E> E jdbccontextList(PreparedStatementStrategy st, ResultSetStrategy<E> rs, Object... args) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;
        try {
            ps = st.newStatement(conn);

            if (args != null) {
                for (int i = 0; i< args.length ; i++)
                    ps.setString(i+1, args[i].toString());
            }
            ResultSet resultSet = ps.executeQuery();
            Object data = rs.getData(resultSet);
            return (E) data;

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

    /**
     *
     *
     *  selectOne
     */
    public <T> T jdbccontext(PreparedStatementStrategy st, ResultSetStrategy rs, Object... args) {
        Connection conn = datasource.newConnection();

        PreparedStatement ps = null;

        try {
            ps = st.newStatement(conn);
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    ps.setString(i+1, arg.toString());
                }
            }
            ResultSet resultSet = ps.executeQuery();
            return DataAccessUtil.SelectOne((List<T>)rs.getData(resultSet));
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


    public void executeSql(String query) {
        this.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                return conn.prepareStatement(query);
            }
        });
    }

    public void executeSql(String query, User user) {
        this.jdbccontext(new PreparedStatementStrategy() {
            @Override
            public PreparedStatement newStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(query);

                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());
                return ps;
            }
        });
    }
}
