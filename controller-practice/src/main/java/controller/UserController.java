package controller;

import annotation.RequestMapping;
import controller.dto.LoginDto;
import controller.dto.RegisterDto;
import model.User;

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

    @RequestMapping("/list")
    public String list(User dto) {
        System.out.println("list controller");
        System.out.println(dto);
        return "/";
    }
}
