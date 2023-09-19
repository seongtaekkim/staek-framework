package com.staekframework.web;

import com.staekframework.test.User.DaoFactory;
import com.staekframework.test.User.UserDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class MasterController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private UserDao getDataByUserDao() {
        UserDao userDao = new DaoFactory().newUserDao();
        userDao.createTable();
        userDao.deleteAll();
        com.staekframework.test.User.User user = new com.staekframework.test.User.User("1","김성택","1111");
        userDao.insert(user);
        user = new com.staekframework.test.User.User("2","spring","2222");
        userDao.insert(user);
        user = new com.staekframework.test.User.User("3","spring","2222");
        userDao.insert(user);
        return userDao;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        String url = null;
        if ("/user".equals(pathInfo)) {
            User user = new User();
            url = user.callList(req);
            System.out.println( getDataByUserDao().selectAll().size());
            List<com.staekframework.test.User.User> users = getDataByUserDao().selectAll();
            req.setAttribute("users",users);
            resp.setContentType("text/html; charset=UTF-8");
        }
        if (url == null) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.include(req, resp);
    }
}
