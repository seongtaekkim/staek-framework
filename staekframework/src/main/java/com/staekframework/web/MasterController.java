package com.staekframework.web;


import com.staekframework.business.User;
import com.staekframework.business.UserDao;
import com.staekframework.business.UserService;
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
        String url = null;
        if ("/user".equals(pathInfo)) {
            UserService user = new UserService();
            url = user.callList(req);
            resp.setContentType("text/html; charset=UTF-8");
        }
        if ("/withdrawal".equals(pathInfo)) {
            UserService user = new UserService();
            url = user.callwithdrawal(req);
            resp.setContentType("text/html; charset=UTF-8");
        }

        if (url == null) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.include(req, resp);
    }
}
