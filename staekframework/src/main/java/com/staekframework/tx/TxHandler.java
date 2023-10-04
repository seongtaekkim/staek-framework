package com.staekframework.tx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *  다이나믹 프록시 인자에 들어갈 InvocationHandler 구현체 구현
 *  target 설정
 *  invoke 메서드에 부가기능을 구현.
 *  -> 부가기능
 */
public class TxHandler implements InvocationHandler {

    Object target;
    TxManager tx;

    public TxHandler(Object target) {
        this.target = target;
    }

    public void setTxManager(TxManager tx) {
        this.tx = tx;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Object invoke = null;
        tx.startTx();
        try {
            invoke = method.invoke(target, args);
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }
        tx.commit();
        return invoke;
    }
}
