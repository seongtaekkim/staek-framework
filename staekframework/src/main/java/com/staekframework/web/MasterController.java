package com.staekframework.web;


import com.staekframework.business.User;
import com.staekframework.business.UserDao;
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

    /**
     * 클래스가 이미 인스턴스가 생성된 경우여야만하는데
     * 그 생각을 못함.. 나중에 개선점으로 남겨두자.
     */
    @WireInject
    private UserDao userDao;

    private UserDao getDataByUserDao(HttpServletRequest req) {
        userDao = (UserDao) req.getServletContext().getAttribute("UserDao");
        userDao.createTable();
        userDao.deleteAll();
        User user = new User("1","김성택","1111");
        userDao.insert(user);
        user = new User("2","spring","2222");
        userDao.insert(user);
        user = new User("3","spring","2222");
        userDao.insert(user);
        return userDao;
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        System.out.println(pathInfo);
        String url = null;
        if ("/user".equals(pathInfo)) {
            UserService user = new UserService();
            url = user.callList(req);
            List<User> users = getDataByUserDao(req).selectAll();
            req.setAttribute("users",users);
            User user1 = new User("10", "a", "b");
            req.setAttribute("data",user1);
            resp.setContentType("text/html; charset=UTF-8");
        }
        if (url == null) {
            throw new ServletException();
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(url);
        dispatcher.include(req, resp);
    }
}
