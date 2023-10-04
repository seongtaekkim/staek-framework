package com.staekframework.tx;

/**
 * transaction 생성주기를 관리하는 인터페이스
 *
 */
public interface TxManager {

    /**
     * 트랜잭션 시작 함수
     *
     */
    void startTx();

    /**
     * commit
     */
    void commit();

    /**
     * rollback
     */
    void rollback();
}
