package com.staekframework.tx;


import com.staekframework.aop.proxy.FactoryProxyInstance;
import com.staekframework.di.Inject;

import java.lang.reflect.Proxy;

/**
 *
 */
@Inject
public class TxFactoryProxy implements FactoryProxyInstance<Object> {
    Object target;
    TxManager txManager;
    Class<?> serviceInterface;

    public void setTarget(Object target) {
        this.target = target;
    }

    public void setTxManager(TxManager txManager) {
        this.txManager = txManager;
    }

    public void setServiceInterface(Class<?> serviceInterface) {
        this.serviceInterface = serviceInterface;
    }

    @Override
    public Object getObject() throws Exception {
        TxHandler txHandler = new TxHandler();
        txHandler.setTarget(target);
        txHandler.setTxManager(txManager);
        return Proxy.newProxyInstance(getClass().getClassLoader()
                , new Class[] {serviceInterface}, txHandler);
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
