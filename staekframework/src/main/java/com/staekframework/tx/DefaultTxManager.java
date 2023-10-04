package com.staekframework.tx;

import com.staekframework.di.Inject;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author staek
 * 스레드가 1개라고 가정하고 같은 connection을 반복해서 사용
 */
@Inject
public class DefaultTxManager implements TxManager {

    private Connection connection;

    public DefaultTxManager() {
    }

    public DefaultTxManager(Connection connection) {
        setConnection(connection);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void startTx() {
        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void commit()  {
        try {
            this.connection.commit();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            this.connection.rollback();
            this.connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
