package com.staekframework.web;


import com.staekframework.business.User;
import com.staekframework.business.UserDao;
import com.staekframework.business.UserService;
import com.staekframework.business.UserServiceImpl;
import com.staekframework.di.WireInject;

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
            user.createUser(new User("1", "김성택", "1111", "10000"));
            user.createUser(new User("2", "staek", "2222", "15000"));
            user.createUser(new User("3", "seongtki", "2222", "20000"));
            req.setAttribute("users", user.users());
            url = UserService.url;
            resp.setContentType("text/html; charset=UTF-8");
        }
        if ("/withdrawal".equals(pathInfo)) {
            UserService user = new UserServiceImpl(userDao);
            try {
                user.callwithdrawal_program();
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
