package com.staekframework.tx;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author staek
 * 스레드가 1개라고 가정하고 같은 connection을 반복해서 사용
 */
public class DefaultTxManager implements TxManager {

    private Connection connection;

    public DefaultTxManager(Connection connection) {
        setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void startTx() throws SQLException {
        this.connection.setAutoCommit(false);
    }

    @Override
    public void commit() throws SQLException {
        this.connection.commit();
        this.connection.setAutoCommit(true);
    }

    @Override
    public void rollback() throws SQLException {
        this.connection.rollback();
        this.connection.setAutoCommit(true);
    }

}