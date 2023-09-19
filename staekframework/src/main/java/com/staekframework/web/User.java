package com.staekframework.web;

import javax.servlet.http.HttpServletRequest;

public class User {
    public String callList(HttpServletRequest request) {

        String findStr = request.getParameter("findStr");
        System.out.println(findStr);

        /**
         * 어떤걸 리턴해주는게 좋은걸까
         */
        return "/jsp/list.jsp";
//        return "/WEB-INF/classes/com/staekframework/web/jsp/list.jsp";
    }
}
