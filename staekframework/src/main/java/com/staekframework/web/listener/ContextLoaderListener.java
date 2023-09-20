package com.staekframework.web.listener;

import com.staekframework.test.User.DaoFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        context.setAttribute("userDao",new DaoFactory().newUserDao());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //TODO
    }
}
