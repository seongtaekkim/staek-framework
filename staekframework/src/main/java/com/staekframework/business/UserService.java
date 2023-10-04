package com.staekframework.business;

import java.util.List;

public interface UserService {

    /**
     * url
     */
    String url = "/jsp/list.jsp";
    //public final String url ="/WEB-INF/classes/com/staekframework/web/jsp/list.jsp"

    List<User> users();

    void createUser(User user);

    void callwithdrawal_program();

}
