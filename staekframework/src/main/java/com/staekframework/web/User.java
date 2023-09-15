package com.staekframework.web;

import javax.servlet.http.HttpServletRequest;

public class User {
    public String callList(HttpServletRequest request) {

        String findStr = request.getParameter("findStr");

        System.out.println(findStr);

        return "/WEB-INF/classes/com/staekframework/web/jsp/list.jsp";
    }
}
