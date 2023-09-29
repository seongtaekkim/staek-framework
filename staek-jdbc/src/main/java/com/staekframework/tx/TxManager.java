package com.staekframework.tx;

import java.sql.SQLException;

/**
 * transaction 생성주기를 관리하는 인터페이스
 *
 */
public interface TxManager {

    /**
     * 트랜잭션 시작 함수
     *
     */
    void startTx() throws SQLException;

    /**
     * commit
     */
    void commit() throws SQLException;

    /**
     * rollback
     */
    void rollback() throws SQLException;
}