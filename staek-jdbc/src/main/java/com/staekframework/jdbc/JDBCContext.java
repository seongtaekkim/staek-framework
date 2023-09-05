package com.staekframework.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}
