package com.staekframework.web.listener;


import com.staekframework.di.ScanAndNewInstance;
import com.staekframework.jdbc.Datasource;
import com.staekframework.jdbc.JDBCConnection;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Iterator;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();

        /**
         * Connection 정보는
         * 설정정보를 읽어오는 로직에서 생성 및 인스턴스등록할 수 있도록 변경 필요.
         */
        Datasource ds = new JDBCConnection();
        ScanAndNewInstance scan = new ScanAndNewInstance();
        scan.putInstance(ds);
        scan.scanAndCreateInstance();
        Object instance = scan.getInstance("com.staekframework.business.UserDao");
        context.setAttribute("UserDao", instance);
        context.setAttribute("defaultTxManager", scan.getInstance("com.staekframework.tx.DefaultTxManager"));
        context.setAttribute("txFactoryProxy", scan.getInstance("com.staekframework.tx.TxFactoryProxy"));

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO
    }
}
