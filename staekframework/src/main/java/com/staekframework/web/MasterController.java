package com.staekframework.web;


import com.staekframework.business.User;
import com.staekframework.business.UserDao;
import com.staekframework.business.UserService;
import com.staekframework.business.UserServiceImpl;
import com.staekframework.di.WireInject;
import com.staekframework.jdbc.JDBCConnection;
import com.staekframework.tx.DefaultTxManager;
import com.staekframework.tx.TxFactoryProxy;
import com.staekframework.tx.TxManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MasterController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
//        System.out.println(pathInfo);
        UserDao userDao = (UserDao) req.getServletContext().getAttribute("UserDao");
        String url = null;

        userDao.createTable();
        if ("/user".equals(pathInfo)) {
            UserService user = new UserServiceImpl(userDao);
            user.createUser(new User("1", "kim", "1111", "50000"));
            user.createUser(new User("2", "kim2", "1111", "45000"));
            user.createUser(new User("3", "kim3", "1111", "35000"));
            user.createUser(new User("4", "kim4", "1111", "20000"));
            user.createUser(new User("5", "kim5", "1111", "10000"));
            req.setAttribute("users", user.users());
            url = UserService.url;
            resp.setContentType("text/html; charset=UTF-8");
        }
        if ("/withdrawal".equals(pathInfo)) {
            UserService user = new UserServiceImpl(userDao);
            DefaultTxManager txManager = (DefaultTxManager) req.getServletContext().getAttribute("defaultTxManager");
            txManager.setConnection(JDBCConnection.conn);
            TxFactoryProxy txFactoryProxy = (TxFactoryProxy) req.getServletContext().getAttribute("txFactoryProxy");
            txFactoryProxy.setTarget(user);
            txFactoryProxy.setServiceInterface(UserService.class);
            txFactoryProxy.setTxManager(txManager);

            try {
                ((UserService)txFactoryProxy.getObject()).callwithdrawal_program();
            } catch (Exception e) {

            }
            url = UserService.url;
            resp.setContentType("text/html; charset=UTF-8");
            req.setAttribute("users", user.users());
        }

        if (url == null) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.include(req, resp);
    }
}
