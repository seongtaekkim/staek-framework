package com.staekframework.test.User;

import com.staekframework.jdbc.JDBCConnection;
import com.staekframework.tx.DefaultTxManager;
import com.staekframework.tx.TxManager;


/**
 * UserService 의 트랜잭션기능을 갖고있는 프록시객체.
 * target: UserService
 * 부가기능: transaction 경계설정
 */
public class UserServiceTx implements UserService {

    UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void createUser(User user) {
        TxManager tx = new DefaultTxManager(JDBCConnection.conn);
        try {
            tx.startTx();
            this.userService.createUser(user);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
    }

    @Override
    public void callwithdrawal_program() {
        TxManager tx = new DefaultTxManager(JDBCConnection.conn);
        tx.startTx();
        try {
            this.userService.callwithdrawal_program();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
    }
}
