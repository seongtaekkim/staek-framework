package controller;

import annotation.RequestMapping;

public class UserController {


    @RequestMapping("/login")
    public String login() {
        System.out.println("login controller");
        return "/index.jsp";
    }

    @RequestMapping("/register")
    public String register() {
        System.out.println("register controller");
        return "/";
    }
}
