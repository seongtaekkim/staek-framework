package com.staekframework.tx;

import com.staekframework.jdbc.JDBCConnection;
import com.staekframework.test.User.UserService;
import com.staekframework.test.User.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *  target 설정
 *  invoke 메서드에 부가기능을 구현.
 *  -> 부가기능
 */
public class TxHandler implements InvocationHandler {

    UserService target;

    public TxHandler(UserService target) {
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        UserServiceImpl invoke = null;
        TxManager tx = new DefaultTxManager(JDBCConnection.conn);
        tx.startTx();
        try {
            invoke = (UserServiceImpl) method.invoke(target, args);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
        return invoke;
    }
}
