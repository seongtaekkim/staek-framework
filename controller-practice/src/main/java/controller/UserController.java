package controller;

import annotation.RequestMapping;
import controller.dto.LoginDto;
import controller.dto.RegisterDto;

public class UserController {


    @RequestMapping("/login")
    public String login(LoginDto dto) {
        System.out.println("login controller");
        System.out.println(dto);
        return "/index.jsp";
    }

    @RequestMapping("/register")
    public String register(RegisterDto dto) {
        System.out.println("register controller");
        System.out.println(dto);
        return "/";
    }
}
